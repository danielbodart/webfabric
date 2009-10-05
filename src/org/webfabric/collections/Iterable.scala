package org.webfabric.collections

trait Iterable[T] extends java.lang.Iterable[T] {
  def foreach(handler: (T) => Unit) = Iterable.foreach(this, handler)
  def tryPick[S](handler: (T) => Option[S]): Option[S] = Iterable.tryPick(this, handler)

}

object Iterable {
  def foreach[T](iterable: java.lang.Iterable[T], handler: (T) => Unit) {
    val iterator = iterable.iterator
    while (iterator.hasNext) {
      handler(iterator.next)
    }
  }

  def tryPick[T,S](iterable: java.lang.Iterable[T], handler: (T) => Option[S]): Option[S] = {
    val iterator = iterable.iterator
    while (iterator.hasNext) {
      handler(iterator.next) match {
        case some:Some[_] => return some
        case _ =>
      }
    }
    None
  }
}