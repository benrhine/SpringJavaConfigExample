SpringJavaConfigExample
=======================
###(with Servlet 3.0 and JSTL 1.2)

![Java CI with Maven](https://github.com/benrhine/SpringJavaConfigExample/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)

A Simple Spring MVC application with Java configuration for servlet 3.0 and a complete test suite

For me this is more of a memory aide as well as a learning tool.  I wanted to have a fully tested, template web application to jump start any future projects for work as well as being able to easily reference things that I have learned or just want to remember.  This is the third template program in a series I am working on in my free time, I hope to soon have four of these examples.

* Spring MVC with XML Config
* Spring MVC with Java Config
* Spring MVC with Groovy on Grails    
* Spring MVC with Scala

Spring MVC with Java Config
=======================
For the java based config I have the servlet configured as 3.0 and JSTL 1.2. The java config in my opinion is much nicer than the XML config as number one it is completely testable since it is all java and two there is no xml (well almost none, the spring security config is still XML as it has not been completely integrated into spring core yet). The original example SpringXmlConfigExample is configured for servlet 2.4 and JSTL 1.1.2 and can run on a legacy Tomcat instance 5.5. At the time of writing this, cobertura shows by test coverage to be around 94% of all lines of code.

Setup
==============
To run this example you will need to setup a MySql database called test. Then you will need to change the username and password located in either the jetty.xml or context.xml.  After that you will need to run the Example-Create.sql file which is located under src/main/resources/sql-setup in order to setup the correct tables.

Running the Example
==============
To actually execute the example use any of the following commands. 

Run with Jetty 8
```
mvn jetty:run
```
Run with Tomcat 7
```
mvn tomcat7:run
```
If you want to build the example as a war and run all the tests then run the following.
```
mvn clean package
```

Testing
==============
I have done my best to completely write tests around my template to ensure that it will work out of the box and that I (or you) no longer have to be frustrated with examples that don't work when trying to get going on a new project. I have used the JUnit 4 framework and have both unit and integration tests around most of the code. At the moment I do not have integration tests around the controllers as I have not had time to figure out how to bypass spring security to allow these to run.  All mocking is performed with Mockito.  I hope to continue using this as a learning tool and will try to teach myself TestNG in the near future, when I get to that point I will add those tests as well.

####Tested Java Versions:

* Java 1.6.0_45
* Java 1.7.0_21

No issues with either of the versions I tried at least none that I noticed

####Tested Containers:

* Tomcat 7.0.37   
* Jetty  8.1.12.v20130726                                                                                               

The later three are all runable from the maven plugin and have been configured with a JNDI datasource.
####Tested Compilers:

* Maven 3.0.5    
* Maven 3.1.0

Builds and runs tests with either version without issue.


