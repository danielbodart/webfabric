package org.webfabric.http

import java.util.Date
import javax.servlet.http.{HttpServletResponse, HttpServletResponseWrapper}
import javax.servlet.ServletOutputStream
import java.io.{ByteArrayOutputStream, OutputStream, PrintWriter}

class EtagResponseWrapper(servletResponse: HttpServletResponse) extends HttpServletResponseWrapper(servletResponse){
  lazy val md5 = new MD5
  lazy val buffer = new ByteArrayOutputStream
  lazy val wrapped: OutputStream = md5.wrap(buffer)

  lazy override val getOutputStream = new ServletOutputStream {
    override def write(int: Int) = wrapped.write(int)
  }

  lazy override val getWriter = new PrintWriter(wrapped)

  def writeToUnderlyingResponse() {
    val time = new Date().getTime
    servletResponse.setDateHeader("Date", time)
    servletResponse.setDateHeader("Last-Modified", time)
    servletResponse.setHeader("ETag", etag)
    servletResponse.setHeader("Content-MD5", contentMD5)
    buffer.writeTo(servletResponse.getOutputStream)
  }

  def etag = '"' + md5.asHex + '"'
  def contentMD5 = md5.asBase64
}
