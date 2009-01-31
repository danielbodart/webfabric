package org.webfabric.web.sitemesh

import com.opensymphony.module.sitemesh.parser.HTMLPageParser
import com.opensymphony.module.sitemesh.html.State
import com.opensymphony.module.sitemesh.html.rules.PageBuilder

class DivCapturingPageParser extends HTMLPageParser {
  override def addUserDefinedRules(state: State, pageBuilder: PageBuilder) {
    super.addUserDefinedRules(state, pageBuilder)
    state.addRule(new DivCapturingRule(pageBuilder))
  }
}
