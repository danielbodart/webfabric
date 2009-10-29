package org.webfabric.collections

trait Iterable[T] extends java.lang.Iterable[T] {
  def find(predicate: (T) => Boolean): Option[T] = Iterable.find(this, predicate)

  def foldLeft[S](initialValue: S, handler: (S, T) => S) = Iterable.foldLeft(this, initialValue, handler)

  def foreach(handler: (T) => Unit) = Iterable.foreach(this, handler)

  def tryPick[S](converter: (T) => Option[S]): Option[S] = Iterable.tryPick(this, converter)

  def pick[S](converter: (T) => Option[S]): S = Iterable.pick(this, converter)

  def map[S](converter: (T) => S): Iterable[S] = Iterable.map(this, converter)

  def flatMap[S](converter: (T) => java.lang.Iterable[S]): Iterable[S] = Iterable.flatMap(this, converter)

  def filter(predicate: (T) => Boolean): Iterable[T] = Iterable.filter(this, predicate)
}

object Iterable {
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
}