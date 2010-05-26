package org.webfabric.properties

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._
import org.webfabric.io.Url
import java.util.UUID
import org.webfabric.jetty.WebServer

class WebPropertiesTest {
  val url: Url = Url("http://localhost:8000/rest/properties/" + UUID.randomUUID.toString)
  val server = new WebServer(8000)

  @Before
  def startServer = server.start

  @After
  def cleanup:Unit = {
    url.delete
    server.stop
  }

  @Test
  def canGetProperties {
    val properties = new WebProperties(url)
    assertThat(properties.entrySet.size, is(0))
  }

  @Test
  def canPutProperties {
    val sameUrl: Url = url
    var properties = new WebProperties(sameUrl)
    assertThat(properties.entrySet.size, is(0))
    properties.setProperty("foo", "bar")
    properties.flush

    properties = new WebProperties(sameUrl)
    assertThat(properties.entrySet.size, is(1))
    assertThat(properties.getProperty("foo"), is("bar"))
  }
}