package org.webfabric.io

class HierarchicalPath(val value:String) extends Path {
  private def segments = value.split('/').toList.reverse
  private def joinDirectories(list: List[String]) = join(list) + "/"
  private def join(list: List[String]) = list.reverse.mkString("/")

  def parent: HierarchicalPath = toString match {
    case "" => this
    case "/" => this
    case _ => new HierarchicalPath(joinDirectories(segments.tail))
  }

  def subDirectory(name: String): HierarchicalPath = {
    return new HierarchicalPath(joinDirectories(name :: segments))
  }

  def file(name: String): HierarchicalPath = {
    return new HierarchicalPath(join(name :: segments))
  }

  override def toString = value

  override def hashCode = value.hashCode

  override def equals(other: Any) = other match {
    case that: Path => this.toString == that.toString
    case _ => false
  }
}

object HierarchicalPath{
  def apply(value:String):HierarchicalPath = {
    new HierarchicalPath(duplicateSeperators.replaceAllIn(value, "/"))
  }

  private val duplicateSeperators = """\/+""".r
}
