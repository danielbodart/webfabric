package org.webfabric.stringtemplate


import antlr.stringtemplate.{StringTemplateGroup, StringTemplate}
import io.RelativeResource
import java.net.URL
import org.junit.Test
import org.junit.Assert.{assertNotNull, assertEquals}

class UrlStringTemplateGroupTest{
  @Test
  def loadsTemplatesFromBaseUrl = {
    // setup
    val baseUrl:URL = getBaseUrl()
    val group: StringTemplateGroup = new UrlStringTemplateGroup("resourceTemplates", baseUrl)

    // execute
    val template: StringTemplate = group.getInstanceOf("resource")

    // verify
    assertNotNull(template)
    template.setAttribute("bar", "test")
    assertEquals("foo:test", template.toString)
  }

  def getBaseUrl(): URL = {
    val url = RelativeResource.asUrl(classOf[UrlStringTemplateGroupTest], "resource.st").toString
    val lastSlash: Int = url.lastIndexOf("/resource.st")
    val base = url.subSequence(0, lastSlash).toString
    new URL(base)
  }
}