package org.webfabric.collections

class FilterIterator[T](iterator:java.util.Iterator[T], predicate:(T) => Boolean) extends java.util.Iterator[T]{
  var matched:Option[T] = None

  def hasNext:Boolean = {
    while(iterator.hasNext){
      val item = iterator.next
      if( predicate(item)) {
        matched = Some(item)
        return true
      }
    }
    false
  }

  def remove = iterator.remove

  def next = hasNext match {
    case true => matched.get
    case false => throw new NoSuchElementException
  }
}