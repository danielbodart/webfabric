package org.webfabric.collections

import org.junit.Test
import org.junit.Assert.{assertThat,fail}
import org.hamcrest.CoreMatchers._

class ListTest {
  @Test
  def supportsAdding(): Unit = {
    // setup
    val results = List(1,2)
    results.add(3)
    // verify
    assertThat(results.size, is(equalTo(3)))
  }
}