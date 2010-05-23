package org.webfabric.rest

import java.io.{ByteArrayOutputStream, OutputStreamWriter, OutputStream}

case class Response(val output: OutputStream) {
  var code:Int = 200
  def this() = this (new ByteArrayOutputStream)

  def write(value: String): Response = {
    var streamWriter = new OutputStreamWriter(output)
    streamWriter.write(value)
    streamWriter.flush
    this
  }
}