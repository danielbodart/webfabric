package org.webfabric.properties

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._
import org.webfabric.io.Url

class WebPropertiesTest {
  @Test
  def canGetProperties {
    val properties = new WebProperties(Url("http://www.webfabric.org/properties/?uuid=550e8400-e29b-41d4-a716-446655440000"))
    assertThat(properties.entrySet.size, is(0))
  }

  @Test
  def canPutProperties {
    val properties = new WebProperties(Url("http://www.webfabric.org/properties/?uuid=550e8400-e29b-41d4-a716-446655440000"))
    assertThat(properties.entrySet.size, is(0))
    properties.setProperty("foo", "bar")
    properties.flush
  }
}