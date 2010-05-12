package org.webfabric.http

import javax.servlet._
import com.google.appengine.tools.development.testing.{LocalServiceTestHelper, LocalDatastoreServiceTestConfig}

class LocalDatastoreFilter extends Filter {
  val localServices = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig)

  def destroy = {

  }

  def doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) = {
    localServices.setUp
    chain.doFilter(request, response)
  }

  def init(p1: FilterConfig) = {
  }
}