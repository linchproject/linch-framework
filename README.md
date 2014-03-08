#Linch Web Framework

Note: This documentation is still work in progress.

##Getting started

### Create a project
Create a Maven project with the Linch archetype.

    mvn archetype:generate \
    -DarchetypeGroupId=com.linchproject \
    -DarchetypeArtifactId=linch-archetype \
    -DarchetypeVersion=0.1-SNAPSHOT \
    -DarchetypeRepository=https://github.com/linchproject/mvn-repo/raw/master/snapshots


The structure of your project should look like this.

    example
    ├─ src
    │  └─ main
    │     ├─ java
    │     │  └─ com.example.your.package.controllers
    │     │     └─ AppController
    │     ├─ resources
    │     │  ├─ templates
    │     │  │  └─ index.mustache
    │     │  └─ app.properties
    │     └─ webapp
    │        └─ WEB-INF
    │           └─ web.xml
    └─ pom.xml


### Run your application
To run your application, use the maven jetty plugin.

    mvn jetty:run

Now you can point your browser to [http://localhost:8080/](http://localhost:8080/). It should say "Hello World"

### Controller

If call the root url (/), Linch will look for the default action "index" in the default controller "app".
That means that it will look for method "index" in the class "AppController".

AppController:

    package com.linchproject.framework.example.controllers;

    import com.linchproject.core.Params;
    import com.linchproject.core.Result;
    import com.linchproject.framework.Controller;

    public class AppController extends Controller {

        public Result index(Params params) {
            return render("index", context().put("hello", "Hello World"));
        }
    }

The controller is derived from the framework controller. The index method receives a map of parameters and returns a result.
The method render returns a Success result with the rendered template as content.

### Templates

The Linch framework uses Mustache templates. Mustache templates must be located in resources/templates and have the extension .mustache.
The controller above renders the template "index", so Linch will look for the file "resources/templates/index.mustache".

index.mustache:

    <!DOCTYPE html>
    <html>
    <body>

    <h1>{{hello}}</h1>

    </body>
    </html>

The variable "hello" is passed to the template in the context map by the controller.


### To be continued...

The example source can be found here: [https://github.com/linchproject/linch-framework-example](https://github.com/linchproject/linch-framework-example)
