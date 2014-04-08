# rent-a-wreck - Developer Guide

## Tools

### IDE

Preferred IDE is Eclipse (Kepler). As it was used for development...

Please configure your Eclipse to use the given Code Formatter and Cleanup Settings for Java and JavaScript.
* [Java Code Formatter](./development/resources/eclipse-java-code-formatter.xml)
* [Java Code Cleanup](./development/resources/eclipse-java-code-cleanup.xml)
* [JavaScript Code Formatter](./development/resources/eclipse-javascript-code-formatter.xml)
* [JavaScript Code Cleanup](./development/resources/eclipse-java-code-cleanup.xml)

It is strongly recommended to also implement the cleanup settings as save actions for the Java and JavaScript editors.

Do also configure all other used editors to the following formatting rules:
* Indentation: Always use tabs!
* Line wrapping: Always use a 120-column line width.

_If you prefer to use another IDE feel free to do so. But please make sure the code matches all of the Eclipse 
formatting rules._

### SCM

This project uses Git. There is only one repository for the complete project.

### Build and Dependency Management
Maven 3.x

### Continous Integration
Travis

