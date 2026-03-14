# Annotation Adder

<p align="center">
    <a href="https://github.com/pomba123/asm/actions/workflows/build.yml" alt="Build">
        <img src="https://github.com/pomba123/asm/actions/workflows/build.yml/badge.svg" /></a>
</p>

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
In your project directory run 

``mvn clean build``

## Configuring Conventions

Create a file named conventions.json at same folder of the pom.xml file. Conventions are defined according the below structure:

```json
{
  "conventions": [
    {
      "name": "PrefixConvention",
      "conventionScope": "method",
      "allRulesMustApply": "false",
      "rules": [
        {
          "implementation": "org.example.verifiers.PrefixConventionVerifier",
          "parameters": [
            { "name": "suffix", "type": "String", "value": "get"
            }
          ]
        }],
      "annotation": {
        "name": "org.example.annotations.AnnotationWithParameters",
        "parameters": [
          {"name": "value", "value": "3", "type": "int"},
          {"name": "name", "value": "false", "type": "boolean"}
        ]
      }
   }]
}
```

1. **name**  
   *Description:* The convention name identifier, PrefixConvention in this example

2. **conventionScope**  
   *Description:* The scope to where the convention must apply, class field and method are supported so far.

3. **allRulesMustApply**  
   *Description:* When multiple rules are defined to a convention, all of them must apply for convention to verify.

4. **Rules**  
   *Description:* Rules have two fileds. Implementation, where the full verifier class name must be provided, and parameters, which are the parameters that the verifier will consume.
   Parameter have their name, type and the value. In this example the class PrefixConventionVerifier will search all methods looking for the prefix "get".

6. **annotation**
   *Description:* When a convention is met, a given annotation must be inserted in the final bytecode. The specification requires the full annotation name, and the annotation parameters if any following the same parameter structure mentioned before.

   ## Supported Conventions

 **ClassImplementsInterfaceConvention**  
   **Scope:** class  
   **Description:** Verifies if a given class implements a given interface.

 **ClassInPackageConventionVerifier**  
   **Scope:** class  
   **Description:** Verifies if a given class is in a given package.

 **ElementNameConventionVerifier**  
   **Scope:** class, method, or field  
   **Description:** Verifies if a given element has that exact name.

 **FieldTypeConventionVerifier**  
   **Scope:** field  
   **Description:** Verifies if a given field is of a given type.

 **MethodReturnTypeConvention**  
   **Scope:** method  
   **Description:** Verifies if a given method returns a given type.

 **PrefixConventionVerifier**  
   **Scope:** class, method, or field  
   **Description:** Verifies if a given code element name has a given prefix.

 **SuffixConventionVerifier**  
   **Scope:** class, method, or field  
   **Description:** Verifies if a given code element name has a given suffix.

 **RegularExpressionConventionVerifier**  
   **Scope:** class, method, or field  
   **Description:** 
   - Verifies if a given code element name matches a given regex. 



