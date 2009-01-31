package org.webfabric.web.sitemesh

import com.opensymphony.module.sitemesh.HTMLPage
import java.io.Writer
import org.antlr.stringtemplate.{StringTemplate, NoIndentWriter}

class StringTemplateDecorator(template: StringTemplate) {
  def Decorate(html: HTMLPage, writer: Writer) = {
    template.setAttribute("page", html)
    template.setAttribute("head", if (html != null) html.getHead else "")
    template.setAttribute("title", if (html != null) html.getTitle else "")
    template.setAttribute("body", if (html != null) html.getBody else "")
    template.write(new NoIndentWriter(writer))
  }
}