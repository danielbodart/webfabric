package org.webfabric.collections

object Iterator {
  def head[T](iterator: java.util.Iterator[T]): T = {
    if (iterator.hasNext) iterator.next else throw new NoSuchElementException
  }

  def headOption[T](iterator: java.util.Iterator[T]): Option[T] = {
    if (iterator.hasNext) Some(iterator.next) else None
  }

  def find[T](iterator: java.util.Iterator[T], predicate: (T) => Boolean): Option[T] = {
    while (iterator.hasNext) {
      val item = iterator.next
      if (predicate(item)) return Some(item)
    }
    None
  }

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

  def pick[T, S](iterator: java.util.Iterator[T], handler: (T) => Option[S]): S = {
    tryPick(iterator, handler) match {
      case Some(result) => result
      case _ => throw new NoSuchElementException
    }
  }

  implicit def toMyIterator[T](scalaIterator:scala.Iterator[T]):java.util.Iterator[T] =
    new java.util.Iterator[T] {
      def remove = throw new UnsupportedOperationException

      def next = scalaIterator.next

      def hasNext = scalaIterator.hasNext
    }

  implicit def toMyIterator[T](enumeration:java.util.Enumeration[T]):java.util.Iterator[T] =
    new java.util.Iterator[T] {
      def remove = throw new UnsupportedOperationException

      def next = enumeration.nextElement

      def hasNext = enumeration.hasMoreElements
    }

  def mkString[T](iterator: java.util.Iterator[T], separator:String):String = mkString(iterator, "", separator, "")
  def mkString[T](iterator: java.util.Iterator[T], start:String, separator:String, end:String):String ={
    val builder = new StringBuilder
    builder.append(start)
    if (iterator.hasNext) builder.append(iterator.next)
    while (iterator.hasNext) {
      builder.append(separator); builder.append(iterator.next)
    }
    builder.append(end)
    builder.toString
  }

  def toList[T](iterator:java.util.Iterator[T]):List[T] = {
    val result = List[T]()
    foreach[T](iterator, result.add(_))
    result
  }

  def forall[T](iterator: java.util.Iterator[T], predicate: (T) => Boolean): Boolean = {
    while (iterator.hasNext) {
      val item = iterator.next
      if (!predicate(item)) return false
    }
    true
  }

  def exists[T](iterator: java.util.Iterator[T], predicate: (T) => Boolean): Boolean = {
    while (iterator.hasNext) {
      val item = iterator.next
      if (predicate(item)) return true
    }
    false
  }
}