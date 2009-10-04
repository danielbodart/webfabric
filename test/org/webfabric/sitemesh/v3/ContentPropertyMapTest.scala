package org.webfabric.sitemesh.v3

import org.junit.Test
import org.junit.Assert.{assertEquals,assertTrue}
import org.sitemesh.content.{ContentProperty}
import org.sitemesh.content.tagrules.TagBasedContentProcessor
import org.sitemesh.content.tagrules.html.{CoreHtmlTagRuleBundle, DivExtractingTagRuleBundle}
import java.nio.CharBuffer
import java.util.Collection

class ContentPropertyMapTest{
  @Test
  def changesValuesToBeValueSoThatStringTemplateWorksCorrectly(): Unit = {
    // setup
    val contentProperties = extractContentProperties("<html><head><title>foo</title></head></html>")
    val map = new ContentPropertyMap(contentProperties)

    // execute & verify
    assertTrue(map.containsKey("title"))
    val values:Collection[Any] = map.get("title").values
    assertEquals(values.size, 1)
    val toArray = values.toArray
    assertEquals(toArray(0), "foo")
  }

  @Test
  def supportsChildProperty(): Unit = {
    // setup
    val contentProperties = extractContentProperties("<html><head><title>foo</title></head></html>")
    val map = new ContentPropertyMap(contentProperties)

    // execute & verify
    assertTrue(map.containsKey("title"))
    assertEquals(map.get("title").toString, "foo")
  }

  def extractContentProperties(html: String): ContentProperty = {
    val contentProcessor = new TagBasedContentProcessor(new CoreHtmlTagRuleBundle(), new DivExtractingTagRuleBundle());
    contentProcessor.build(CharBuffer.wrap(html), null).getExtractedProperties
  }

}