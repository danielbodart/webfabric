package org.webfabric.rest.coercers

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._
import java.io.{InputStream, ByteArrayInputStream}

class PathThroughCoercerTest{
  @Test
  def canCoerceTypesTheImplementInterface{
    val input = new ByteArrayInputStream(new Array[Byte](0))
    val coercer = new PassThroughCoercer()
    assertThat(coercer.canCoerce(input, classOf[InputStream]), is(true))
    assertThat(coercer.coerce(input, classOf[InputStream]), is(instanceOf(classOf[InputStream])))
  }
}