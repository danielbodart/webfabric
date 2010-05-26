package org.webfabric.rest

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._
import javax.ws.rs.{PathParam, Path}
import org.webfabric.rest.RedirectTest.SomeResource
import org.webfabric.rest.Redirect.resource
import javax.ws.rs.core.StreamingOutput

class RedirectTest{
  @Test
  def canExtractPath{
    assertThat(Redirect(resource(classOf[SomeResource]).getHtml("foo")).toString, is("path/foo"))
  }

  @Test
  def canExtractPathWithStreamingOutput{
    assertThat(Redirect(resource(classOf[SomeResource]).getStreamingHtml("foo")).toString, is("path/foo"))
  }
}

object RedirectTest{

  @Path("path/{id}")
  trait SomeResource{
    def getHtml(@PathParam("id") id: String): String

    def getStreamingHtml(@PathParam("id") id: String): StreamingOutput
  }
}