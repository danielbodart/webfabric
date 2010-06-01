package org.webfabric.rest

import coercers.Coercers
import javax.ws.rs.{QueryParam, FormParam, PathParam}
import java.io.InputStream
import java.lang.reflect.Method

object Extractors {
  def generateExtractors(method: Method, pathExtractor: PathExtractor) = {
    method.getParameterTypes.zip(method.getParameterAnnotations).map(pair => {
      if (pair._1 == classOf[InputStream]) new InputStreamExtractor() else pair._2(0) match {
        case query: QueryParam => new QueryParameterExtractor(query)
        case form: FormParam => new FormParameterExtractor(form)
        case path: PathParam => new PathParameterExtractor(path, pathExtractor)
        case _ => null
      }
    }).filter(_ != null)
  }

  class CoercionExtractor(parameterType:Class[_], extractor:RequestExtractor[Object]) extends RequestExtractor[Object]{
    val coercer = new Coercers()
    def isMatch(request: Request) = extractor.isMatch(request)

    def extract(request: Request):Object = {
      val value = extractor.extract(request)
      coercer.coerce(value, parameterType)
    }
  }
}