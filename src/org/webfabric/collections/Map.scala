package org.webfabric.collections

import java.util.HashMap

object Map {
  def apply[K,V](pairs: (K, V)*): java.util.Map[K, V] = {
    val result = new HashMap[K, V]
    pairs.foreach(pair => result.put(pair._1, pair._2))
    result
  }

  implicit def toIterable[K, V](m: java.util.Map[K, V]): Iterable[(K, V)] =
    new Iterable[(K, V)] {
      def iterator = new java.util.Iterator[(K,V)] {
        val iterator = m.entrySet.iterator

        def remove = iterator.remove

        def next = {
          val entry = iterator.next
          (entry.getKey, entry.getValue)
        }

        def hasNext = iterator.hasNext
      }
    }
}