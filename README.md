
# Swagger2 Springboot Starter [![Build Status](https://travis-ci.org/sparkmuse/swagger2-spring-boot-starter.svg?branch=master)](https://travis-ci.org/sparkmuse/swagger2-spring-boot-starter)

Starter to autoconfigure Swagger2 for Springboot. The project focuses on getting the user to use Swagger as soon as possible with the least amount of configuration. 

## Usage

All needed to start using the project is to add the dependency to the POM and that's it.


```xml
<dependency>
    <groupId>com.github.sparkmuse</groupId>
    <artifactId>swagger2-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Extra configuration

Add a file bellow to change the default configurations as needed. 

```yaml
swagger:
  group: Main group
  path-ant-expressions: /api/**,/foo/**
  api-info:
    title: Api Title
    description: My awesome description
    version: 1.0
    terms-of-service-url: url
    license: ApiInfo License
    license-url: Licence Url
    contact:
      name: Contact Name
      url: contact-url.com
      email: contact@email.com
```
**Note:** Notice there is no space between the ant expressions in the paths

The auto-configuration processor will enhance the existing configuration with the new ones added. You need to override only what's needed.
So if the only change needed is the name of the contact then the configuration bellow will suffice.

```yaml
swagger:
  api-info:
    contact:
      name: Awesome new name
```
