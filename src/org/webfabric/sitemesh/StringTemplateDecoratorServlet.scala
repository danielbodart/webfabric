package org.webfabric.sitemesh

import javax.servlet.http._
import org.webfabric.stringtemplate._
import org.webfabric.servlet._
import com.opensymphony.module.sitemesh.{HTMLPage, RequestConstants}
import org.webfabric.io.{Url}
import org.antlr.stringtemplate.{StringTemplate}

class StringTemplateDecoratorServlet extends HttpServlet {
  override def doPost(request: HttpServletRequest, response: HttpServletResponse) = {
    handle(request, response)
  }

  override def doGet(request: HttpServletRequest, response: HttpServletResponse) = {
    handle(request, response)
  }

  def handle(request: HttpServletRequest, response: HttpServletResponse) {
    val templateDecorator = new StringTemplateDecorator(getTemplate(request))
    templateDecorator.setBase(ContextPath(request))
    templateDecorator.setQueryString(QueryString(request))
    templateDecorator.setInclude(getPageMap(request, response))

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

    val StringTemplateDecoratorServlet.TemplateName(path, name) = possiblePaths.find(_ != null).get
    val groups = new UrlStringTemplateGroup("decorators", getBaseUrl(path))
    return groups.getInstanceOf(name)
  }

  def getBaseUrl(path:String): Url = {
    getServletContext().getResource(path)
  }

  def getPage(request: HttpServletRequest): Option[HTMLPage] = {
    request.getAttribute(RequestConstants.PAGE) match {
      case page: HTMLPage => Some(page)
      case _ => None
    }
  }

  def getPageMap(request: HttpServletRequest, response: HttpServletResponse):PageMap = {
    val loaders = new PageLoaders(new UrlPageLoader, new ServletPageLoader(request, response, getServletConfig))
    new PageMap(loaders)
  }
}

object StringTemplateDecoratorServlet {
  val TemplateName = """(?:(.+)/)?(.+)\.st$""".r
}