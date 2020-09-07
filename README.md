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
