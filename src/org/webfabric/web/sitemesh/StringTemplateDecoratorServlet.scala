package org.webfabric.web.sitemesh

import java.util.regex.Pattern
import stringtemplate.{PageMap, UrlStringTemplateGroup}
import web.servlet._
import com.opensymphony.module.sitemesh.{HTMLPage, RequestConstants}
import io.{Url, Path}
import java.net.URL
import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import antlr.stringtemplate.{StringTemplateGroup, StringTemplate}

class StringTemplateDecoratorServlet extends HttpServlet {
  override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
    val templateDecorator = new StringTemplateDecorator(getTemplate(request))
    templateDecorator.setBase(ContextPath(request))
    templateDecorator.setQueryString(QueryString(request))
    templateDecorator.setInclude(new PageMap)

    getPage(request) match {
      case Some(page) => templateDecorator.setPage(page)
      case None =>
    }

    templateDecorator.writeTo(response.getWriter())
  }

  def getTemplate(request: HttpServletRequest): StringTemplate = {
    val possiblePaths = List[String](OriginalServletPath(request),
      OriginalPathInfo(request),
      ServletPath(request),
      PathInfo(request))

    val StringTemplateDecoratorServlet.TemplateName(name) = possiblePaths.find(_ != null).get
    val groups = new UrlStringTemplateGroup("decorators", getBaseUrl())
    return groups.getInstanceOf(name)
  }

  def getBaseUrl(): Url = {
    getServletContext().getResource("/")
  }

  def getPage(request: HttpServletRequest): Option[HTMLPage] = {
    request.getAttribute(RequestConstants.PAGE) match {
      case page: HTMLPage => Some(page)
      case _ => None
    }
  }
}

object StringTemplateDecoratorServlet {
  private val TemplateName = """(.+)\.st$""".r
}