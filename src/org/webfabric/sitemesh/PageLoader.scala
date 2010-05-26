package org.webfabric.sitemesh

trait PageLoader {
  def load(path:String): Option[PropertyMap]
}