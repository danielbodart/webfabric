package org.webfabric.web.sitemesh

import antlr.stringtemplate.language.DefaultTemplateLexer
import com.opensymphony.module.sitemesh.{HTMLPage, RequestConstants}
import io.{Url, Path}
import java.net.URL
import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import antlr.stringtemplate.{StringTemplateGroup, StringTemplate}
import servlet._
import stringtemplate.UrlStringTemplateGroup

class StringTemplateDecoratorServlet extends HttpServlet {
  override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
    val template = getTemplate(request)
    template.setAttribute("params", request.getParameterMap())
    template.setAttribute("base", ContextPath(request))

    val html = getPage(request)

    val templateDecorator = new StringTemplateDecorator(template)
    templateDecorator.Decorate(html, response.getWriter())
  }

  def getTemplate(request: HttpServletRequest): StringTemplate = {
    val possiblePaths = List(OriginalServletPath(request),
      OriginalPathInfo(request),
      ServletPath(request),
      PathInfo(request))

    val validPath = possiblePaths.find(path => path.value() != null).get
    val name = removeExtension(validPath.value())

    val groups = new UrlStringTemplateGroup("decorators", getBaseUrl())
    return groups.getInstanceOf(name)
  }

  def getBaseUrl(): URL = {
    val url: Url = new Url(getServletContext().getResource("/WEB-INF/web.xml"))
    return url.replacePath(url.path.parent.parent).toURL
  }

  def removeExtension(path: String): String = {
    path.replace(".st", "");
  }

  def getPage(request: HttpServletRequest): HTMLPage = {
    request.getAttribute(RequestConstants.PAGE).asInstanceOf[HTMLPage]
  }
}