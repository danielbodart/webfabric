package org.webfabric.caching

import apache.commons.codec.binary.{Hex, Base64}
import java.io.{InputStream, OutputStream}
import java.lang.String
import java.math.BigInteger
import java.security.{DigestInputStream, DigestOutputStream, MessageDigest}

class MD5{
  val digest = MessageDigest.getInstance("MD5")

  def wrap(outputStream: OutputStream): OutputStream = {
    new DigestOutputStream(outputStream, digest)
  }

  def wrap(inputStream: InputStream): InputStream = {
    new DigestInputStream(inputStream, digest)
  }

  lazy val asHex:String = {
    new String(Hex.encodeHex(bytes))
  }

  lazy val asBase64:String = {
    new String(Base64.encodeBase64(bytes))
  }

  lazy val bytes: Array[byte] = digest.digest
}
