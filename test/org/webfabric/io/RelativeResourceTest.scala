package org.webfabric.io

import org.junit.Test;
import org.junit.Assert.{assertNotNull, assertEquals}
import org.webfabric.io.Converter.asString

class RelativeResourceTest {
  @Test
  def supportsLoadingAsStream() {
    // execute
    val stream = RelativeResource.asStream(classOf[RelativeResourceTest], "relative.file")

    // verify
    assertNotNull(stream);
    assertEquals("test file", asString(stream))
  }
}
