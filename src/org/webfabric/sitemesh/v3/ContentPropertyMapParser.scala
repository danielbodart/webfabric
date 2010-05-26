package org.webfabric.sitemesh.v3

import org.sitemesh.content.tagrules.TagBasedContentProcessor
import org.sitemesh.content.tagrules.html.{CoreHtmlTagRuleBundle, DivExtractingTagRuleBundle}
import java.nio.CharBuffer
import org.webfabric.sitemesh.{PropertyMap, PropertyMapParser}

class ContentPropertyMapParser extends PropertyMapParser {
  def parse(html: String): org.webfabric.sitemesh.PropertyMap = {
    parse(CharBuffer.wrap(html))
  }

  def parse(buffer: CharBuffer): org.webfabric.sitemesh.PropertyMap = {
    val contentProcessor = new TagBasedContentProcessor(new CoreHtmlTagRuleBundle(), new DivExtractingTagRuleBundle());
    new PropertyMap(new ContentPropertyAdapter(contentProcessor.build(buffer, null).getExtractedProperties))
  }
}