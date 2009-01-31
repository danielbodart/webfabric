package org.webfabric.io

import org.junit.Test;
import org.junit.Assert.{assertNotNull, assertEquals}
import org.webfabric.io.Converter.asString

class RelativeResourceLoaderTest {
  @Test
  def supportsLoadingAsStream() {
    // execute
    val stream = RelativeResourceLoader.load[RelativeResourceLoaderTest]("relative.file")

    // verify
    assertNotNull(stream);
    assertEquals("test file", asString(stream))
  }
}
