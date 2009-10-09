package org.webfabric.collections

class List[T](collection: java.util.Collection[T]) extends java.util.ArrayList[T](collection) with Iterable[T]{
  def this() = this(new java.util.ArrayList[T])
}

object List {
  def apply[T](values: T*): List[T] = {
    val result = new List[T]
    values.foreach(result.add(_))
    result
  }

  def empty[T] = new List[T]
}