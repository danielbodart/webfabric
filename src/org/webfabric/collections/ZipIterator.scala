package org.webfabric.collections


class ZipIterator[T,S](iterator:java.util.Iterator[T], anotherIterator:java.util.Iterator[S]) extends java.util.Iterator[(T,S)] {
  def remove = {
    iterator.remove
    anotherIterator.remove
  }

  def next = (iterator.next, anotherIterator.next)

  def hasNext = iterator.hasNext && anotherIterator.hasNext
}