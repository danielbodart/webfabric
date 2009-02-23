package org.webfabric.stringtemplate


import _root_.junit.framework.AssertionFailedError
import com.opensymphony.module.sitemesh.parser.TokenizedHTMLPage
import org.junit.{Test, Assert}
import org.junit.Assert.{assertNotNull, assertEquals, fail}

class PageLoadersTest {
  @Test
  def triesEachLoaderTillSuccessful = {
    // setup
    val unsuccessful = new PageLoader{ def load(path:String ) = None }
    val successful = new PageLoader{ def load(path:String ) = Some(page) }
    val fails = new PageLoader{ def load(path:String ) = throw new AssertionFailedError("Should have stopped before getting here") }
    val loaders = new PageLoaders(unsuccessful, successful, fails)

    // execute & verify
    loaders.load("ignored") match {
      case Some(result) => assertEquals(page, result)
      case _ => fail("should have found 'Some(page)'")
    }
  }

  val page = new TokenizedHTMLPage(new Array[Char](0), null, null)
}