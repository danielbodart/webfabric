package org.webfabric.sitemesh

import org.junit.{Test}
import org.junit.Assert.{assertEquals, fail}

class PageLoadersTest {
  @Test
  def triesEachLoaderTillSuccessful = {
    // setup
    val unsuccessful = new PageLoader{ def load(path:String ) = None }
    val successful = new PageLoader{ def load(path:String ) = Some(page) }
    val fails = new PageLoader{ def load(path:String ) = throw new AssertionError("Should have stopped before getting here") }
    val loaders = new PageLoaders(unsuccessful, successful, fails)

    // execute & verify
    loaders.load("ignored") match {
      case Some(result) => assertEquals(page, result)
      case _ => fail("should have found 'Some(page)'")
    }
  }

  val page = new PropertyMap(null)
}