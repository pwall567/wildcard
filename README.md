# wildcard

[![Build Status](https://github.com/pwall567/wildcard/actions/workflows/build.yml/badge.svg)](https://github.com/pwall567/wildcard/actions/workflows/build.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/static/v1?label=Kotlin&message=v2.0.21&color=7f52ff&logo=kotlin&logoColor=7f52ff)](https://github.com/JetBrains/kotlin/releases/tag/v2.0.21)
[![Maven Central](https://img.shields.io/maven-central/v/net.pwall.text/wildcard?label=Maven%20Central)](https://search.maven.org/search?q=g:%22net.pwall.text%22%20AND%20a:%22wildcard%22)

Wildcard comparison function for Kotlin

## Background

It is often useful to be able to match a string using simple wildcard rules, using a pattern with `?` to match any
single character in that position, or `*` to match zero or more characters.

Of course, the same functionality can be achieved using a `Regex`, but regular expression syntax is unnecessarily
complex for many cases.
Take the case of a filter mechanism which requires a user to enter a string to match against a set of filenames; `*.txt`
is much simpler to enter (and more intuitive, for anyone familiar with the `*` notation) than `\.txt$`.
Someone unfamiliar with regular expression syntax might not know that the expression needs the anchor `$` character to
disallow any further characters at the end of the string.
And they also might not realise that a dot matches any character, so to match a dot exactly it needs to be preceded by a
backslash (and depending on the context, that backslash may need to be &ldquo;escaped&rdquo; with another backslash).

## Usage

To create a `Wildcard`:
```kotlin
    val wildcard = Wildcard("*.txt")
```

And to use that `Wildcard` in a comparison:
```kotlin
    filteredList = list.filter { wildcard matches it }
```

The `matches` function exists in two forms &ndash; as a member function on the `Wildcard` class, as shown above, and as
an extension function on `CharSequence`, taking a `Wildcard` as a parameter:
```kotlin
    filteredList = list.filter { it matches wildcard }
```
The disadvantage of the second form is that it requires an `import`:
```kotlin
import io.kstuff.text.Wildcard.Companion.matches
```

## Complex Uses

The default characters used to represent a single-characters wildcard match and a multi-character match are `?` and `*`
respectively.
If the text to be matching may contain these characters (for example, a URI may contain `?`), alternative characters may
be specified:
```kotlin
    val webAddressMatch = Wildcard("https://~/example.com?customer=~", multiMatchChar = '~')
```
The options are:

| Parameter Name    | Matches                 | Default |
|-------------------|-------------------------|:-------:|
| `singleMatchChar` | Single character        |   `?`   |
| `multiMatchChar`  | Zero or more characters |   `*`   |

To match one or more characters, the `?` and `*` (or their specified alternatives) may be combined, for example:
```kotlin
    val webAddressMatch = Wildcard("file?*.txt")
```
This pattern will match `File1.txt` or `File9999.txt`, but not `File.txt`.

## Dependency Specification

The latest version of the library is 2.0, and it may be obtained from the Maven Central repository.

### Maven
```xml
    <dependency>
      <groupId>io.kstuff.text</groupId>
      <artifactId>wildcard</artifactId>
      <version>2.0</version>
    </dependency>
```
### Gradle
```groovy
    implementation 'io.kstuff.text:wildcard:2.0'
```
### Gradle (kts)
```kotlin
    implementation("io.kstuff.text:wildcard:2.0")
```

Peter Wall

2025-01-26
