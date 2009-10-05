package org.webfabric.collections

class List[T] extends java.util.ArrayList[T] with Iterable[T]

object List {
  def apply[T](values: T*): List[T] = {
    val result = new List[T]
    for (value <- values) result.add(value)
    result
  }

  def empty[T] = new List[T]
}