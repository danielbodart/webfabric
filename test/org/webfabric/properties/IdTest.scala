package org.webfabric.properties

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._

class IdTest{
  @Test
  def supportsEquality() {
    assertThat(Id("new"), is(equalTo(Id("new"))))
  }
}