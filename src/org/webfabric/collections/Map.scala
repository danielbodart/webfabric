package org.webfabric.collections

import java.util.HashMap

object Map {
  def apply[K,V](pairs: (K, V)*): java.util.Map[K, V] = {
    val result = new HashMap[K, V]
    pairs.foreach(pair => result.put(pair._1, pair._2))
    result
  }

  implicit def toIterable[K, V](m: java.util.Map[K, V]): Iterable[java.util.Map.Entry[K, V]] =
    new Iterable[java.util.Map.Entry[K, V]] {
      def iterator = m.entrySet.iterator
    }
}