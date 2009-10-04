package org.webfabric.collections

class List[T] extends java.util.ArrayList[T]{
  def foreach(handler: (T) => Unit) = List.foreach(this, handler)
  def tryPick[S](handler: (T) => Option[S]): Option[S] = List.tryPick(this, handler)
}

object List {
  def apply[T](values: T*): List[T] = {
    val result = new List[T]
    for (value <- values) result.add(value)
    result
  }

  def foreach[T](list: java.util.List[T], handler: (T) => Unit) {
    val iter = list.iterator
    while (iter.hasNext) {
      handler(iter.next)
    }
  }

  def tryPick[T,S](list: java.util.List[T], handler: (T) => Option[S]): Option[S] = {
    val iter = list.iterator
    while (iter.hasNext) {
      handler(iter.next) match {
        case some:Some[_] => return some
        case _ =>
      }
    }
    None
  }
}