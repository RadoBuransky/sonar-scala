/*
 * Sonar Scoverage Plugin
 * Copyright (C) 2013 Rado Buransky
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.scala.colorizer

import org.sonar.api.web.CodeColorizerFormat
import org.sonar.plugins.scala.language.Scala
import org.sonar.colorizer._
import scala.collection.JavaConversions._

class ScalaColorizerFormat extends CodeColorizerFormat(Scala.key) {
  override val getTokenizers: java.util.List[Tokenizer] = ScalaColorizerFormat.TOKENIZERS
}

private[colorizer] object ScalaColorizerFormat {
  val END_SPAN_TAG = "</span>"

  val TOKENIZERS = List(
    new LiteralTokenizer("<span class=\"s\">", END_SPAN_TAG),
    new KeywordsTokenizer("<span class=\"k\">", END_SPAN_TAG, KEYWORDS),
    new CDocTokenizer("<span class=\"cd\">", END_SPAN_TAG),
    new CppDocTokenizer("<span class=\"cppd\">", END_SPAN_TAG),
    new JavadocTokenizer("<span class=\"j\">", END_SPAN_TAG),
    new JavaAnnotationTokenizer("<span class=\"a\">", END_SPAN_TAG)
  )

  val KEYWORDS = Set("abstract", "assert", "case", "catch", "class", "def", "do", "else", "extends", "false",
    "final", "finally", "for", "forSome", "if", "implicit", "import", "lazy", "match", "new",
    "null", "object", "override", "package", "private", "protected", "requires", "return",
    "sealed", "super", "this", "throw", "trait", "true", "try", "type", "val", "var", "while",
    "with", "yield", "_", ":", "=", "=>", "<-", "<:", "<%", ">:", "#", "@")
}