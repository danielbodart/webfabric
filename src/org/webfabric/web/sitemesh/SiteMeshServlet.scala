package org.webfabric.web.sitemesh


import com.opensymphony.module.sitemesh.HTMLPage
import io.{Url, HierarchicalPath}
import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import servlet.{QueryString, ContextPath}
import stringtemplate.{PageMap, UrlStringTemplateGroup, PageLoader, UrlPageLoader}
class SiteMeshServlet extends HttpServlet{
  override def doGet(request: HttpServletRequest, response: HttpServletResponse) = {
    val content = request.getParameter("content")

    new UrlPageLoader().load(content) match {
      case page: HTMLPage => {
        val decoratorUrl = new Url(request.getParameter("decorator"))
        val base: Url = decoratorUrl.parent
        val templateGroup: UrlStringTemplateGroup = new UrlStringTemplateGroup("decorator", base)
        val StringTemplateDecoratorServlet.TemplateName(name) = decoratorUrl.path.file
        val template = templateGroup.getInstanceOf(name)
        val decorator = new StringTemplateDecorator(template)
        decorator.setBase(new ContextPath(base.toString))
        decorator.setInclude(new PageMap(new UrlPageLoader))
        decorator.setPage(page)
        decorator.setQueryString(QueryString(request))
        decorator.writeTo(response.getWriter())
      }
      case _ =>
    }
  }
}