package org.webfabric.collections

class MapIterator[T,S](iterator:java.util.Iterator[T],handler:(T) => S) extends java.util.Iterator[S]{
  def hasNext = iterator.hasNext

  def remove = iterator.remove

  def next = handler(iterator.next)
}