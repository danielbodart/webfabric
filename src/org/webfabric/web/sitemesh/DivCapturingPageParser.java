package org.webfabric.web.sitemesh;

import com.opensymphony.module.sitemesh.parser.HTMLPageParser;
import com.opensymphony.module.sitemesh.html.State;
import com.opensymphony.module.sitemesh.html.rules.PageBuilder;

public class DivCapturingPageParser extends HTMLPageParser {
    @Override
    protected void addUserDefinedRules(State state, PageBuilder pageBuilder) {
        super.addUserDefinedRules(state, pageBuilder);
        state.addRule(new DivCapturingRule(pageBuilder));
    }
}
