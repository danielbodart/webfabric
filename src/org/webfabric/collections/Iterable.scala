package org.webfabric.collections

trait Iterable[T] extends java.lang.Iterable[T] {
  def head: T = Iterable.head(this)

  def headOption: Option[T] = Iterable.headOption(this)

  def find(predicate: (T) => Boolean): Option[T] = Iterable.find(this, predicate)

  def foldLeft[S](initialValue: S, handler: (S, T) => S) = Iterable.foldLeft(this, initialValue, handler)

  def foreach(handler: (T) => Unit) = Iterable.foreach(this, handler)

  def tryPick[S](converter: (T) => Option[S]): Option[S] = Iterable.tryPick(this, converter)

  def pick[S](converter: (T) => Option[S]): S = Iterable.pick(this, converter)

  def map[S](converter: (T) => S): Iterable[S] = Iterable.map(this, converter)

  def flatMap[S](converter: (T) => java.lang.Iterable[S]): Iterable[S] = Iterable.flatMap(this, converter)

  def filter(predicate: (T) => Boolean): Iterable[T] = Iterable.filter(this, predicate)

  def mkString(start: String, separator: String, end: String): String = Iterable.mkString(this, start, separator, end)

  def mkString(separator: String): String = Iterable.mkString(this, "", separator, "")

  def toList:List[T] = Iterable.toList(this)

  def zip[S](list:java.lang.Iterable[S]): Iterable[(T,S)]  = Iterable.zip(this, list)

  def forall(predicate: (T) => Boolean): Boolean  = Iterable.forall(this, predicate)

  def exists(predicate: (T) => Boolean): Boolean  = Iterable.exists(this, predicate)
}


object Iterable {
  def head[T](iterable: java.lang.Iterable[T]): T =
    Iterator.head(iterable.iterator)

  def headOption[T](iterable: java.lang.Iterable[T]): Option[T] =
    Iterator.headOption(iterable.iterator)

  def find[T](iterable: java.lang.Iterable[T], predicate: (T) => Boolean): Option[T] =
    Iterator.find(iterable.iterator, predicate)

  def foldLeft[T, S](iterable: java.lang.Iterable[T], initialValue: S, handler: (S, T) => S): S =
    Iterator.foldLeft(iterable.iterator, initialValue, handler)

  def foreach[T](iterable: java.lang.Iterable[T], handler: (T) => Unit) =
    Iterator.foreach(iterable.iterator, handler)

  def tryPick[T, S](iterable: java.lang.Iterable[T], converter: (T) => Option[S]): Option[S] =
    Iterator.tryPick(iterable.iterator, converter)

  def pick[T, S](iterable: java.lang.Iterable[T], converter: (T) => Option[S]): S =
    Iterator.pick(iterable.iterator, converter)

  def map[T, S](iterable: java.lang.Iterable[T], converter: (T) => S): Iterable[S] = {
    new Iterable[S] {
      def iterator = new MapIterator[T, S](iterable.iterator, converter)
    }
  }

  def flatMap[T, S](iterable: java.lang.Iterable[T], converter: (T) => java.lang.Iterable[S]): Iterable[S] = {
    new Iterable[S] {
      def iterator = new FlatMapIterator[T, S](iterable.iterator, converter)
    }
  }

  def filter[T](iterable: java.lang.Iterable[T], predicate: (T) => Boolean): Iterable[T] = {
    new Iterable[T] {
      def iterator = new FilterIterator[T](iterable.iterator, predicate)
    }
  }

  implicit def convertScalaIterable[T](scalaIterable: scala.Iterable[T]): Iterable[T] = {
    new Iterable[T] {
      def iterator = Iterator.toMyIterator(scalaIterable.elements)
    }
  }

  implicit def convertEnumeration[T](enumeration: java.util.Enumeration[T]): Iterable[T] = {
    new Iterable[T] {
      def iterator = Iterator.toMyIterator(enumeration)
    }
  }

  def mkString[T](iterable:java.lang.Iterable[T], separator:String):String = mkString(iterable, "", separator, "")
  def mkString[T](iterable:java.lang.Iterable[T], start:String, separator:String, end:String):String ={
    Iterator.mkString(iterable.iterator, start, separator, end)
  }

  def toList[T](iterable:java.lang.Iterable[T]):List[T] = Iterator.toList[T](iterable.iterator)

  def zip[T,S](iterable:java.lang.Iterable[T], anotherIterable:java.lang.Iterable[S]): Iterable[(T,S)]  = {
    new Iterable[(T,S)] {
      def iterator = new ZipIterator[T,S](iterable.iterator, anotherIterable.iterator)
    }
  }

  def forall[T](iterable:java.lang.Iterable[T], predicate: (T) => Boolean): Boolean  = Iterator.forall(iterable.iterator, predicate)

  def exists[T](iterable:java.lang.Iterable[T], predicate: (T) => Boolean): Boolean  = Iterator.exists(iterable.iterator, predicate)


}