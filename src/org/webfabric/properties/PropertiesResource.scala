package org.webfabric.properties

import com.google.appengine.api.datastore.{DatastoreServiceFactory}
import javax.ws.rs.core.StreamingOutput
import java.util.{Properties, UUID}
import javax.ws.rs._
import java.io.{InputStream, OutputStream}

@Path("properties")
class PropertiesResource(repository: PropertiesRepository) {
  def this() = this (new PropertiesRepository(DatastoreServiceFactory.getDatastoreService))

  @GET
  @Path("{id}")
  @Produces(Array("text/plain"))
  def get(@PathParam("id") id: String): StreamingOutput = {
    var uuid = UUID.fromString(id)
    var properties = repository.get(uuid)
    new StreamingOutput() {
      def write(out: OutputStream) = properties.store(out, null)
    }
  }

  @PUT
  @Path("{id}")
  //@Consumes(Array("text/plain"))
  def put(@PathParam("id") id: String, input: InputStream) = {
    var uuid = UUID.fromString(id)
    val properties = new Properties
    properties.load(input)
    repository.set(uuid, properties)
  }

  @DELETE
  @Path("{id}")
  def delete(@PathParam("id") id: String) = {
    var uuid = UUID.fromString(id)
    repository.remove(uuid)
  }
}