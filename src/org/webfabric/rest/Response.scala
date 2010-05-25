package org.webfabric.rest

import javax.servlet.http.HttpServletResponse
import java.io.{Writer, ByteArrayOutputStream, OutputStreamWriter, OutputStream}

case class Response(val writer: Writer, val output: OutputStream) {
  def this(output: OutputStream) = this (new OutputStreamWriter(output), output)

  def this() = this (new ByteArrayOutputStream)

  var code: Int = 200
  val headers = HeaderParameters()

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
    Response(response.getWriter, response.getOutputStream)
  }

  def apply(output: OutputStream): Response = {
    new Response(output)
  }
}