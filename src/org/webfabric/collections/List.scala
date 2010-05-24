package org.webfabric.collections

import java.util.{Comparator, Collections}

class List[T](collection: java.util.Collection[T]) extends java.util.ArrayList[T](collection) with Iterable[T] {
  def this() = this (new java.util.ArrayList[T])

  def sort(sorter: (T, T) => Boolean):List[T] = {
    val copy = this.toList
    Collections.sort(copy, new Comparator[T] {
      def compare(first: T, second: T) = {
        if (sorter(first, second)) -1 else 1
      }
    })
    copy
  }
}

object List {
  def apply[T](values: T*): List[T] = {
    val result = new List[T]
    values.foreach(result.add(_))
    result
  }

  def empty[T] = new List[T]
}