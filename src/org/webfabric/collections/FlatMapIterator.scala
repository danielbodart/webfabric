package org.webfabric.collections

class FlatMapIterator[T,S](iterator:java.util.Iterator[T], handler:(T) => java.lang.Iterable[S]) extends java.util.Iterator[S]{
  var currentIterator:Option[java.util.Iterator[S]] = None

  def hasNext:Boolean = currentIterator match {
    case None => iterator.hasNext match {
      case true => {
        currentIterator = Some(handler(iterator.next).iterator)
        hasNext
      }
      case false => false
    }
    case _ => currentIterator.get.hasNext match {
      case true => true
      case false => {
        currentIterator = None
        hasNext
      }
    }
  }

  def remove = iterator.remove

  def next = hasNext match {
    case true => currentIterator.get.next
    case false => throw new NoSuchElementException
  }
}