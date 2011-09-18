package cn.orz.pascal.scala.mechanize

import com.gargoylesoftware.htmlunit.html.{HtmlPage => HtmlUnitPage}
import com.gargoylesoftware.htmlunit.html.{HtmlForm => HtmlUnitForm}
import java.net.URL

// vim: set ts=4 sw=4 et:
abstract class HttpMethod
case class Get extends HttpMethod
case class Post extends HttpMethod
case class Put extends HttpMethod
case class Delete extends HttpMethod
case class Head extends HttpMethod
/*
object HttpMethod extends Enumeration { 
    val HTTP_METHOD = Value
  val GET,POST = Value 
}
*/
class HtmlPage(val page:HtmlUnitPage) {
    def title:String =  page.getTitleText
    def url:URL = page.getUrl
    def forms:List[HtmlForm] = {
        import scala.collection.JavaConversions._
        page.getForms.map(new HtmlForm(_)).toList
    }
}

class HtmlForm(val form:HtmlUnitForm) {
    def name:String = form.getNameAttribute
    def method:HttpMethod = {
        form.getMethodAttribute.toUpperCase match {
          case "GET" => Get()
          case "POST" => Post()
          case _ => Get()
        }
    }
}
