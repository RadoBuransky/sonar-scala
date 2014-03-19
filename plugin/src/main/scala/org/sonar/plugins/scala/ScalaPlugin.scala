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
package org.sonar.plugins.scala

import org.sonar.api.{Extension, SonarPlugin}
import org.sonar.plugins.scala.measure.ScalaMetrics
import scala.collection.mutable.ListBuffer
import org.sonar.plugins.scala.sensor.{ScoverageSensor, ScoverageSourceImporterSensor}
import org.sonar.plugins.scala.language.Scala
import org.sonar.plugins.scala.widget.ScoverageWidget
import scala.collection.JavaConversions._
import org.sonar.plugins.scala.colorizer.ScalaColorizerFormat

/**
 * Plugin entry point.
 *
 * @author Rado Buransky
 */
class ScalaPlugin extends SonarPlugin {
  override def getExtensions: java.util.List[Class[_ <: Extension]] = ListBuffer(
    classOf[Scala],
    classOf[ScalaMetrics],
    classOf[ScoverageSourceImporterSensor],
    classOf[ScoverageSensor],
    classOf[ScoverageWidget],
    classOf[ScalaColorizerFormat]
  )

  override val toString = getClass.getSimpleName
}

object ScalaPlugin {
  def pathToScalaLibrary = getPathByResource("scala/package.class")

  private def getPathByResource(name: String): String = {
    val path = ScalaPlugin.getClass.getClassLoader.getResource(name).getPath
    path.substring("file:".length(), path.lastIndexOf('!'))
  }
}