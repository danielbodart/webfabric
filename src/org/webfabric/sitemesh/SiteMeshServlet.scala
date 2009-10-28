package org.webfabric.sitemesh

import org.antlr.stringtemplate.StringTemplate
import org.webfabric.io.{Url}
import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import org.webfabric.servlet.{QueryString, ContextPath}
import org.webfabric.stringtemplate.UrlStringTemplateGroup
import com.opensymphony.module.sitemesh.multipass.DivExtractingPageParser
import v2.DivCapturingPageParser

class SiteMeshServlet extends HttpServlet{
  override def doGet(request: HttpServletRequest, response: HttpServletResponse) = {
    val content = request.getParameter("content")

    new UrlPageLoader(new DivCapturingPageParser).load(content) match {
      case Some(page) => {
        val decoratorUrl = new Url(request.getParameter("decorator"))
        val template: StringTemplate = getTemplate(decoratorUrl)
        val decorator = new StringTemplateDecorator(template)

        decorator.setBase(new ContextPath(decoratorUrl.parent.toString))
        decorator.setInclude(new PageMap)
        decorator.setContent(page)
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