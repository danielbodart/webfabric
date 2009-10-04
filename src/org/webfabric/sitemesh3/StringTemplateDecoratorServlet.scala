package org.webfabric.sitemesh3

import javax.servlet.http._
import org.webfabric.stringtemplate._
import org.webfabric.servlet._
import org.webfabric.io.{Url}
import org.antlr.stringtemplate.{StringTemplate}
import org.sitemesh.webapp.WebAppContext
import org.sitemesh.content.{Content}
import java.util.Map
import org.webfabric.sitemesh.StringTemplateDecorator

class StringTemplateDecoratorServlet extends HttpServlet {
  override def doPost(request: HttpServletRequest, response: HttpServletResponse) = {
    handle(request, response)
  }

  override def doGet(request: HttpServletRequest, response: HttpServletResponse) = {
    handle(request, response)
  }

  def handle(request: HttpServletRequest, response: HttpServletResponse) {
    val templateDecorator = new StringTemplateDecorator(getTemplate(request))

    getContent(request) match {
      case Some(content:Map[_,_]) => templateDecorator.setContent(content)
      case None =>
    }

    templateDecorator.setBase(ContextPath(request))
    templateDecorator.setQueryString(QueryString(request))
    templateDecorator.setInclude(getPageMap(request, response))

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

  def getContent(request: HttpServletRequest): Option[Map[_,_]] = {
    request.getAttribute(WebAppContext.CONTENT_KEY) match {
      case content: Content => Some(new ContentPropertyMap(content.getExtractedProperties))
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