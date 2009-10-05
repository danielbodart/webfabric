package org.webfabric.stringtemplate

import org.webfabric.sitemesh.PropertyMap

class PageLoaders(list:PageLoader*) extends PageLoader{
  def load(path:String): Option[PropertyMap] = {
    for( loader <- list ) {
      loader.load(path) match {
        case Some(page) => return Some(page)
        case _ =>
      }
    }
    None
  }
}