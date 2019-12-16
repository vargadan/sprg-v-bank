# Exercise 7 - Components with Known Vulnerabilities
The insecure serialization vulnerability in the previous exercise was caused by a bug in the (apache) commons-collections library version 3.2.1  
In this exercise we are going demonstrate how such known vulnerabilities can be scanned for and how it can be integrated into the build process.
## Scan for vulnerable libraries in our application
1. Open _pom.xml_ and scroll down to the end, till:
```xml
            <plugin>
                <groupId>org.owasp</groupId>
                <artifactId>dependency-check-maven</artifactId>
                <version>5.2.4</version>
                <executions>
                    <execution>
                        <id>default</id>
<!--                        <phase>prepare-package</phase>-->
                        <goals>
                            <goal>check</goal>
                        </goals>
                    </execution>
                </executions>
<!--                <configuration>-->
<!--                    <failBuildOnCVSS>7</failBuildOnCVSS>-->
<!--                </configuration>-->
            </plugin>
```
1. Open terminal and run ``mvn dependency:check``
## Integrate scanning into the build process
1. Comment out below fragments for plugin configuration in _pom.xml_
````xml
                <phase>prepare-package</phase>
````
so that the vulnerability scanning is run when packaging by default,
and 
````xml
                <configuration>
                    <failBuildOnCVSS>7</failBuildOnCVSS>
                </configuration>
````
so that only breaks the build on HIGH severity vulnerabilities (CVSS - Common Vulnerability Scoring System  >= 7).  
1. Run standard build `mvn package`
** update vulnerable components  
   1. find below snippet and update version to 3.2.2:
```xml
        <dependency>
           <groupId>commons-collections</groupId>
           <artifactId>commons-collections</artifactId>
           <version>3.2.1</version>
        </dependency>
```
  1. Run build again