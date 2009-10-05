package org.webfabric.sitemesh

import javax.servlet.http.HttpServletRequest
import org.webfabric.collections.List
import java.util.Map
import v2.PagePropertiesProvider
import v3.ContentPropertiesProvider

class AutoDetectingContentProvider extends ContentProvider{
  val providers = List(
    (request: HttpServletRequest) => { new ContentPropertiesProvider().getContent(request) },
    (request: HttpServletRequest) => { new PagePropertiesProvider().getContent(request) }
    )

  def getContent(request: HttpServletRequest): Option[PropertyMap] = {
    providers.tryPick( provider => {
        try {
          provider(request)
        } catch {
          case _ => None
        }
    })
  }
}