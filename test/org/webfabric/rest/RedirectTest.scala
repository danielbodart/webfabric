package org.webfabric.rest

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._
import javax.ws.rs.{PathParam, Path}
import org.webfabric.rest.Redirect.resource
import javax.ws.rs.core.StreamingOutput
import org.webfabric.rest.RedirectTest.{NoDefaultConstructor, SomeResource}

class RedirectTest{
  @Test
  def canExtractPath{
    assertThat(Redirect(resource(classOf[SomeResource]).getHtml("foo")).toString, is("path/foo"))
  }

  @Test
  def canExtractPathWithStreamingOutput{
    assertThat(Redirect(resource(classOf[SomeResource]).getStreamingHtml("foo")).toString, is("path/foo"))
  }

//  @Test
//  def canHandleClassWithNoDefaultConstructor{
//    assertThat(Redirect(resource(classOf[NoDefaultConstructor]).getStreamingHtml("foo")).toString, is("path/foo"))
//  }
}

object RedirectTest{

  @Path("path/{id}")
  class SomeResource{
    def getHtml(@PathParam("id") id: String): String = "bob"

    def getStreamingHtml(@PathParam("id") id: String): StreamingOutput = null
  }

  @Path("path/{id}")
  class NoDefaultConstructor(dependancy:SomeResource){
    def getHtml(@PathParam("id") id: String): String = "bob"

    def getStreamingHtml(@PathParam("id") id: String): StreamingOutput = null
  }
}