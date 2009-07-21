package org.webfabric.web.sitemesh

import antlr.stringtemplate.StringTemplate
import com.opensymphony.module.sitemesh.HTMLPage
import io.{Url, HierarchicalPath}
import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import servlet.{QueryString, ContextPath}
import stringtemplate.{PageMap, UrlStringTemplateGroup, UrlPageLoader}

class SiteMeshServlet extends HttpServlet{
  override def doGet(request: HttpServletRequest, response: HttpServletResponse) = {
    val content = request.getParameter("content")

    new UrlPageLoader().load(content) match {
      case page: HTMLPage => {
        val decoratorUrl = new Url(request.getParameter("decorator"))
        val template: StringTemplate = getTemplate(decoratorUrl)
        val decorator = new StringTemplateDecorator(template)

        decorator.setBase(new ContextPath(decoratorUrl.parent.toString))
        decorator.setInclude(new PageMap(new UrlPageLoader))
        decorator.setPage(page)
        decorator.setQueryString(QueryString(request))

        decorator.writeTo(response.getWriter)
      }
      case _ => {
        response.getWriter.write("Unable to load content: " + content )
      }
    }
  }

  def getTemplate(url: Url): StringTemplate = {
    val base = url.parent
    val templates = new UrlStringTemplateGroup("decorator", base)
    val StringTemplateDecoratorServlet.TemplateName(name) = url.path.file
    templates.getInstanceOf(name)
  }

}