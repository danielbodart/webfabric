package org.webfabric.rest.coercers

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._
import java.io.{ByteArrayInputStream, InputStream}

class CoercersTest{
  @Test
  def canAddCoercers{
    val coercers = new Coercers()
    assertThat(coercers.coerce("foo", classOf[String]).asInstanceOf[String], is("foo"))
    assertThat(coercers.coerce(new ByteArrayInputStream(new Array[Byte](0)), classOf[InputStream]), is(instanceOf(classOf[InputStream])))
  }
}