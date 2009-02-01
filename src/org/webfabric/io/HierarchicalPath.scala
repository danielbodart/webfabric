package org.webfabric.io

class HierarchicalPath(segments: Array[String]) extends Path {
  def this(s:String) = this(s.split('/'))

  def value = segments.deepMkString("/")

  def parent: HierarchicalPath = {
    return new HierarchicalPath(segments.take(segments.length - 1));
  }

  def child(name:String):HierarchicalPath = {
    val childSegments: List[String] = segments.toList ::: List(name)
    return new HierarchicalPath(childSegments.toArray)
  }

  override def toString = value

  override def hashCode = value.hashCode

  override def equals(obj: Any) = {
    if(obj == null) false else toString == obj.toString
  }
}
