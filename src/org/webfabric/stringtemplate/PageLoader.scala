package org.webfabric.stringtemplate

import org.webfabric.sitemesh.PropertyMap

trait PageLoader {
  def load(path:String): Option[PropertyMap]
}