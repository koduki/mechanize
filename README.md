# Mechanize

Mechanize is web scraping tool. 
Not only HTML but JavaScript is supported. 

Thanks to  [Mechanize(ruby)](https://github.com/tenderlove/mechanize) and [HtmlUnit](http://htmlunit.sourceforge.net/). 

## Building
    sbt update publish-local

## Example
    import cn.orz.pascal.scala.mechanize._
    val agent = new Mechanize()
    val page  = agent.get("http://www.google.co.jp")
    val form  = page.forms(0)
    val input = form.fields_with(Name("q"))(0)
     
    input.value = "Scala"
     
    val result_page = form.submit()
    val body = result_page.asXml
    body \\ "title" text


## License 
    Copyright (c) 2002-2011 Gargoyle Software Inc.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
     
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

