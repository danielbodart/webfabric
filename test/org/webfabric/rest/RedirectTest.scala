package org.webfabric.rest

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._
import javax.ws.rs.{PathParam, Path}
import org.webfabric.rest.Redirect.resource
import org.webfabric.rest.RedirectTest.{NoDefaultConstructor, SomeResource}
import javax.ws.rs.core.{HttpHeaders, StreamingOutput}
import org.webfabric.servlet.{BasePath, ContextPath}

class RedirectTest{
  @Test
  def canExtractPath{
    assertThat(Redirect(resource(classOf[SomeResource]).getHtml("foo")).location, is("path/foo"))
  }

  @Test
  def canExtractPathWithStreamingOutput{
    assertThat(Redirect(resource(classOf[SomeResource]).getStreamingHtml("foo")).location, is("path/foo"))
  }

  @Test
  def canHandleClassWithNoDefaultConstructor{
    assertThat(Redirect(resource(classOf[NoDefaultConstructor]).getStreamingHtml("foo")).location, is("path/foo"))
  }

  @Test
  def canApplyToResponse{
    val response = Response()
    val base = BasePath("")
    Redirect("foo").applyTo(base, response)
    assertThat(response.headers.getValue(HttpHeaders.LOCATION), is("/foo"))
    assertThat(response.code, is(303))
  }
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