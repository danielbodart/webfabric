package org.webfabric.properties

import java.util.{Properties, UUID}
import javax.ws.rs._
import core.{StreamingOutput}
import java.io._
import org.antlr.stringtemplate.{AutoIndentWriter, StringTemplateGroup}
import org.webfabric.rest.Redirect.resource
import org.webfabric.rest.{StreamingWriter, Redirect}

@Path("properties/{id}")
class PropertiesResource(repository: PropertiesRepository, templates:StringTemplateGroup) {
  def this() = this(null, null)

  @GET
  @Produces(Array("text/plain"))
  def getProperties(@PathParam("id") id: Id): StreamingOutput = {
    val properties = repository.get(id)
    new StreamingOutput {
      def write(out: OutputStream) = properties.store(out, null)
    }
  }

  @GET
  @Produces(Array("text/html"))
  def getHtml(@PathParam("id") id: Id): StreamingWriter = {
    val properties = repository.get(id)
    val template = templates.getInstanceOf("editor")
    val writer = new StringWriter
    properties.store(writer, null)
    template.setAttribute("properties", writer.toString)
    new StreamingWriter{
      def write(writer: Writer) = {
        template.write(new AutoIndentWriter(writer))
      }
    }
  }

  @POST
  def post(@PathParam("id") rawId: String, @FormParam("properties") input:String):Redirect = {
    val id = rawId match {
      case "new" => Id()
      case _ => Id(rawId)
    }
    val properties = new Properties
    properties.load( new StringReader(input))
    repository.set(id, properties)
    Redirect(resource(classOf[PropertiesResource]).getHtml(id))
  }

  @PUT
  @Consumes(Array("text/plain"))
  def put(@PathParam("id") id: Id, input: InputStream):Unit = {
    val properties = new Properties
    properties.load(input)
    repository.set(id, properties)
  }

  @DELETE
  def delete(@PathParam("id") id: Id):Unit = {
    repository.remove(id)
  }
}