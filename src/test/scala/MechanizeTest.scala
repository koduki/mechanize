package cn.orz.pascal.scala.mechanize 


// vim: set ts=4 sw=4 et:
import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import java.net.URL

class MechanaizeTest extends WordSpec with ShouldMatchers {
    "GET 'http://www.google.co.jp'" when{
        val agent:Mechanize = new Mechanize() 
        val page:HtmlPage = agent.get("http://www.google.co.jp")

        page.title should be("Google")
        page.url should be(new URL("http://www.google.co.jp/"))
        page.forms(0).name should be("f")
        page.forms(0).method should be(Get())
        
    }

    "GET 'http://www.amazon.co.jp/'" when{
        val agent:Mechanize = new Mechanize() 
        val page:HtmlPage = agent.get("http://www.amazon.co.jp/")

        page.title should be("Amazon.co.jp： 通販 - ファッション、家電から食品まで【無料配送】")
        page.url should be(new URL("http://www.amazon.co.jp/"))
        page.forms(0).name should be("site-search")
    }
}
