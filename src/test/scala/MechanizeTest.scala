package cn.orz.pascal.scala.mechanize 


// vim: set ts=4 sw=4 et:
import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

class MechanaizeTest extends WordSpec with ShouldMatchers {
    "'http://www.google.com'" when{
        val agent:Mechanize = new Mechanize() 
        val page:HtmlPage = agent.get("http://www.google.com")

        "title is Google" in {
            page.title should be("Google")
        }
    }

    "'http://www.amazon.co.jp/'" when{
        val agent:Mechanize = new Mechanize() 
        val page:HtmlPage = agent.get("http://www.amazon.co.jp/")

        "title is Amazon.co.jp： 通販 - ファッション、家電から食品まで【無料配送】" in {
            page.title should be("Amazon.co.jp： 通販 - ファッション、家電から食品まで【無料配送】")
        }
    }
}
