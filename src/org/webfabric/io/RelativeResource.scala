package org.webfabric.io

import java.net.URL
import scala.reflect.Manifest
import java.io.InputStream;

object RelativeResource {
  def asStream(aClass:Class[_], relativeFilename: String): InputStream = {
    val loader = aClass.getClassLoader()
    val path = absolutePath(aClass, relativeFilename)
    return loader.getResourceAsStream(path.value)
  }

  def asUrl(aClass:Class[_], relativeFilename: String): Url = {
    val loader = aClass.getClassLoader()
    val path = absolutePath(aClass, relativeFilename)
    return loader.getResource(path.value)
  }

  def absolutePath(aClass:Class[_], relativeFilename: String): HierarchicalPath = {
    val name = packagePath(aClass)
    name.file(relativeFilename)
  }

  def packagePath(aClass:Class[_]): HierarchicalPath = {
    new HierarchicalPath(aClass.getPackage().getName().replace(".", "/"))
  }
}
