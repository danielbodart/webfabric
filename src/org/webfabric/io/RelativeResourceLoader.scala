package org.webfabric.io

;

import scala.reflect.Manifest
import java.io.InputStream;

object RelativeResourceLoader {
  def load[T](relativeFilename: String)(implicit manifest: Manifest[T]): InputStream = {
    val aClass = manifest.erasure
    var name = aClass.getPackage().getName();
    name = name.replace(".", "/");
    val resource = name + "/" + relativeFilename;
    return aClass.getClassLoader().getResourceAsStream(resource);
  }
}
