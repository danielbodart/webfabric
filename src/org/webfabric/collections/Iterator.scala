package org.webfabric.collections

object Iterator {
  def foldLeft[T, S](iterator: java.util.Iterator[T], initialValue: S, handler: (S, T) => S): S = {
    var accumilator = initialValue
    while (iterator.hasNext) {
      accumilator = handler(accumilator, iterator.next)
    }
    accumilator
  }

  def foreach[T](iterator: java.util.Iterator[T], handler: (T) => Unit) {
    while (iterator.hasNext) {
      handler(iterator.next)
    }
  }

  def tryPick[T, S](iterator: java.util.Iterator[T], handler: (T) => Option[S]): Option[S] = {
    while (iterator.hasNext) {
      handler(iterator.next) match {
        case some: Some[_] => return some
        case _ =>
      }
    }
    None
  }
}