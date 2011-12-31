# Selector

## Example

```scala
val html =
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
    </body>
  </html>

val $ = new Selector(html)

println($("div#container2 span.foo").text) //=> oshogatsu

```


