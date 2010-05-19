package org.webfabric.rest

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._
import org.webfabric.rest.RestTest._
import javax.ws.rs._
import core.StreamingOutput
import java.io._
import Request._

class RestTest {
  @Test
  def canGet() {
    val engine = new RestEngine
    engine.add(classOf[Gettable])
    assertThat(engine.handle(get("foo")), is("bar"))
  }

  @Test
  def canGetWithQueryParameter() {
    val engine = new RestEngine
    engine.add(classOf[GettableWithQuery])
    assertThat(engine.handle(get("foo", QueryParameters("name" -> "value"))), is("value"))
  }

  @Test
  def canPostWithFormParameter() {
    val engine = new RestEngine
    engine.add(classOf[Postable])
    assertThat(engine.handle(post("foo", FormParameters("name" -> "value"))), is("value"))
  }

  @Test
  def canHandlePathsOnMethodAsWellAsClass() {
    val engine = new RestEngine
    engine.add(classOf[MutlilplePaths])
    assertThat(engine.handle(get("foo/bar")), is("found"))
  }

  @Test
  def canDetermineMethodWhenThereIsAChoice() {
    val engine = new RestEngine
    engine.add(classOf[MutlilpleGets])
    assertThat(engine.handle(get("foo")), is("no parameters"))
    assertThat(engine.handle(get("foo", QueryParameters("arg" -> "match"))), is("match"))
  }

  @Test
  def canDetermineGetMethodBasedOnMimeType() {
    val engine = new RestEngine
    engine.add(classOf[GetsWithMimeTypes])
    assertThat(engine.handle(Request(HttpMethod.GET, "text", HeaderParameters("Accept" -> "text/plain"), QueryParameters(), FormParameters())), is("plain"))
    assertThat(engine.handle(Request(HttpMethod.GET, "text", HeaderParameters("Accept" -> "text/html"), QueryParameters(), FormParameters())), is("html"))
  }

  @Test
  def canStreamOutput() {
    val engine = new RestEngine
    engine.add(classOf[StreamOutput])
    val out = new ByteArrayOutputStream

    engine.handle(get("foo"), out)

    assertThat(out.toString, is("stream"))
  }

  @Test
  def supportsNoContent() {
    val engine = new RestEngine
    engine.add(classOf[NoContent])
    assertThat(engine.handle(post( "foo", FormParameters())), is(nullValue[String]))
  }

  @Test
  def supportsPathParameter() {
    val engine = new RestEngine
    engine.add(classOf[PathParameter])
    assertThat(engine.handle(get( "path/bar")), is("bar"))
  }
}

object RestTest {
  @Path("foo")
  class Gettable {
    @GET
    def get(): String = {
      "bar"
    }
  }

  @Path("foo")
  class GettableWithQuery {
    @GET
    def get(@QueryParam("name") name: String): String = {
      name
    }
  }

  @Path("foo")
  class Postable {
    @POST
    def post(@FormParam("name") name: String): String = {
      name
    }
  }

  @Path("foo")
  class MutlilplePaths {
    @GET
    @Path("bar")
    def get(): String = {
      "found"
    }
  }

  @Path("foo")
  class MutlilpleGets {
    @GET
    def get(): String = {
      "no parameters"
    }

    @GET
    def get(@QueryParam("arg") arg: String): String = {
      arg
    }
  }

  @Path("text")
  class GetsWithMimeTypes {
    @GET
    @Produces(Array("text/plain"))
    def getPlain(): String = {
      "plain"
    }

    @GET
    @Produces(Array("text/html"))
    def getHtml(): String = {
      "html"
    }
  }

  @Path("foo")
  class StreamOutput {
    @GET
    def get(): StreamingOutput = {
      new StreamingOutput {
        def write(out: OutputStream) = {
          var streamWriter = new OutputStreamWriter(out)
          streamWriter.write("stream")
          streamWriter.flush
        }
      }
    }
  }

  @Path("foo")
  class NoContent {
    var count = 0
    @POST
    def post(): Unit = {
      count = count + 1
    }
  }

  @Path("path/{id}")
  class PathParameter {
    @GET
    def get(@PathParam("id") id:String): String = {
      id
    }
  }
}