package org.webfabric.properties

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._

class WebIdTest {
  var value = "550e8400-e29b-41d4-a716-446655440000"

  @Test
  def supportsConvertingToAndFromString {
    var id = WebId.valueOf(value)
    assertThat(id.toString, is(value))
  }

  @Test
  def supportsEquality {
    assertThat(WebId.valueOf(value), is(equalTo(WebId.valueOf(value))))
  }
}