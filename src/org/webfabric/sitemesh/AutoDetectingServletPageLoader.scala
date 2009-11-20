package org.webfabric.sitemesh

import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import org.webfabric.collections.List
import javax.servlet.ServletConfig

class AutoDetectingServletPageLoader(request: HttpServletRequest, response: HttpServletResponse, servletConfig: ServletConfig) extends PageLoader {
  val providers = List(
    (path: String) => { new v2.ServletPageLoader(request, response, servletConfig).load(path) },
    (path: String) => { new v3.ServletPageLoader(request, response, servletConfig).load(path) }
    )

  def load(path: String): Option[PropertyMap] = {
    providers.tryPick( provider => {
        try {
          provider(path)
        } catch {
          case _ => None
        }
    })
  }
}