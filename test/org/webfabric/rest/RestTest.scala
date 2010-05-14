package org.webfabric.rest

import org.hamcrest.CoreMatchers._
import org.junit.Assert._
import org.junit._
import org.webfabric.rest.RestTest._
import javax.ws.rs.{QueryParam, GET, Path}

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
}

object RestTest {
  @Path("foo")
  class Gettable {
    @GET
    def get():String = {
      "bar"
    }
  }

  @Path("foo")
  class GettableWithQuery {
    @GET
    def get(@QueryParam("name") name:String):String = {
      name
    }
  }
}