package org.webfabric.sitemesh3

import org.webfabric.servlet.{QueryString, ContextPath}
import org.webfabric.stringtemplate.PageMap
import java.io.{StringWriter, Writer}
import org.antlr.stringtemplate.{NoIndentWriter, StringTemplate}
import org.sitemesh.content.{ContentProperty}

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

  def setPage(content: ContentProperty): StringTemplateDecorator = {
    template.setAttribute("properties", new ContentPropertyMap(content))
    template.setAttribute("title", content.getChild("title").getValue)
    template.setAttribute("head", content.getChild("head").getValue)
    template.setAttribute("body", content.getChild("body").getValue)
    this
  }

  def writeTo(writer: Writer) = template.write(new NoIndentWriter(writer))

  override def toString = {
    val writer = new StringWriter
    writeTo(writer)
    writer.toString
  }
}