package org.webfabric.sitemesh2

import com.opensymphony.module.sitemesh.html.rules.PageBuilder
import com.opensymphony.module.sitemesh.html.State

class CrossSiteRequestForgeryParser(tokenProvider: TokenProvider) extends HtmlParser{
  override def addUserDefinedRules(state: State, pageBuilder: PageBuilder) = {
    state.addRule(new CrossSiteRequestForgeryRule(pageBuilder, tokenProvider))
  }
}