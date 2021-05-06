[![build](https://github.com/nachg/xpathqs/actions/workflows/build.yml/badge.svg)](https://github.com/nachg/xpathqs/actions/workflows/build.yml)
![GitHub top language](https://img.shields.io/github/languages/top/nachg/xpathqs)
[![Coverage](.github/badges/jacoco.svg)](jacoco.svg)

[![License](https://img.shields.io/github/license/nachg/xpathqs)](https://github.com/nachg/xpathqs/blob/master/LICENSE)
[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)

# XpathQS

A library for building Xpath queries in an OOP style. In a [JOOQ](https://www.jooq.org/) way, but for the XPATH.

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

For more examples you can discover [unit tests](https://github.com/nachg/xpathqs/tree/master/src/test/kotlin/org/nachg/xpathqs/core/selector).


## License

This XpathQS lib is released under
the [MIT License](https://github.com/nachg/xpathqs/blob/master/LICENSE).