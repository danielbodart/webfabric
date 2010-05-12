package org.webfabric.properties

import java.util.{Properties, UUID}
import javax.ws.rs._
import core.{Response, StreamingOutput}
import org.antlr.stringtemplate.{AutoIndentWriter, StringTemplateGroup}
import java.io._

@Path("properties/{id}")
class PropertiesResource(repository: PropertiesRepository, templates:StringTemplateGroup) {
  def this() = this(null, null)

  @GET
  @Produces(Array("text/plain"))
  def getProperties(@PathParam("id") id: String): StreamingOutput = {
    val uuid = UUID.fromString(id)
    val properties = repository.get(uuid)
    new StreamingOutput() {
      def write(out: OutputStream) = properties.store(out, null)
    }
  }

  @GET
  @Produces(Array("text/html"))
  def getHtml(@PathParam("id") id: String): String = {
    val uuid = UUID.fromString(id)
    val properties = repository.get(uuid)
    val template = templates.getInstanceOf("editor")
    val writer = new StringWriter
    properties.store(writer, null)
    template.setAttribute("properties", writer.toString)
    template.toString
  }

  @POST
  def post(@PathParam("id") id: String, @FormParam("properties") input:String) = {
    val uuid = UUID.fromString(id)
    val properties = new Properties
    properties.load( new StringReader(input))
    repository.set(uuid, properties)
    Response.noContent
  }

  @PUT
  @Consumes(Array("text/plain"))
  def put(@PathParam("id") id: String, input: InputStream) = {
    val uuid = UUID.fromString(id)
    val properties = new Properties
    properties.load(input)
    repository.set(uuid, properties)
    Response.noContent
  }

  @DELETE
  def delete(@PathParam("id") id: String) = {
    val uuid = UUID.fromString(id)
    repository.remove(uuid)
    Response.noContent
  }
}