#Linch web framework

Note: This documentation is still work in progress.

##Getting started

### Create an application
Create a Maven project that is structured like this.

    example
    ├─ src
    │  └─ main
    │     ├─ java
    │     ├─ resources
    │     └─ webapp
    │        └─ WEB-INF
    │           └─ web.xml
    └─ pom.xml

First you need to add the linchproject maven repository to your pom.xml.

    <repositories>
        <repository>
            <id>linchproject-snapshots</id>
            <url>https://github.com/linchproject/mvn-repo/raw/master/snapshots</url>
        </repository>
    </repositories>

Then you need to add two maven dependencies to your pom: linch-servlet and linch-framework.

    <dependencies>
        <dependency>
            <groupId>com.linchproject</groupId>
            <artifactId>linch-servlet</artifactId>
            <version>0.1-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>com.linchproject</groupId>
            <artifactId>linch-framework</artifactId>
            <version>0.1-SNAPSHOT</version>
        </dependency>
    </dependencies>

To run your application locally, you also need to add the Maven Jetty plugin.

    <build>
        <plugins>
            <plugin>
                <groupId>org.mortbay.jetty</groupId>
                <artifactId>maven-jetty-plugin</artifactId>
                <configuration>
                    <contextPath>/</contextPath>
                    <systemProperties>
                        <systemProperty>
                            <name>com.linchproject.dev</name>
                            <value>true</value>
                        </systemProperty>
                    </systemProperties>
                </configuration>
            </plugin>
        </plugins>
    </build>

In your web.xml you need to add the linch dispatcher. It should lool like this.

web.xml:

    <web-app version="2.4"
             xmlns="http://java.sun.com/xml/ns/j2ee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee
    	http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd">

        <display-name>Example</display-name>

        <servlet>
            <servlet-name>linch-dispatcher</servlet-name>
            <servlet-class>com.linchproject.servlet.DispatcherServlet</servlet-class>
            <load-on-startup>1</load-on-startup>
        </servlet>

        <servlet-mapping>
            <servlet-name>linch-dispatcher</servlet-name>
            <url-pattern>/</url-pattern>
        </servlet-mapping>
    </web-app>

A Linch application needs an app.properties in the resources directory where your base package is defined.

app.properties:

    package=com.linchproject.framework.example

You need to create this package in your java directory. Additional, Linch looks for controllers in the controllers package, so you need to create that as well.

Your project should now look like this.

    example
        ├─ src
        │  └─ main
        │     ├─ java
        │     │  └─ com.linchproject.framework.example.controllers
        │     ├─ resources
        │     │  └─ app.properties
        │     └─ webapp
        │        └─ WEB-INF
        │           └─ web.xml
        └─ pom.xml


### Run your application
To run your application, run the maven jetty plugin.

    mvn jetty:run

Now you can point your browser to [http://localhost:8080/](http://localhost:8080/). It should say "Cannot find controller 'app'"

This is because we called the root path (/) and Linch looks for an controller called "AppController" and we haven't defined any yet.

### Create a controller

The root controller must be called "AppController". There you need to create an action. The default action is called "index".

    package com.linchproject.framework.example.controllers;

    import com.linchproject.core.Params;
    import com.linchproject.core.Result;
    import com.linchproject.framework.Controller;

    public class AppController extends Controller {

        public Result index(Params params) {
            return success("Hello World");
        }
    }

The controller is derived from the framework controller. The index method receives a map of parameters and returns a result.

When refreshing, you should now see "Hello World" in your Browser.

### Render a template

The Linch framework uses Mustache templates. Mustache templates must be located in resources/templates and have the extension .mustace.
Create the templates directory and create a file called "index.mustache"

index.mustache:

    <!DOCTYPE html>
    <html>
    <body>

    <h1>Hello World</h1>

    </body>
    </html>

Change your controller to render the template:

    public Result index(Params params) {
        return render("index");
    }

To pass variables to your template, you can use the controllers context() method, to conveniently create a context map.

    public Result index(Params params) {
        return render("index", context().put("hello", "Hello World"));
    }

The variable is called "hello". Print the variable in your template like this.

index.mustache:

    <!DOCTYPE html>
    <html>
    <body>

    <h1>{{hello}}</h1>

    </body>
    </html>


### To be continued...

The example source can be found here: [https://github.com/linchproject/linch-framework-example](https://github.com/linchproject/linch-framework-example)
