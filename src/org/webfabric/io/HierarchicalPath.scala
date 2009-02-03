package org.webfabric.io

class HierarchicalPath(path: String) extends Path {
  def this(list: List[String]) = this(list.reverse.mkString("/") + "/")

  def value = path

  private def segments = path.split('/').toList.reverse

  def parent: HierarchicalPath = {
    return new HierarchicalPath(if(segments.isEmpty) List() else segments.tail);
  }

  def child(name: String): HierarchicalPath = {
    return new HierarchicalPath(name :: segments)
  }

  override def toString = value

  override def hashCode = value.hashCode

  override def equals(other: Any) = other match {
    case that: Path => this.value == that.value
    case _ => false
  }
}
