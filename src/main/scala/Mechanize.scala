package cn.orz.pascal.mechanize

import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.WebClient
import java.net.URL

import org.apache.commons.logging.LogFactory 
import org.apache.commons.logging.impl.Jdk14Logger
import java.util.logging.Logger
import java.util.logging.Level

// vim: set ts=4 sw=4 et:
class Mechanize {
    val client = new WebClient(BrowserVersion.FIREFOX_3)
    val logger = LogFactory.getLog("com.gargoylesoftware.htmlunit").asInstanceOf[Jdk14Logger].getLogger
    logger.setLevel(Level.OFF)

    def isJavaScriptEnabled() = client.isJavaScriptEnabled
    def isJavaScriptEnabled_= (isEnabled:Boolean) {
        client.setJavaScriptEnabled(isEnabled)
      }

    def get(url:String):HtmlPage = {
        client.waitForBackgroundJavaScript(10000)
        new HtmlPage(client.getPage(new URL(url)))
    }
}
