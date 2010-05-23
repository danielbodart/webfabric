package org.webfabric.rest

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._
import org.webfabric.io.Converter.asString
import org.webfabric.rest.RestTest._
import javax.ws.rs._
import core.StreamingOutput
import java.io._
import Request._
import com.googlecode.yadic.SimpleContainer

class RestTest {
  @Test
  def canGet() {
    val engine = new TestEngine
    engine.add(classOf[Gettable])
    assertThat(engine.handle(get("foo")), is("bar"))
  }

  @Test
  def leadingSlashInPathShouldNotChangeMatch() {
    val engine = new TestEngine
    engine.add(classOf[Gettable])
    assertThat(engine.handle(get("/foo")), is("bar"))
  }

  @Test
  def canGetWithQueryParameter() {
    val engine = new TestEngine
    engine.add(classOf[GettableWithQuery])
    assertThat(engine.handle(get("foo", QueryParameters("name" -> "value"))), is("value"))
  }

  @Test
  def canPostWithFormParameter() {
    val engine = new TestEngine
    engine.add(classOf[Postable])
    assertThat(engine.handle(post("foo", FormParameters("name" -> "value"))), is("value"))
  }

  @Test
  def canHandlePathsOnMethodAsWellAsClass() {
    val engine = new TestEngine
    engine.add(classOf[MutlilplePaths])
    assertThat(engine.handle(get("foo/bar")), is("found"))
  }

  @Test
  def canDetermineMethodWhenThereIsAChoice() {
    val engine = new TestEngine
    engine.add(classOf[MutlilpleGets])
    assertThat(engine.handle(get("foo")), is("no parameters"))
    assertThat(engine.handle(get("foo", QueryParameters("arg" -> "match"))), is("match"))
  }

  @Test
  def canDetermineGetMethodBasedOnMimeType() {
    val engine = new TestEngine
    engine.add(classOf[GetsWithMimeTypes])
    assertThat(engine.handle(Request(HttpMethod.GET, "text", HeaderParameters("Accept" -> "text/plain"), QueryParameters(), FormParameters(), Request.emptyInput)), is("plain"))
    assertThat(engine.handle(Request(HttpMethod.GET, "text", HeaderParameters("Accept" -> "text/html"), QueryParameters(), FormParameters(), Request.emptyInput)), is("html"))
  }

  @Test
  def setsResponseMimeType() {
    val engine = new TestEngine
    engine.add(classOf[GetsWithMimeTypes])

    var response = new Response
    engine.handle(Request(HttpMethod.GET, "text", HeaderParameters("Accept" -> "text/plain"), QueryParameters(), FormParameters(), Request.emptyInput), response)
    assertThat(response.headers.getValue("Content-Type"), is("text/plain"))
  }

  @Test
  @Ignore
  def canHandleRealWorldAcceptsHeader() {
    val engine = new TestEngine
    engine.add(classOf[GetsWithMimeTypes])
    val accepts = """application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5"""

    var response = new Response
    assertThat(engine.handle(Request(HttpMethod.GET, "text", HeaderParameters("Accept" -> accepts), QueryParameters(), FormParameters(), Request.emptyInput)), is("xml"))
  }

  @Test
  def canStreamOutput() {
    val engine = new TestEngine
    engine.add(classOf[StreamOutput])
    assertThat(engine.handle(get("foo")), is("stream"))
  }

  @Test
  def supportsNoContent() {
    val engine = new TestEngine
    engine.add(classOf[NoContent])
    val response = new Response()
    engine.handle(post( "foo", FormParameters()), response)
    assertThat(response.code, is(204))
  }

  @Test
  def supportsPathParameter() {
    val engine = new TestEngine
    engine.add(classOf[PathParameter])
    assertThat(engine.handle(get( "path/bar")), is("bar"))
  }

  @Test
  def supportsDelete() {
    val engine = new TestEngine
    engine.add(classOf[DeleteContent])
    val response = new Response()
    engine.handle(delete( "path/bar"), response)
    assertThat(response.code, is(204))
  }

  @Test
  def supportsPut() {
    val engine = new TestEngine
    engine.add(classOf[PutContent])

    val input = new ByteArrayInputStream("input".getBytes)
    assertThat(engine.handle(put( "path/bar", input)), is("input"))
  }

  @Test
  def canDetermineInputHandlerByMimeType() {
    val engine = new TestEngine
    engine.add(classOf[MultiplePutContent])

    assertThat(engine.handle(Request(HttpMethod.PUT, "text", HeaderParameters("Content-Type" -> "text/plain"), QueryParameters(), FormParameters(), Request.emptyInput)), is("plain"))
    assertThat(engine.handle(Request(HttpMethod.PUT, "text", HeaderParameters("Content-Type" -> "text/html"), QueryParameters(), FormParameters(), Request.emptyInput)), is("html"))
  }
}

object RestTest {
  class TestEngine{
    val engine = new RestEngine
    val container = new SimpleContainer

    def add(resource: Class[_]):TestEngine = {
      engine.add(resource)
      container.add(resource)
      this
    }

    def handle(request:Request):String = {
      val output = new ByteArrayOutputStream
      handle(request, Response(output))
      output.toString
    }

    def handle(request:Request, response:Response):Unit = {
      engine.handle(container, request, response)
    }
  }


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
    @Produces(Array("application/xml"))
    def getXml(): String = {
      "xml"
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
  class DeleteContent {
    var count = 0
    @DELETE
    def delete(@PathParam("id") id:String): Unit = {
      count = count + 1
    }
  }

 @Path("path/{id}")
  class PutContent {
    @PUT
    def put(@PathParam("id") id:String, input:InputStream): String = {
      asString(input)
    }
  }

  @Path("path/{id}")
  class PathParameter {
    @GET
    def get(@PathParam("id") id:String): String = {
      id
    }
  }

  @Path("text")
  class MultiplePutContent {
    @PUT
    @Consumes(Array("text/plain"))
    def putPlain(input:InputStream): String = {
      "plain"
    }

    @PUT
    @Consumes(Array("text/html"))
    def putHtml(input:InputStream): String = {
      "html"
    }
  }


}