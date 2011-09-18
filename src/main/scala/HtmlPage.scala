package cn.orz.pascal.scala.mechanize

import com.gargoylesoftware.htmlunit.html.{HtmlPage => HtmlUnitPage}
import java.net.URL

// vim: set ts=4 sw=4 et:
class HtmlPage(val page:HtmlUnitPage) {
    def title:String =  page.getTitleText
    def url:URL = page.getUrl
    
}
