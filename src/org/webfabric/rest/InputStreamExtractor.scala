package org.webfabric.rest


class InputStreamExtractor extends Extractor[Request, Object] with Matcher[Request]{
  def isMatch(request: Request) = true

  def extract(request: Request) = request.input
}