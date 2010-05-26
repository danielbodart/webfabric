package org.webfabric.io

import java.io.{InputStreamReader, Reader, InputStream}

object Converter {
  def asString(stream: InputStream): String = {
    return asString(new InputStreamReader(stream))
  }

  def asString(reader: Reader): String = {
    val builder = new StringBuilder()
    val buffer = new Array[Char](512)
    var read = 0;
    read = reader.read(buffer)
    while (read > 0) {
      builder.append(buffer, 0, read);
      read = reader.read(buffer)
    }
    return builder.toString();
  }
}
