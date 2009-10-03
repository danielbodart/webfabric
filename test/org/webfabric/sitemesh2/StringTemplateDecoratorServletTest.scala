package org.webfabric.sitemesh2

import org.junit.Test
import org.junit.Assert.assertThat
import org.hamcrest.CoreMatchers.{is,equalTo, nullValue}

class StringTemplateDecoratorServletTest {
  @Test
  def capturesTheFilenameFromThePath() {
    // setup
    val StringTemplateDecoratorServlet.TemplateName(path, name) = "somefile.st"
    assertThat(path, is(nullValue[String]))
    assertThat(name, is(equalTo("somefile")))
  }

  @Test
  def capturesThePathAndTheFilenameSeperately() {
    // setup
    val StringTemplateDecoratorServlet.TemplateName(path, name) = "somepath/somefile.st"
    assertThat(path, is(equalTo("somepath")))
    assertThat(name, is(equalTo("somefile")))
  }
}