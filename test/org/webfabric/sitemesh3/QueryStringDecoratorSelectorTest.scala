package org.webfabric.sitemesh3

import org.junit.Test
import org.junit.Assert.assertThat
import org.sitemesh.webapp.WebAppContext
import org.hamcrest.CoreMatchers.{is, equalTo}
import javax.servlet.http.{HttpServletRequest, HttpServletRequestWrapper}
import java.lang.reflect.{InvocationHandler, Method, Proxy}
import java.lang.String

class QueryStringDecoratorSelectorTest {
  @Test
  def supports(): Unit = {
    // setup
    val r = Proxy.newProxyInstance(classOf[HttpServletRequest].getClassLoader(),
      Array[Class[_]](classOf[HttpServletRequest]), new InvocationHandler() {
      def invoke(proxy: Any, method: Method, arguments: Array[Object]) = null
    }).asInstanceOf[HttpServletRequest]

    val request = new HttpServletRequestWrapper(r) {
      override def getParameter(name: String) = "body"
    }
    val context = new WebAppContext(null, request, null, null, null, null)
    val selector = new QueryStringDecoratorSelector

    // execute
    val decoratorPaths = selector.selectDecoratorPaths(null, context)

    // verify
    assertThat(decoratorPaths.length, is(equalTo(1)))
    assertThat(decoratorPaths(0), is(equalTo("/decorators/body.st")))
  }
}