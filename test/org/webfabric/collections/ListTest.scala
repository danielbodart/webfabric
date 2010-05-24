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

  @Test
  def supportsSorting(): Unit = {
    // setup
    val results = List(1,3,2,0).sort(_ > _)

    // verify
    var iterator = results.iterator
    assertThat(iterator.next, is(equalTo(3)))
    assertThat(iterator.next, is(equalTo(2)))
    assertThat(iterator.next, is(equalTo(1)))
    assertThat(iterator.next, is(equalTo(0)))
  }
}