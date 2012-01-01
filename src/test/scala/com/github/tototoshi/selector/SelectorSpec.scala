package com.github.tototoshi.selector

import org.scalatest.Spec

class SelectorSpec extends Spec {

  def fixture =
    new {
      val xml =
        <html>
          <head>
              <title>
              </title>
          </head>
          <body>
              <ul>
                <li>foo</li>
                <li>bar</li>
              </ul>
              <div id="container">
                  <div class="contents">
                      <span class="foo">oo</span>
                      <span class="foo">miso</span>
                      <span class="foo">ka</span>
                  </div>
              </div>
              <div id="container2">
                  <div class="contents">
                      <span class="foo">o</span>
                      <span class="foo">sho</span>
                      <span class="foo">gatsu</span>
                  </div>
              </div>
              <div id="container3">
                <a href="aaa">bar</a>
              </div>
              <div id="container4">
                <a href="a.a.a">baz</a>
              </div>
          </body>
        </html>
    }

  describe("Selector") {

    it("works fine!") {

      val xml = fixture.xml
      val $ = new Selector(xml)

      assert("foobar" === $("li").text)

      assert("foobar" === $("body ul li").text)

      assert("oomisokaoshogatsu" === $("span.foo").text)

      assert("oshogatsu" === $("div#container2 span.foo").text)

      assert("oshogatsu" === $("div#container2 span.foo").text)

      assert("bar" === $("a[href=aaa]").text)

      assert($("a[href=a.a.a]").text === "baz")
    }

  }

}
