[![CI/CD](https://github.com/nachg/xpathqs-core/actions/workflows/build.yml/badge.svg)](https://github.com/nachg/xpathqs-core/actions/workflows/build.yml)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.xpathqs/xpathqs-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.xpathqs/xpathqs-core/)
![GitHub top language](https://img.shields.io/github/languages/top/nachg/xpathqs-core)
[![Coverage](.github/badges/jacoco.svg)](jacoco.svg)

[![License](https://img.shields.io/github/license/nachg/xpathqs-core)](https://github.com/nachg/xpathqs-core/blob/master/LICENSE)
[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)

# XpathQS

A library for building Xpath queries in an OOP style. In a [JOOQ](https://www.jooq.org/) way, but for the XPATH.

## Usage

Apache Maven
```xml
<dependency>
  <groupId>org.xpathqs</groupId>
  <artifactId>xpathqs-core</artifactId>
  <version>0.0.4</version>
</dependency>
```

Gradle Kotlin DSL
```kotlin
implementation("org.xpathqs:xpathqs-core:0.0.4")
```

Gradle Groovy DSL
```groovy
implementation 'org.xpathqs:xpathqs-core:0.0.4'
```

## Quick Example

Simple selector with _tag_ and _text_:

```kotlin
tagSelector("div").text("demo").toXpath()
```

Will return such XPATH query:

```xpath
//div[text()='test']
```

Selector with _tag_, _text contains_, and _position_:

```kotlin
tagSelector("div").text("test", contains = true)[2].toXpath()
```

Result:

```xpath
//div[contains(text(), 'test') and position()=2]
```

For more examples you can
discover [unit tests](https://github.com/nachg/xpathqs-core/tree/master/src/test/kotlin/org/xpathqs).

## License

This XpathQS lib is released under the [MIT License](https://github.com/nachg/xpathqs-core/blob/master/LICENSE).