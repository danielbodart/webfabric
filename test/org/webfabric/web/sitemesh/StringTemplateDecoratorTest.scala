package org.webfabric.web.sitemesh

import _root_.org.webfabric.web.servlet.{QueryString, ContextPath}
import antlr.stringtemplate.StringTemplate
import com.opensymphony.module.sitemesh.HTMLPage
import org.junit.Test
import org.junit.Assert.assertEquals
import stringtemplate.{PageMap, PageMapTest}

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
    val html = createPage("")
    val template = new StringTemplate("$page$")
    val decorator = new StringTemplateDecorator(template)

    // execute
    val result = GetResult(decorator, html)

    // verify
    assertEquals(html.toString(), result)
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
    decorator.setPage(html).toString()
  }

  def createPage(html: String): HTMLPage = {
    val pageParser = new DivCapturingPageParser()
    pageParser.parse(html)
  }
}
