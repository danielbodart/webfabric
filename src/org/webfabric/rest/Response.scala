package org.webfabric.rest

import java.io.{ByteArrayOutputStream, OutputStreamWriter, OutputStream}
import javax.servlet.http.HttpServletResponse

case class Response(val output: OutputStream) {
  var code:Int = 200
  val headers = HeaderParameters()
  def this() = this (new ByteArrayOutputStream)

  def write(value: String): Response = {
    var streamWriter = new OutputStreamWriter(output)
    streamWriter.write(value)
    streamWriter.flush
    this
  }
}

object Response{
  def apply(response:HttpServletResponse):Response = {
    Response(response.getOutputStream)
  }
}