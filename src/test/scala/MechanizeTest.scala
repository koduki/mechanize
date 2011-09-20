package cn.orz.pascal.scala.mechanize 


// vim: set ts=4 sw=4 et:
import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import java.net.URL

class MechanaizeTest extends WordSpec with ShouldMatchers {
    "GET 'http://www.google.co.jp'" when {
        val agent:Mechanize = new Mechanize() 
        val page:HtmlPage = agent.get("http://www.google.co.jp")

        page.title should be("Google")
        page.url should be(new URL("http://www.google.co.jp/"))

        val form = page.forms(0)
        form.name should be("f")
        form.method should be(Get)
        form.fields_with(Name("q"))(0).name should be("q")
        form.fields_with(Class("lst"))(0).name should be("q")
        form.fields_with(Type("hidden"))(0).name should be("hl")
        form.fields_with(XPath(".//input[@type='hidden' and @value='hp']"))(0).name should be("source")
        //form.submit()

    }

    "GET 'http://www.amazon.co.jp/'" when {
        val agent:Mechanize = new Mechanize() 
        val page:HtmlPage = agent.get("http://www.amazon.co.jp/")

        page.title should be("Amazon.co.jp： 通販 - ファッション、家電から食品まで【無料配送】")
        page.url should be(new URL("http://www.amazon.co.jp/"))

        val form = page.forms(0)
        form.name should be("site-search")
        form.method should be(Get)
        form.fields_with(Name("field-keywords"))(0).name should be("field-keywords")
        form.fields_with(Class("searchSelect"))(0).name should be("field-keywords")
        form.fields_with(Type("text"))(0).name should be("field-keywords")
        form.fields_with(XPath(".//input[@type='text' and @title='検索']"))(0).name should be("field-keywords")
 
    }
}

