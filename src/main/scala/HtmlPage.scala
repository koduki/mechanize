package cn.orz.pascal.scala.mechanize

import com.gargoylesoftware.htmlunit.html.{HtmlElement => HtmlUnitElement}
import com.gargoylesoftware.htmlunit.html.{HtmlPage => HtmlUnitPage}
import com.gargoylesoftware.htmlunit.html.{HtmlForm => HtmlUnitForm}
import com.gargoylesoftware.htmlunit.html.{HtmlInput => HtmlUnitInput}
import com.gargoylesoftware.htmlunit.html.{DomNode => DomUnitNode}
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
case class Id(val value:String) extends FieldAttribute
case class Name(val value:String) extends FieldAttribute
case class Class(val value:String) extends FieldAttribute
case class Type(val value:String) extends FieldAttribute
case class XPath(val value:String) extends FieldAttribute

trait HtmlBase {
    type DomNode = DomUnitNode with org.w3c.dom.Document
    def source:DomNode

    def get(attr:FieldAttribute) = {
        val element = source.getElementById(attr.value).asInstanceOf[DomUnitNode]
        toNode(element)
    }

    def asXml = {
        toNode(source)
    }

    private def toNode(node:DomUnitNode) = {
        import java.io.StringReader
        import scala.xml.Node
        import scala.xml.parsing.NoBindingFactoryAdapter
        import nu.validator.htmlparser.sax.HtmlParser
        import nu.validator.htmlparser.common.XmlViolationPolicy
        import org.xml.sax.InputSource

        val hp = new HtmlParser
        hp.setNamePolicy(XmlViolationPolicy.ALLOW)

        val saxer = new NoBindingFactoryAdapter
        hp.setContentHandler(saxer)
        hp.parse(new InputSource(new StringReader(node.asXml)))
        saxer.rootElem
    }
}

class HtmlPage(val page:HtmlUnitPage) extends HtmlBase {
    def source = page

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

    def field(id:String):HtmlField = {
        new HtmlField(form.getElementById(id).asInstanceOf[HtmlUnitInput])
    }

    def fields_with(attr:FieldAttribute):List[HtmlField] = {
        (attr match {
          case Name(value) => findByXpath(".//input[@name='" + value + "']", form)
          case Class(value) => findByXpath(".//input[@class='" + value + "']", form)
          case Type(value) => findByXpath(".//input[@type='" + value + "']", form)
          case XPath(xpath) => findByXpath(xpath, form)
        }).map( node => new HtmlField(node.asInstanceOf[HtmlUnitInput]))
    }

    def findByXpath(xpathValue:String, node:org.w3c.dom.Node):List[org.w3c.dom.Node] = {
        import javax.xml.xpath._
        import org.w3c.dom._
        
        val xpathParser = XPathFactory.newInstance().newXPath().compile(xpathValue)
        val nodelist = xpathParser.evaluate(node, XPathConstants.NODESET).asInstanceOf[NodeList]
        (0 to nodelist.getLength).map( i => nodelist.item(i)).toList
    }
}

class HtmlField(val field:HtmlUnitInput) {
    def name():String = field.getNameAttribute
    def value:String = field.getValueAttribute
    def value_=(value:String) = new HtmlPage(field.setValueAttribute(value).asInstanceOf[HtmlUnitPage])
}
