package org.webfabric.stringtemplate

import antlr.stringtemplate.StringTemplate
import io.{HierarchicalPath, Url, RelativeResource}
import java.lang.String
import java.net.URL
import junit.Test
import junit.Assert.{assertEquals}

class PageMapTest{
  @Test{val timeout=200}
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

  @Test{val timeout=200}
  def canBeUsedInAStringTemplate{
    // setup
    val pageMap = new PageMap
    val template = new StringTemplate("""$include.(url).title$""")
    template.setAttribute("include", pageMap)
    template.setAttribute("url", url)

    // execute
    val title = template.toString

    // verify
    assertEquals("Test", title)
  }

  def url:Url = {
    return new Url(RelativeResource.asUrl(classOf[PageMapTest], "html.st"))
  }
}