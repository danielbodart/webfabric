package org.webfabric.rest

import java.io.InputStream

class InputStreamExtractor extends RequestExtractor[InputStream]{
  def isMatch(request: Request) = true

  def extract(request: Request) = request.input
}