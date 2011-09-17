package cn.orz.pascal.scala.mechanize

import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.WebClient
import java.net.URL

// vim: set ts=4 sw=4 et:
class Mechanize {
    val client = new WebClient(BrowserVersion.FIREFOX_3)

    def get(url:String):HtmlPage = {
        new HtmlPage(client.getPage(new URL(url)))
    }
}
