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
package org.sonar.plugins.scala.language

case class Comment(lines: List[String], commentType: CommentType.CommentType) {
  lazy val numberOfLines = lines.size + numberOfBlankLines - numberOfCommentedOutLinesOfCode
  val numberOfBlankLines = lines.count(!_.exists(c => !Character.isWhitespace(c) && c != '*' && c != '/'))
  val numberOfCommentedOutLinesOfCode =
    commentType match {
      case CommentType.Doc => 0
      case _ => {
        lines.count { line =>
          val charsToDrop = " /*"
          val leftStripped = line.dropWhile(charsToDrop.contains(_))
          val rightStripped = leftStripped.reverse.dropWhile(charsToDrop.contains(_))

          CodeDetector.hasDetectedCode(rightStripped)
        }
      }
    }

  val isDocComment = commentType == CommentType.Doc
}

object Comment {
  def apply(content: String, commentType: CommentType.CommentType) =
    new Comment(content.lines.toList, commentType)
}

object CommentType extends Enumeration {
  type CommentType = Value
  val Normal, Doc, Header = Value
}