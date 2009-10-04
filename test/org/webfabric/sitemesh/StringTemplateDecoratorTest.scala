package org.webfabric.sitemesh

import org.webfabric.servlet.{ContextPath}
import com.opensymphony.module.sitemesh.HTMLPage
import org.junit.Test
import org.junit.Assert.assertEquals
import org.webfabric.stringtemplate.{PageMapTest, PageMap}
import org.antlr.stringtemplate.StringTemplate
import org.webfabric.sitemesh2.{PagePropertyMap, DivCapturingPageParser}

class StringTemplateDecoratorTest {
  @Test
  def supportsIncludingOtherPages(): Unit = {
    // setup
    val include = new PageMap
    val template = new StringTemplate("$include.(url).title$")
    template.setAttribute("url", PageMapTest.url)
    val decorator = new StringTemplateDecorator(template)

    // execute
    val result = decorator.setInclude(include).toString

    // verify
    assertEquals("Test", result)
  }


  @Test
  def supportsBase(): Unit = {
    // setup
    val base = new ContextPath("/foo/")
    val template = new StringTemplate("$base$")
    val decorator = new StringTemplateDecorator(template)

    // execute
    val result = decorator.setBase(base).toString

    // verify
    assertEquals(base.toString(), result)
  }

  @Test
  def SupportsPage = {
    // setup
    val html = createPage("<html><head><title>Some title</title></head></html>")
    val template = new StringTemplate("$properties.title$")
    val decorator = new StringTemplateDecorator(template)

    // execute
    val result = GetResult(decorator, html)

    // verify
    assertEquals("Some title", result)
  }

  @Test
  def SupportsHeadTag = {
    // setup
    val html = createPage("<html><head><script/></head></html>")
    val template = new StringTemplate("$head$")
    val decorator = new StringTemplateDecorator(template)

    // execute
    val result = GetResult(decorator, html)

    // verify
    assertEquals("<script/>", result)
  }

  @Test
  def SupportsBodyTag = {
    // setup
    val html = createPage("<html><body>Some text</body></html>")
    val template = new StringTemplate("$body$")
    val decorator = new StringTemplateDecorator(template)

    // execute
    val result = GetResult(decorator, html)

    // verify
    assertEquals("Some text", result)
  }

  @Test
  def SupportsTitleTag = {
    // setup
    val html = createPage("<html><head><title>Some title</title></head></html>")
    val template = new StringTemplate("$title$")
    val decorator = new StringTemplateDecorator(template)

    // execute
    val result = GetResult(decorator, html)

    // verify
    assertEquals("Some title", result)
  }

  def GetResult(decorator: StringTemplateDecorator, html: HTMLPage): String = {
    decorator.setContent(new PagePropertyMap(html)).toString()
  }

  def createPage(html: String): HTMLPage = {
    val pageParser = new DivCapturingPageParser()
    pageParser.parse(html)
  }
}
