package org.webfabric.sitemesh

import v2.DivCapturingPageParser
import v3.ContentPropertyMapParser
import org.webfabric.collections.List

class AutoDetectingPropertyMapParser extends PropertyMapParser{
  val providers = List(
    (html:String) => { new ContentPropertyMapParser().parse(html) },
    (html:String) => { new DivCapturingPageParser().parse(html) }
    )

  def parse(html: String) = {
    providers.pick( provider => {
        try {
          Some(provider(html))
        } catch {
          case _ => None
        }
    })
  }
}