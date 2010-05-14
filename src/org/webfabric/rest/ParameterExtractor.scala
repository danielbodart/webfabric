package org.webfabric.rest

trait ParameterExtractor {
  def extractFrom(query:Parameters):Object
}