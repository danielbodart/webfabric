package org.webfabric.web.sitemesh

import com.opensymphony.module.sitemesh.html.rules.PageBuilder
import com.opensymphony.module.sitemesh.html.util.CharArray
import com.opensymphony.module.sitemesh.html.{BasicRule, Tag}
import java.util.Stack

class DivCapturingRule(pageBuilder: PageBuilder) extends BasicRule("div") {
  val ids: Stack[String] = new Stack[String]

  def process(tag: Tag):Unit = tag.getType match {
    case Tag.OPEN => {
      ensureTagIsNotConsumed(tag)
      if (shouldCapture(tag)) {
        pushContent
      }
      pushId(tag)
    }
    case Tag.CLOSE => {
      val id: String = popId
      if (capturing(id)) {
        val content = popContent
        pageBuilder.addProperty("div." + id, content)
        ensureContentIsNotConsumed(content)
      }
      ensureTagIsNotConsumed(tag)
    }
  }

  def ensureContentIsNotConsumed(content: String) {
    currentBuffer().append(content)
  }

  def popContent: String = {
    context.popBuffer().toString();
  }

  def capturing(id: String): Boolean = {
    id != null
  }

  def popId: String = {
    ids.pop()
  }

  def pushId(tag: Tag) {
    ids.push(tag.getAttributeValue("id", false))
  }

  def pushContent {
    context.pushBuffer(new CharArray(256))
  }

  def shouldCapture(tag: Tag): Boolean = {
    return tag.hasAttribute("id", false)
  }

  def ensureTagIsNotConsumed(tag: Tag) {
    tag.writeTo(currentBuffer())
  }
}
