package org.webfabric.properties

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._

class IdTest{
  @Test
  def supportsEquality() {
    assertThat(Id("new"), is(equalTo(Id("new"))))
    assertThat(Id("4d237b0a-535f-49e9-86ca-10d28aa3e4f8"), is(equalTo(Id("4d237b0a-535f-49e9-86ca-10d28aa3e4f8"))))
  }
}