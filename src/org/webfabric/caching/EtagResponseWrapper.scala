package org.webfabric.caching


import com.opensymphony.module.sitemesh.util.FastByteArrayOutputStream
import java.io.{OutputStream, PrintWriter}
import javax.servlet.http.{HttpServletResponse, HttpServletResponseWrapper}
import javax.servlet.ServletOutputStream

class EtagResponseWrapper(servletResponse: HttpServletResponse) extends HttpServletResponseWrapper(servletResponse){
  lazy val md5 = new MD5
  lazy val buffer = new FastByteArrayOutputStream
  lazy val wrapped: OutputStream = md5.wrap(buffer)

  lazy override val getOutputStream = new ServletOutputStream {
    override def write(int: Int) = wrapped.write(int)
  }

  lazy override val getWriter = new PrintWriter(wrapped)

  def writeToUnderlyingResponse() {
    servletResponse.setHeader("ETag", etag)
    servletResponse.setHeader("Content-MD5", contentMD5)
    buffer.writeTo(servletResponse.getOutputStream)
  }

  def etag = md5.asHex
  def contentMD5 = md5.asBase64
}
