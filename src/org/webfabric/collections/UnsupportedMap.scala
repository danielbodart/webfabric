package org.webfabric.collections

import java.util.{Map, Collection}

class UnsupportedMap[Key, Value] extends Map[Key, Value] {
  def values:Collection[Value] = throw new UnsupportedOperationException

  def size = throw new UnsupportedOperationException

  def remove(key: Any) = throw new UnsupportedOperationException

  def putAll(m: Map[_ <: Key, _ <: Value]) = throw new UnsupportedOperationException

  def put(key: Key, value: Value) = throw new UnsupportedOperationException

  def keySet = throw new UnsupportedOperationException

  def isEmpty = throw new UnsupportedOperationException

  def get(key: Any):Value = throw new UnsupportedOperationException

  def entrySet = throw new UnsupportedOperationException

  def containsValue(value: Any):Boolean = throw new UnsupportedOperationException

  def containsKey(key: Any):Boolean = throw new UnsupportedOperationException

  def clear = throw new UnsupportedOperationException
}