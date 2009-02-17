package org.webfabric.web.sitemesh

import _root_.org.webfabric.web.servlet.{QueryString, ContextPath}
import com.opensymphony.module.sitemesh.HTMLPage
import java.io.{Writer, StringWriter}
import org.antlr.stringtemplate.{StringTemplate, NoIndentWriter}

class StringTemplateDecorator(template: StringTemplate) {
  def setBase(base: ContextPath): StringTemplateDecorator = {
    template.setAttribute("base", base)
    this
  }

  def setQueryString(queryString: QueryString): StringTemplateDecorator = {
    template.setAttribute("query", queryString.map)
    this
  }

  def setPage(page: HTMLPage): StringTemplateDecorator = {
    template.setAttribute("page", page)
    template.setAttribute("head", page.getHead)
    template.setAttribute("title", page.getTitle)
    template.setAttribute("body", page.getBody)
    this
  }

  def writeTo(writer: Writer) = template.write(new NoIndentWriter(writer))

  override def toString = {
    val writer = new StringWriter
    writeTo(writer)
    writer.toString
  }
}