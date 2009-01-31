package org.webfabric.io

import java.net.URL
import scala.reflect.Manifest
import java.io.InputStream;

object RelativeResource {
  def asStream(aClass:Class[_], relativeFilename: String): InputStream = {
    val loader = aClass.getClassLoader()
    val path = absolutePath(aClass, relativeFilename)
    return loader.getResourceAsStream(path)
  }

  def asUrl(aClass:Class[_], relativeFilename: String): URL = {
    val loader = aClass.getClassLoader()
    val path = absolutePath(aClass, relativeFilename)
    return loader.getResource(path)
  }

  def absolutePath(aClass:Class[_], relativeFilename: String): String = {
    val name = packagePath(aClass)
    name + "/" + relativeFilename
  }

  def packagePath(aClass:Class[_]): String = {
    aClass.getPackage().getName().replace(".", "/")
  }
}
