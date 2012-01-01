package com.github.tototoshi.selector

import scala.xml._

import scala.util.parsing.combinator._

trait SelectorParser extends RegexParsers {

  val html: NodeSeq

  type Filter    = NodeSeq => Boolean
  type Extractor = NodeSeq => NodeSeq

  def tagSelector(tag: String)(filter: Filter): Extractor =
    xml => xml \\ tag filter filter

  def idFilter(id: String): Filter = n => (n \ "@id" text) == id
  def classFilter(cls: String): Filter = n => (n \ "@class" text) == cls
  def attrFilter(attrName: String, attr: String): Filter = n => (n \ ("@" + attrName) text) == attr
  def all: Filter = _ => true

  def idPrefix = "#"
  def classPrefix = "."
  def token = """[a-zA-Z0-9-_:]+""".r
  def attrAvailableChars = """[a-zA-Z0-9-_:.#]+""".r
  def tag = token ^^ tagSelector

  def id = idPrefix ~> attrAvailableChars ^^ idFilter
  def cls = classPrefix ~> attrAvailableChars ^^ classFilter
  def otherAttr = "[" ~> token ~ "=" ~ attrAvailableChars <~ "]" ^^ {
    case attrName ~ equal ~ attr => attrFilter(attrName, attr)
  }
  def attr = id | cls | otherAttr

  def selector: Parser[Extractor] = tag ~ opt(attr) ^^ {
    case t ~ Some(a) => t(a)
    case t ~ None    => t(all)
  }

  def selectors: Parser[List[Extractor]] = rep1(selector)

  def select(selectors: List[Extractor]): NodeSeq = {
    selectors.foldLeft(html) { (xml, func) => xml flatMap func }
  }

  def parse(in: String) = parseAll(selectors, in) match {
    case Success(ss, _) => select(ss)
    case _ => NodeSeq.Empty
  }

}


class Selector(val html: NodeSeq) extends SelectorParser {
  def apply(selector: String): NodeSeq = parse(selector)
}

