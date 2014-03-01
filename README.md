# Sonar Scala Plugin
Supports Sonar 3.0+ and requires Surefire plugin.

To include test reports install this plugin to your scala project:
https://github.com/mmarich/sbt-simple-junit-xml-reporter-plugin
- Creates junit xml reports for output from scalatest.

Add the following properties to your project's sonar-project.properties file:

    sonar.dynamicAnalysis=reuseReports
    sonar.surefire.reportsPath=test-reports

## Code coverage

This plugin doesn't support code coverage anymore. Use [Scoverage](https://github.com/scoverage/scalac-scoverage-plugin)
with [Scoverage plugin for Sonar](https://github.com/RadoBuransky/sonar-scoverage-plugin).

## Changeset

### 0.4
- removed support for Cobertura code coverage