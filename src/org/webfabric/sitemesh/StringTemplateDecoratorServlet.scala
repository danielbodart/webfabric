package org.webfabric.sitemesh

import javax.servlet.http._
import org.webfabric.stringtemplate._
import org.webfabric.servlet._
import org.webfabric.io.{Url}
import org.antlr.stringtemplate.{StringTemplate}

class StringTemplateDecoratorServlet extends HttpServlet {
  val contentProvider = new AutoDetectingContentProvider()
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

    contentProvider.getContent(request) match {
      case Some(content) => templateDecorator.setContent(content)
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

  def getPageMap(request: HttpServletRequest, response: HttpServletResponse):PageMap = {
    val loaders = new PageLoaders(new UrlPageLoader, new v2.ServletPageLoader(request, response, getServletConfig))
    new PageMap(loaders)
  }
}

object StringTemplateDecoratorServlet {
  val TemplateName = """(?:(.+)/)?(.+)\.st$""".r
}