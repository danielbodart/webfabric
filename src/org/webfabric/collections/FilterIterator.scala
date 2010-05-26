package org.webfabric.collections

class FilterIterator[T](iterator: java.util.Iterator[T], predicate: (T) => Boolean) extends java.util.Iterator[T] {
  var matched: Option[T] = None

  def hasNext: Boolean = {
    matched match {
      case None => {
        while (iterator.hasNext) {
          val item = iterator.next
          if (predicate(item)) {
            matched = Some(item)
            return true
          }
        }
        false
      }
      case _ => true
    }
  }

  def remove = iterator.remove

  def next = matched match {
    case Some(result) => {
      matched = None
      result
    }
    case None => if(hasNext) next else throw new NoSuchElementException
  }
}