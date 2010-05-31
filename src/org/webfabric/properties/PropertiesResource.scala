package org.webfabric.properties

import java.util.{Properties, UUID}
import javax.ws.rs._
import core.{StreamingOutput}
import java.io._
import org.antlr.stringtemplate.{AutoIndentWriter, StringTemplateGroup}
import org.webfabric.rest.Redirect
import org.webfabric.rest.Redirect.resource

@Path("properties/{id}")
class PropertiesResource(repository: PropertiesRepository, templates:StringTemplateGroup) {
  def this() = this(null, null)

  @GET
  @Produces(Array("text/plain"))
  def getProperties(@PathParam("id") id: String): StreamingOutput = {
    val uuid = UUID.fromString(id)
    val properties = repository.get(uuid)
    new StreamingOutput {
      def write(out: OutputStream) = properties.store(out, null)
    }
  }

  @GET
  @Produces(Array("text/html"))
  def getHtml(@PathParam("id") id: String): StreamingOutput = {
    val uuid = UUID.fromString(id)
    val properties = repository.get(uuid)
    val template = templates.getInstanceOf("editor")
    val writer = new StringWriter
    properties.store(writer, null)
    template.setAttribute("properties", writer.toString)
    new StreamingOutput{
      def write(output: OutputStream) = {
        var streamWriter = new OutputStreamWriter(output)
        template.write(new AutoIndentWriter(streamWriter))
        streamWriter.flush
      }
    }
  }

  @POST
  def post(@PathParam("id") id: String, @FormParam("properties") input:String):Redirect = {
    val uuid = id match {
      case "new" => UUID.randomUUID
      case _ => UUID.fromString(id)
    }
    val properties = new Properties
    properties.load( new StringReader(input))
    repository.set(uuid, properties)
    Redirect(resource(classOf[PropertiesResource]).getHtml(uuid.toString))
  }

  @PUT
  @Consumes(Array("text/plain"))
  def put(@PathParam("id") id: String, input: InputStream):Unit = {
    val uuid = UUID.fromString(id)
    val properties = new Properties
    properties.load(input)
    repository.set(uuid, properties)
  }

  @DELETE
  def delete(@PathParam("id") id: String):Unit = {
    val uuid = UUID.fromString(id)
    repository.remove(uuid)
  }
}