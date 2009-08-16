package org.webfabric.sitemesh

import com.opensymphony.module.sitemesh.html.State
import com.opensymphony.module.sitemesh.html.rules.PageBuilder

class DivCapturingPageParser extends HtmlParser {
  override def addUserDefinedRules(state: State, pageBuilder: PageBuilder) {
    super.addUserDefinedRules(state, pageBuilder)
    state.addRule(new DivCapturingRule(pageBuilder))
  }
}
