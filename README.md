# Logistics_company

## Setting up development environment
Environment configuration is made using Spring Boot configuration files. List of common configuration properties can be found [here](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)

### Database
All connection parameters (*spring.datasource.\**) are specified in [application.properties](src/main/resources/application.properties) file.

### Mail
Mail server parameters (*spring.mail.\**) must be specified in *mail.properties* file, which should be placed in *resources* folder.

## Usage
### IDEA
Run configuration for IDEA is included.

### Maven
Use spring-boot-maven-plugin:
```
mvn spring-boot:run
```

### Packaged JAR file
To run JAR file, you must manually specify location for configuration files:
```
java -jar application.jar --spring.config.location=classpath:/application.properties,classpath:/mail.properties
```
