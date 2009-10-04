package org.webfabric.sitemesh2

import org.webfabric.servlet.{QueryString, ContextPath}
import java.io.{Writer, StringWriter}
import org.antlr.stringtemplate.{StringTemplate, NoIndentWriter}
import org.webfabric.stringtemplate.PageMap
import com.opensymphony.module.sitemesh.HTMLPage

class StringTemplateDecorator(template: StringTemplate) {
  def setInclude(include: PageMap): StringTemplateDecorator = {
    template.setAttribute("include", include)
    this
  }

  def setBase(base: ContextPath): StringTemplateDecorator = {
    template.setAttribute("base", base)
    this
  }

  def setQueryString(queryString: QueryString): StringTemplateDecorator = {
    template.setAttribute("query", queryString.map)
    this
  }

  def setPage(page: HTMLPage ): StringTemplateDecorator = {
    template.setAttribute("properties", page.getProperties)
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