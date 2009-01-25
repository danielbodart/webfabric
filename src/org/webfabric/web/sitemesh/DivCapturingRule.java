package org.webfabric.web.sitemesh;

import com.opensymphony.module.sitemesh.html.BasicRule;
import com.opensymphony.module.sitemesh.html.Tag;
import com.opensymphony.module.sitemesh.html.rules.PageBuilder;
import com.opensymphony.module.sitemesh.html.util.CharArray;

import java.util.Stack;


public class DivCapturingRule extends BasicRule {
    private final PageBuilder pageBuilder;
    private Stack<String> ids = new Stack<String>();

    public DivCapturingRule(PageBuilder pageBuilder) {
        super("div");
        this.pageBuilder = pageBuilder;
    }

    public void process(Tag tag) {
        if (tag.getType() == Tag.OPEN) {
            ensureTagIsNotConsumed(tag);
            if(shouldCapture(tag)){
               captureContent();
            }
            pushId(tag);
        } else if (tag.getType() == Tag.CLOSE) {
            String id = popId();
            if(capturing(id)){
                String content = popContent();
                pageBuilder.addProperty("div." + id, content);
                ensureContentIsNotConsumed(content);
            }
            ensureTagIsNotConsumed(tag);
        }
    }

    private void ensureContentIsNotConsumed(String content) {
        currentBuffer().append(content);
    }

    private String popContent() {
        return context.popBuffer().toString();
    }

    private boolean capturing(String id) {
        return id != null;
    }

    private String popId() {
        return ids.pop();
    }

    private void pushId(Tag tag) {
        ids.push(tag.getAttributeValue("id", false));
    }

    private void captureContent() {
        context.pushBuffer(new CharArray(256));
    }

    private boolean shouldCapture(Tag tag) {
        return tag.hasAttribute("id", false);
    }

    private void ensureTagIsNotConsumed(Tag tag) {
        tag.writeTo(currentBuffer());
    }


}
