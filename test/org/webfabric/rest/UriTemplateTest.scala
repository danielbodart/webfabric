package org.webfabric.rest

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._

class UriTemplateTest{
  @Test
  def supportsCustomRegex() {
    val template = new UriTemplate("""path/{id:\d}""")
    assertThat(template.isMatch("path/foo"), is(false))
    assertThat(template.isMatch("path/1"), is(true))
    assertThat(template.extract("path/1").getValue("id"), is("1"))
  }

  @Test
  def canMatch() {
    val template = new UriTemplate("""path/{id}""")
    assertThat(template.isMatch("path/foo"), is(true))
  }

  @Test
  def canExtractFromUri() {
    val template = new UriTemplate("""path/{id}""")
    assertThat(template.extract("path/foo").getValue("id"), is("foo"))
  }

  @Test
  def canGenerateUri() {
    val template = new UriTemplate("path/{id}")
    assertThat(template.generate(PathParameters("id" -> "foo")), is("path/foo"))
  }

}
