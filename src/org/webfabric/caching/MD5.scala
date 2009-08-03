package org.webfabric.caching

import java.io.{InputStream, OutputStream}
import java.lang.String
import java.math.BigInteger
import java.security.{DigestInputStream, DigestOutputStream, MessageDigest}
import sun.misc.BASE64Encoder;

class MD5{
  val digest = MessageDigest.getInstance("MD5")

  def wrap(outputStream: OutputStream): OutputStream = {
    new DigestOutputStream(outputStream, digest)
  }

  def wrap(inputStream: InputStream): InputStream = {
    new DigestInputStream(inputStream, digest)
  }

  def asHex:String = {
    new BigInteger(1, bytes).toString(16)
  }

  def asBase64:String = {
    new BASE64Encoder().encode(bytes)
  }

  lazy val bytes = digest.digest
}
