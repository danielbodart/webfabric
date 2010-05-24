package org.webfabric.rest

import java.lang.reflect.Method
import javax.ws.rs._
import com.googlecode.yadic.{Resolver}
import core.StreamingOutput
import java.io.InputStream

class HttpMethodActivator(httpMethod: String, resource: Class[_], method: Method) extends Matcher[Request]{
  val pathExtractor = new PathExtractor(resource, method)

  lazy val extractors = method.getParameterTypes.zip(method.getParameterAnnotations).map(pair => {
    if(pair._1 == classOf[InputStream]) new InputStreamExtractor() else pair._2(0) match {
      case query: QueryParam => new QueryParameterExtractor(query)
      case form: FormParam => new FormParameterExtractor(form)
      case path: PathParam => new PathParameterExtractor(path, pathExtractor)
      case _ => null
    }}).filter(_ != null)

  var producesMatcher = new ProducesMimeMatcher(resource, method)
  val matchers = List(new MethodMatcher(httpMethod), producesMatcher, new ConsumesMimeMatcher(resource, method), pathExtractor)

  def isMatch(request:Request): Boolean = matchers.forall(_.isMatch(request)) && extractors.forall(_.isMatch(request))

  def matchQuality(request:Request):Float = producesMatcher.matchQuality(request) * (method.getParameterTypes.size + 1)
  
  def activate(container: Resolver, request:Request, response:Response): Unit = {
    val instance = container.resolve(resource)
    var result = method.invoke(instance, getParameters(request): _*)
    response.headers.add("Content-Type", producesMatcher.mimeType)
    result match {
      case body:String => response.write(body)
      case streaming:StreamingOutput => streaming.write(response.output)
      case null => response.code = 204
    }
  }

  def getParameters(request:Request): Array[Object] = extractors.map(extractor => extractor.extract(request))
}