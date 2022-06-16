# Configure your project
Clone the repo in github  https://github.com/dikmarti85/metrics-app-api.git

Ater execute `mvn clean install` and enjoy :)

# Spring Boot App model for Java 11

We provide a basic model for JDK 11 / Spring based web applications.

## Usage

* Change the artifactId and project name in pom.xml.
* If you update the package hierarchy, **remember to update the package reference in @ComponentScan of SpringConfig class**.

### Web Server

Each Spring Boot web application includes an embedded web server. For servlet stack applications, Its supports three web Servers:
* Tomcat (maven dependency: `spring-boot-starter-tomcat`)
* Jetty (maven dependency: `spring-boot-starter-jetty`)
* Undertow (maven dependency: `spring-boot-starter-undertow`)
