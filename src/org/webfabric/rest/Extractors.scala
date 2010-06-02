package org.webfabric.rest

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
}