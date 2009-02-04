package org.webfabric.stringtemplate

import antlr.stringtemplate.StringTemplate
import java.lang.String
import junit.Test
import junit.Assert.{assertEquals}

class PageMapTest{
  @Test
  def failsSilentlyIfPageIsNotAvailable(){
    // setup
    val pageMap = new PageMap()
    val template = new StringTemplate("""$include.("http://www.webfabric.org/made/up/page").title$""")
    template.setAttribute("include", pageMap)

    // execute
    val title = template.toString

    // verify
    assertEquals("", title)
  }

  @Test
  def canBeUsedInAStringTemplate(){
    // setup
    val pageMap = new PageMap()
    val template = new StringTemplate("""$include.("http://www.google.co.uk/").title$""")
    template.setAttribute("include", pageMap)

    // execute
    val title = template.toString

    // verify
    assertEquals("Google", title)
  }
}