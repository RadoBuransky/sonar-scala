/*
 * Sonar Scala Plugin
 * Copyright (C) 2011 Felix Müller
 * felix.mueller.berlin@googlemail.com
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

import org.sonar.plugins.scala.compiler.{ Compiler, Parser }

/**
 * This object is a helper object for computing several statistics
 * of a given Scala source.
 *
 * @author Felix Müller
 * @since 0.1
 */
object CodeStatistics {

  import Compiler._

  private lazy val parser = new Parser()

  // TODO add traversing of match, try, case and other code blocks
  def countTypes(source: String) = {

    def countTypeTrees(tree: Tree, foundTypes: Int = 0) : Int = tree match {
      case PackageDef(_, content) => foundTypes + countTypeTreesInList(content)
      case Template(_, _, content) => foundTypes + countTypeTreesInList(content)
      case DocDef(_, content) => countTypeTrees(content, foundTypes)
      case DefDef(_, _, _, _, _, content) => countTypeTrees(content, foundTypes)
      case ValDef(_, _, _, content) => countTypeTrees(content, foundTypes)
      case Assign(_, rhs) => countTypeTrees(rhs, foundTypes)
      case LabelDef(_, _, rhs) => countTypeTrees(rhs, foundTypes)
      case If(cond, thenBlock, elseBlock) => foundTypes + countTypeTrees(cond)
          + countTypeTrees(thenBlock) + countTypeTrees(elseBlock)
      case Block(stats, expr) => foundTypes + countTypeTreesInList(stats) + countTypeTrees(expr)
      case ClassDef(_, _, _, content) => countTypeTrees(content, foundTypes + 1)
      case ModuleDef(_, _, content) => countTypeTrees(content, foundTypes + 1)
      case _ => foundTypes
    }

    def countTypeTreesInList(trees: List[Tree]) : Int = {
        trees.map(countTypeTrees(_)).foldLeft(0)(_ + _)
    }

//    Compiler.treeBrowser.browse(parser.parse(source))
    countTypeTrees(parser.parse(source))
  }
}