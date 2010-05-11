package org.webfabric.properties

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._
import org.webfabric.io.Url
import java.util.UUID

class WebPropertiesTest {
  def url: Url = Url("http://www.webfabric.org/rest/properties/" + UUID.randomUUID.toString)
  
  @After
  def cleanup:Unit = url.delete

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
  }
}