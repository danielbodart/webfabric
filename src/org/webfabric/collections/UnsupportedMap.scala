package org.webfabric.collections

import java.util.{Collection, Set}

class UnsupportedMap[Key, Value] extends java.util.Map[Key, Value] {
  def values:Collection[Value] = throw new UnsupportedOperationException

  def size:Int = throw new UnsupportedOperationException

  def remove(key: Any):Value = throw new UnsupportedOperationException

  def putAll(m: java.util.Map[_ <: Key, _ <: Value]):Unit = throw new UnsupportedOperationException

  def put(key: Key, value: Value):Value = throw new UnsupportedOperationException

  def keySet:Set[Key] = throw new UnsupportedOperationException

  def isEmpty:Boolean = throw new UnsupportedOperationException

  def get(key: Any):Value = throw new UnsupportedOperationException

  def entrySet: Set[java.util.Map.Entry[Key, Value]] = throw new UnsupportedOperationException

  def containsValue(value: Any):Boolean = throw new UnsupportedOperationException

  def containsKey(key: Any):Boolean = throw new UnsupportedOperationException

  def clear:Unit = throw new UnsupportedOperationException
}