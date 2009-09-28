package org.webfabric.stringtemplate

import org.antlr.stringtemplate.StringTemplate
import org.webfabric.io.{Url, RelativeResource}
import org.junit.Test
import org.junit.Assert.{assertEquals}

class PageMapTest{
  @Test
  def failsSilentlyIfPageIsNotAvailable{
    // setup
    val pageMap = new PageMap
    val template = new StringTemplate("""$include.(url).title$""")
    template.setAttribute("include", pageMap)
    template.setAttribute("url", "invalidUrl")

    // execute
    val title = template.toString

    // verify
    assertEquals("", title)
  }

  @Test
  def canBeUsedInAStringTemplate{
    // setup
    val pageMap = new PageMap
    val template = new StringTemplate("""$include.(url).title$""")
    template.setAttribute("include", pageMap)
    template.setAttribute("url", PageMapTest.url)

    // execute
    val title = template.toString

    // verify
    assertEquals("Test", title)
  }
}

object PageMapTest{
  def url:Url = {
    return RelativeResource.asUrl(classOf[PageMapTest], "html.st")
  }
}