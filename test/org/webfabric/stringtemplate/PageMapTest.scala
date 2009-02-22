package org.webfabric.stringtemplate

import antlr.stringtemplate.StringTemplate
import io.{HierarchicalPath, Url, RelativeResource}
import java.lang.String
import java.net.URL
import junit.Test
import junit.Assert.{assertEquals}

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