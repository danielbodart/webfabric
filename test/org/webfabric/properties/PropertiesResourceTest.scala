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
    val application = new PropertiesApplication
    val postResponse = Response()
    val properties = "foo=bar"
    application.handle(post("properties/new").withForm("properties" -> properties), postResponse)

    assertThat(postResponse.code, is(Status.SEE_OTHER))
    val location = postResponse.headers.getValue(HttpHeaders.LOCATION)
    val getResponse = Response()
    application.handle(get(location).accepting("text/plain"), getResponse)
    assertTrue(getResponse.output.toString.contains(properties))
  }

  @Test
  def canGetNewResource{
    val application = new PropertiesApplication
    val response = Response()
    val properties = "foo=bar"
    application.handle(get("properties/new").accepting("text/html"), response)

    assertThat(response.code, is(Status.OK))
    
  }

  @Test
  def canGetASinglePropertyValue{
    val application = new PropertiesApplication
    val postResponse = Response()
    val properties = "foo=bar"
    application.handle(post("properties/new").withForm("properties" -> properties), postResponse)

    val location = postResponse.headers.getValue(HttpHeaders.LOCATION) + "/foo"
    val getResponse = Response()
    application.handle(get(location).accepting("text/plain"), getResponse)
    getResponse.flush
    assertThat(getResponse.output.toString, is("bar"))
  }
}