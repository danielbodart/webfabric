package org.webfabric.stringtemplate


import antlr.stringtemplate.{StringTemplateGroup, StringTemplate}
import io.RelativeResource
import java.net.URL
import org.junit.{Test, Assert}
import org.junit.Assert.{assertNotNull, assertEquals, fail}

class UrlStringTemplateGroupTest {
  @Test {val expected = classOf[IllegalArgumentException]}
  def handlesMissingTemplatesTheSameWay(): Unit = {
    // setup
    val group: StringTemplateGroup = new UrlStringTemplateGroup("resourceTemplates", baseUrl)

    // execute
    group.getInstanceOf("missing")
  }

  @Test
  def supportsTemplatesInSubFolders = {
    // setup
    val group: StringTemplateGroup = new UrlStringTemplateGroup("resourceTemplates", baseUrl)

    // execute
    val template: StringTemplate = group.getInstanceOf("parent")

    // verify
    assertNotNull(template)
    assertEquals("what say you child? dont call me child!", template.toString)
  }

  @Test
  def loadsTemplatesFromBaseUrl = {
    // setup
    val group: StringTemplateGroup = new UrlStringTemplateGroup("resourceTemplates", baseUrl)

    // execute
    val template: StringTemplate = group.getInstanceOf("resource")

    // verify
    assertNotNull(template)
    template.setAttribute("bar", "test")
    assertEquals("foo:test", template.toString)
  }

  def baseUrl(): URL = {
    val url = RelativeResource.asUrl(classOf[UrlStringTemplateGroupTest], "resource.st").toString
    val lastSlash: Int = url.lastIndexOf("/resource.st")
    val base = url.subSequence(0, lastSlash).toString
    new URL(base)
  }
}