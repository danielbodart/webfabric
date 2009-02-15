package org.webfabric.web.sitemesh

import _root_.org.webfabric.web.servlet._
import antlr.stringtemplate.language.DefaultTemplateLexer
import com.opensymphony.module.sitemesh.{HTMLPage, RequestConstants}
import io.{Url, Path}
import java.net.URL
import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import antlr.stringtemplate.{StringTemplateGroup, StringTemplate}
import stringtemplate.UrlStringTemplateGroup

class StringTemplateDecoratorServlet extends HttpServlet {
  override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
    val templateDecorator = new StringTemplateDecorator(getTemplate(request))
    templateDecorator.setBase(ContextPath(request))
    templateDecorator.setQueryString(QueryString(request))

    getPage(request) match {
      case Some(page) => templateDecorator.setPage(page)
      case None =>
    }

    templateDecorator.writeTo(response.getWriter())
  }

  def getTemplate(request: HttpServletRequest): StringTemplate = {
    val possiblePaths = List[Path](OriginalServletPath(request),
      OriginalPathInfo(request),
      ServletPath(request),
      PathInfo(request))

    val validPath = possiblePaths.find(path => path.value() != null).get
    val name = removeExtension(validPath.value())

    val groups = new UrlStringTemplateGroup("decorators", getBaseUrl())
    return groups.getInstanceOf(name)
  }

  def getBaseUrl(): Url = {
    val url: Url = getServletContext().getResource("/WEB-INF/web.xml")
    return url.replacePath(url.path.parent.parent)
  }

  def removeExtension(path: String): String = {
    path.replace(".st", "");
  }

  def getPage(request: HttpServletRequest): Option[HTMLPage] = {
    request.getAttribute(RequestConstants.PAGE) match{
      case page:HTMLPage => Some(page)
      case null => None
    }

  }
}