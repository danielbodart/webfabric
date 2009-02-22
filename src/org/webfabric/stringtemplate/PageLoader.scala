package org.webfabric.stringtemplate

import com.opensymphony.module.sitemesh.HTMLPage

trait PageLoader {
  def load(path:String): Option[HTMLPage]
}