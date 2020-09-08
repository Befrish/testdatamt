[![Build Status](https://travis-ci.com/Befrish/testdatamt.svg?token=diU3zY8u2h8512q9UWvK&branch=master)](https://travis-ci.com/Befrish/testdatamt)

# TestDataMT - Test data management tool

Tool for managing test data.

Scenarios:
* load test data for test cases before test execution (e.g. JUnit)
* check state after test execution (e.g. state of a database)
* load test data for an application in development

Features:
* simple domain specific language
* multiple input possibilities (file, builder)
* multiple output possiblities (database, graph database for visualization, objects, ...)
* integration in diverent test frameworks (JUnit, Cucumber, ...)
* visualization tool (based on Neo4J)

Technology/libraries:
- Spring Boot
- Scala (Combinator Parser)
- DbUnit
- Lombok
- Vavr
