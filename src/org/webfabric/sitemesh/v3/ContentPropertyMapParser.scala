package org.webfabric.sitemesh.v3

import org.webfabric.sitemesh.PropertyMapParser
import org.sitemesh.content.tagrules.TagBasedContentProcessor
import org.sitemesh.content.tagrules.html.{CoreHtmlTagRuleBundle, DivExtractingTagRuleBundle}
import java.nio.CharBuffer

class ContentPropertyMapParser extends PropertyMapParser {
  def parse(html: String): org.webfabric.sitemesh.PropertyMap = {
    val contentProcessor = new TagBasedContentProcessor(new CoreHtmlTagRuleBundle(), new DivExtractingTagRuleBundle());
    new ContentPropertyMap(contentProcessor.build(CharBuffer.wrap(html), null).getExtractedProperties)
  }
}