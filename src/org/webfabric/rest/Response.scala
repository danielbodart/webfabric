package org.webfabric.rest

import javax.servlet.http.HttpServletResponse
import java.io.{Writer, ByteArrayOutputStream, OutputStreamWriter, OutputStream}
import java.lang.String

case class Response(val writer: Writer, val output: OutputStream) {
  def this(output: OutputStream) = this (new OutputStreamWriter(output), output)

  def this() = this (new ByteArrayOutputStream)

  var code: Int = 200
  val headers = HeaderParameters()

  def setCode(value:Int):Unit = code = value
  def setHeader(name:String, value:String):Unit = headers.add(name, value)

  def write(value: String): Response = {
    writer.write(value)
    this
  }

  def flush = {
    writer.flush
    output.flush
  }
}

object Response {
  def apply(response: HttpServletResponse): Response = {
    new Response(response.getWriter, response.getOutputStream){
      override def setHeader(name: String, value: String) = response.setHeader(name, value)

      override def setCode(value: Int) = response.setStatus(value)
    }
  }

  def apply(output: OutputStream): Response = {
    new Response(output)
  }
}