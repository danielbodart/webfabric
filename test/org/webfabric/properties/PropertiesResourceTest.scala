package org.webfabric.properties

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._
import org.webfabric.gae.LocalDatastore
import org.webfabric.rest._
import org.webfabric.rest.RequestBuilder._
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response.Status

class PropertiesResourceTest extends LocalDatastore{
  @Test
  def canPostToCreateNewResource{
    val application = new RestApplication
    val postResponse = Response()
    val properties = "foo=bar"
    application.handle(post("properties/new").withForm("properties" -> properties), postResponse)

    assertThat(postResponse.code, is(Status.SEE_OTHER.getStatusCode))
    val location = postResponse.headers.getValue(HttpHeaders.LOCATION)
    val getResponse = Response()
    application.handle(get(location).withHeader(HttpHeaders.ACCEPT -> "text/plain"), getResponse)
    assertTrue(getResponse.output.toString.contains(properties))
  }
}