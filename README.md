# Annotation Adder

Annotation Adder is a tool that reads compiled bytecode and adds annotations based on defined conventions for annotations.

## How to install
Download the source code and install as maven plugin

``mvn clean install``

## How to use

Add the tool as a  maven plugin in the pom.xml file of the target project.

```xml
<plugin>
    <groupId>org.example</groupId>
    <artifactId>asm-convention-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <executions>
        <execution>
            <goals>
                <goal>instrument</goal>
            </goals>
            <phase>process-classes</phase>
        </execution>
    </executions>
</plugin>
```
Build your project normally.


