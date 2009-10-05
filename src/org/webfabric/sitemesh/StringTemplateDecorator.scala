package org.webfabric.sitemesh

import org.webfabric.servlet.{QueryString, ContextPath}
import java.io.{Writer, StringWriter}
import org.antlr.stringtemplate.{StringTemplate, NoIndentWriter}
import org.webfabric.stringtemplate.PageMap
import java.util.Map

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

  def setContent(properties: PropertyMap ): StringTemplateDecorator = {
    template.setAttribute("properties", properties)
    template.setAttribute("head", properties.get("head"))
    template.setAttribute("title", properties.get("title"))
    template.setAttribute("body", properties.get("body"))
    this
  }

  def writeTo(writer: Writer) = template.write(new NoIndentWriter(writer))

  override def toString = {
    val writer = new StringWriter
    writeTo(writer)
    writer.toString
  }
}