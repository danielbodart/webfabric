package org.webfabric.caching

import io.RelativeResource
import java.io._
import org.junit.{Test}
import org.junit.Assert.{assertEquals}

class MD5Test {
  @Test
  def canCalculateDigestOnDataPassedThroughOutputStream {
    // setup
    val md5 = new MD5

    // execute
    val wrapped = md5.wrap(new ByteArrayOutputStream)
    wrapped.write("abc".getBytes)

    // verify
    assertEquals("900150983cd24fb0d6963f7d28e17f72", md5.asHex)
  }

  @Test
  def canCalculateDigestOnDataPassedThroughInputStream {
    // setup
    val md5 = new MD5

    // execute
    val inputStream = md5.wrap(RelativeResource.asStream(getClass, "abc.dat"))
    while (inputStream.read() != -1) {}

    // verify
    assertEquals("900150983cd24fb0d6963f7d28e17f72", md5.asHex)
  }

  @Test
  def supportsEmptyStream {
    // setup
    val md5 = new MD5

    // execute
    val wrapped = md5.wrap(new ByteArrayOutputStream)

    // verify
    assertEquals("d41d8cd98f00b204e9800998ecf8427e", md5.asHex)
  }
  
  @Test
  def returnsSameDigestOnMultipleCalls {
    // setup
    val md5 = new MD5

    // execute
    val wrapped = md5.wrap(new ByteArrayOutputStream)
    wrapped.write("abc".getBytes)

    // verify
    assertEquals("900150983cd24fb0d6963f7d28e17f72", md5.asHex)
    assertEquals("900150983cd24fb0d6963f7d28e17f72", md5.asHex)
  }

}