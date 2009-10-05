package org.webfabric.collections

class List[T] extends java.util.ArrayList[T] with Iterable[T]

object List {
  def apply[T](values: T*): List[T] = {
    val result = new List[T]
    values.foreach(result.add(_))
    result
  }

  def empty[T] = new List[T]
}