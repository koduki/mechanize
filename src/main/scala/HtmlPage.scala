package cn.orz.pascal.scala.mechanize

import com.gargoylesoftware.htmlunit.html.{HtmlPage => HtmlUnitPage}
import com.gargoylesoftware.htmlunit.html.{HtmlForm => HtmlUnitForm}
import com.gargoylesoftware.htmlunit.html.{HtmlInput => HtmlUnitInput}
import java.net.URL
import scala.collection.JavaConversions._

// vim: set ts=4 sw=4 et:
abstract class HttpMethod
case object Get extends HttpMethod
case object Post extends HttpMethod
case object Put extends HttpMethod
case object Delete extends HttpMethod
case object Head extends HttpMethod

abstract class FieldAttribute() { def value:String }
case class Name(val value:String) extends FieldAttribute


class HtmlPage(val page:HtmlUnitPage) {
    def title:String =  page.getTitleText
    def url:URL = page.getUrl
    def forms:List[HtmlForm] = {
        page.getForms.map(new HtmlForm(_)).toList
    }
}

class HtmlForm(val form:HtmlUnitForm) {
    def name:String = form.getNameAttribute
    def method:HttpMethod = {
        form.getMethodAttribute.toUpperCase match {
          case "GET" => Get
          case "POST" => Post
          case _ => Get
        }
    }    
    def submit():HtmlPage = new HtmlPage(form.click.asInstanceOf[HtmlUnitPage])
    def fields_with(attribute:FieldAttribute):List[HtmlField] = {
        form.getInputsByName(attribute.value).toList.map(new HtmlField(_))

    }
}
class HtmlField(val field:HtmlUnitInput) {
    def name():String = field.getNameAttribute
}
