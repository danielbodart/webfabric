package org.webfabric.sitemesh2

import com.opensymphony.module.sitemesh.html.rules.PageBuilder
import com.opensymphony.module.sitemesh.html.{BasicRule, Tag}
import java.lang.String

class CrossSiteRequestForgeryRule(pageBuilder: PageBuilder, tokenProvider:TokenProvider) extends BasicRule("form") {
  def process(tag: Tag): Unit = {
    ensureTagIsNotConsumed(tag)
    if (tag.getType == Tag.OPEN) {
      if (shouldAddToken(tag)) {
        addToken(tag)
      }
    }
  }

  def shouldAddToken(tag: Tag): Boolean = {
    tag.getAttributeValue("method", false) match {
      case null => false
      case value => value.toLowerCase match {
        case "post" => true
        case _ => false
      }
    }
  }

  def addToken(tag: Tag) = {
    val action: String = tag.getAttributeValue("action", false)
    val token = tokenProvider.getToken(action)
    currentBuffer.append("<input type=\"hidden\" name=\"csrf.token\" value=\"")
    currentBuffer.append(token)
    currentBuffer.append("\"/>")
    pageBuilder.addProperty("csrf.token:" + action, token)
  }

  def ensureTagIsNotConsumed(tag: Tag) {
    tag.writeTo(currentBuffer)
  }
}