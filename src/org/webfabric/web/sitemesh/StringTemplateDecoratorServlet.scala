package org.webfabric.web.sitemesh

import antlr.stringtemplate.language.DefaultTemplateLexer
import com.opensymphony.module.sitemesh.{HTMLPage, RequestConstants}
import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import io.Path
import antlr.stringtemplate.{StringTemplateGroup, StringTemplate}
import servlet._

class StringTemplateDecoratorServlet extends HttpServlet {
  override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
    var template = getTemplate(request)
    template.setAttribute("params", request.getParameterMap())
    template.setAttribute("base", ContextPath(request))

    var html = getPage(request)

    var templateDecorator = new StringTemplateDecorator(template)
    templateDecorator.Decorate(html, response.getWriter())
  }

  def getTemplate(request: HttpServletRequest): StringTemplate = {
    var possiblePaths = List(OriginalServletPath(request),
      OriginalPathInfo(request),
      ServletPath(request),
      PathInfo(request))

    var validPath = possiblePaths.find(path => path.value() != null).get
    var name = removeExtension(validPath.value())

    var groups = new StringTemplateGroup("decorators", getRootPath())
    return groups.getInstanceOf(name)
  }

  def getRootPath(): String = {
    getServletContext().getRealPath("")
  }

  def removeExtension(path: String): String = {
    path.replace(".st", "");
  }

  def getPage(request: HttpServletRequest): HTMLPage = {
    request.getAttribute(RequestConstants.PAGE).asInstanceOf[HTMLPage]
  }
}