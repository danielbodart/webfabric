package org.webfabric.rest.coercers


import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._
import org.webfabric.rest.coercers.ReflectionTypeCoercerTest.Foo

class ReflectionTypeCoercerTest{
  @Test
  def canCoerceTypesWithStringConstructors{
    val coercedType = new ReflectionTypeCoercer().coerce("bar", classOf[Foo])
    assertThat(coercedType, is(instanceOf(classOf[Foo])))
    assertThat(coercedType.asInstanceOf[Foo].value, is("bar"))
  }
}

object ReflectionTypeCoercerTest{
  class Foo(val value:String)
}