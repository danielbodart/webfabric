package org.webfabric.rest

import java.lang.reflect.Method
import javax.ws.rs._
import com.googlecode.yadic.{Resolver}
import core.{HttpHeaders, StreamingOutput}
import org.webfabric.servlet.ContextPath

class HttpMethodActivator(httpMethod: String, resource: Class[_], method: Method) extends Matcher[Request]{
  val pathExtractor = new PathExtractor(resource, method)

  lazy val extractors = Extractors.generateExtractors(method, pathExtractor)

  var producesMatcher = new ProducesMimeMatcher(resource, method)
  val matchers = List(new MethodMatcher(httpMethod), producesMatcher, new ConsumesMimeMatcher(resource, method), pathExtractor)

  def isMatch(request:Request): Boolean = {
    var allMatchers = matchers.forall(_.isMatch(request))
    var allExtractors = extractors.forall(_.isMatch(request))
    allMatchers && allExtractors
  }

  def matchQuality(request:Request):Float = producesMatcher.matchQuality(request)

  def numberOfArguments = method.getParameterTypes.size
  
  def activate(container: Resolver, request:Request, response:Response): Unit = {
    val instance = container.resolve(resource)
    var result = method.invoke(instance, getParameters(request): _*)
    response.setHeader(HttpHeaders.CONTENT_TYPE, producesMatcher.mimeType)
    result match {
      case redirect:Redirect => redirect.applyTo(request.base, response)
      case body:String => response.write(body)
      case streaming:StreamingOutput => streaming.write(response.output)
      case null => response.setCode(204)
    }
  }

  def getParameters(request:Request): Array[Object] = extractors.map(extractor => extractor.extract(request))
}