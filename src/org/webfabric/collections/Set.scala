package org.webfabric.collections

import java.util.HashSet

object Set {
  def apply[T](items:T *): java.util.Set[T] = {
    val result = new HashSet[T]
    items.foreach(item => result.add(item))
    result
  }
}