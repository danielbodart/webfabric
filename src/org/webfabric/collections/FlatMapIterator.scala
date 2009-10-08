package org.webfabric.collections

class FlatMapIterator[T, S](iterator: java.util.Iterator[T], handler: (T) => java.lang.Iterable[S]) extends java.util.Iterator[S] {
  var currentIterator: Option[java.util.Iterator[S]] = None

  final def hasNext: Boolean = currentIterator match {
    case None => iterator.hasNext && {
      currentIterator = Some(handler(iterator.next).iterator)
      hasNext
    }
    case Some(it) => it.hasNext || {
      currentIterator = None
      hasNext
    }
  }

  def remove = currentIterator.get.remove

  def next = hasNext match {
    case true => currentIterator.get.next
    case false => throw new NoSuchElementException
  }
}