package org.webfabric.io

trait Path {
  override def toString():String
}

object Path {
  implicit def toString(path:Path) = path.toString
}
