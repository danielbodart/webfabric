package org.webfabric.rest

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._
import org.webfabric.rest.RestTest._
import javax.ws.rs._
import core.StreamingOutput
import java.io._

class RestTest {
  @Test
  def canGet() {
    val engine = new RestEngine
    engine.add(classOf[Gettable])
    assertThat(engine.get("foo"), is("bar"))
  }

  @Test
  def canGetWithQueryParameter() {
    val engine = new RestEngine
    engine.add(classOf[GettableWithQuery])
    assertThat(engine.get("foo", QueryParameters("name" -> "value")), is("value"))
  }

  @Test
  def canPostWithFormParameter() {
    val engine = new RestEngine
    engine.add(classOf[Postable])
    assertThat(engine.post("foo", QueryParameters(), FormParameters("name" -> "value")), is("value"))
  }

  @Test
  def canHandlePathsOnMethodAsWellAsClass() {
    val engine = new RestEngine
    engine.add(classOf[MutlilplePaths])
    assertThat(engine.get("foo/bar"), is("found"))
  }

  @Test
  def canDetermineMethodWhenThereIsAChoice() {
    val engine = new RestEngine
    engine.add(classOf[MutlilpleGets])
    assertThat(engine.get("foo"), is("no parameters"))
    assertThat(engine.get("foo", QueryParameters("arg" -> "match")), is("match"))
  }

  @Test
  def canStreamOutput() {
    val engine = new RestEngine
    engine.add(classOf[StreamOutput])
    val out = new ByteArrayOutputStream

    engine.handle("GET", "foo", QueryParameters(), FormParameters(), out)

    assertThat(out.toString, is("stream"))
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
}