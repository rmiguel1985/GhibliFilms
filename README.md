# GhibliFilms [![Build Status](https://app.bitrise.io/app/5722857c-4db7-4d47-b5a5-2e7ee0e62a6e/status.svg?token=WJoLphygJ4-ozyHZYeTftA&branch=master)](https://app.bitrise.io/app/5722857c-4db7-4d47-b5a5-2e7ee0e62a6e) [![Coverage Status](https://coveralls.io/repos/github/rmiguel1985/GhibliFilms/badge.svg)](https://coveralls.io/github/rmiguel1985/GhibliFilms)

The purpose of this repo is to create an application that tries to
implement Clean Architecture principles with compose and architecture components in Kotlin.

Application simply shows a list of Ghibli movies and lets user to get detail info of them.

There are a lot of things to improve, **this is a work in progress!!**

Libraries
=========

### Android
* [Coil][1]
* [Kotlinx serialization][2]
* [JetPak Compose][3]
* [Koin][4]
* [Retrofit][5]
* [kotlinx.serialization][6]
* [OkHttp][7]
* [Coroutines][8]
* [Room Persistence Library][9]

### Testing
* [Junit 5][10]
* [Corotuines test][11]
* [Mockk][12]
* [MockWebServer][13]

### Debugging
* [Timber][14]
* [LeakCanary][15]

[1]: https://coil-kt.github.io/coil/compose/ 
[2]: https://github.com/Kotlin/kotlinx.serialization
[3]: https://developer.android.com/jetpack/compose
[4]: https://insert-koin.io/
[5]: https://github.com/square/retrofit
[6]: https://github.com/Kotlin/kotlinx.serialization
[7]: https://github.com/square/okhttp
[8]: https://github.com/Kotlin/kotlinx.coroutines
[9]: https://developer.android.com/topic/libraries/architecture/room
[10]: https://github.com/junit-team/junit5
[11]: https://github.com/Kotlin/kotlinx.coroutines/tree/master/core/kotlinx-coroutines-test
[12]: https://github.com/mockk/mockk
[13]: https://github.com/square/okhttp/tree/master/mockwebserver
[14]: https://github.com/JakeWharton/timber
[15]: https://github.com/square/leakcanary

Presentation Layer
==================
With viewModelScope all lifecycle is managed

Domain Layer
============
The domain layer implements access repository and if needed executes business logic (check **GetGhibliFilmUseCaseImpl**).

Data Layer
==========
- **Repository**: exposes functions to get data.
- **Policy**: Only CloudWithCache (**GhibliFilmsCloudWithCachePolicyImpl**) is implemented but could be there:
    - *CloudWithCache*: the first attempt is to get data from cloud and if there's
      a successful response save it to disk. If there's no access to cloud, then if
      there's local data it is returned.
    - *CacheWithCloud*: the first attempt is to get data from disk (if it is not expired expiration) 
      and if there's a successful response return it. If there's no disk data, then get data from
      cloud and if there's a successful response save it to disk and return it.
    - *OnlyCloud*: gets data from cloud if it's possible.
- **Data Sources**
    - Cloud: using Retrofit.
    - Disk: Using Room.

Testing
==========
There are resources for both instrumental and unit tests.
- Andriod Test: On test assets folder there's a json with sample data.

- Unit Test: On test resource folders there are two json. One wiht sample
  daga and another with malformed data.


>Instrumental tests are not fully covering UI. There are tests mainly for Room (because
a context is needed) and basic instrumentation for Compose.

### Presentation Layer
The test verify that StateFlow is updated with the expect Ui State on success and failure responses from UseCase.

### Data Layer
There are positive and negative test for:
- Room
- Retrofit
- Policies

### Jacoco
There's a jacoco script that enables a global coverage since Android Studio only allows coverage with Junit tests.

With the command:
```gradle
./gradlew clean JacocoDebugCodeCoverage
```
A report will be generated at **$rootProjectDir/app/build/reports/jacoco/JacocoDebugCodeCoverage/html/index.html**

## Detekt
Detekt is included as code analysis

With the command:
```gradle
./gradlew detekt
```
Analysis will be executed and report generated at **$rootProjectDir/app/build/reports/detekt/detekt-report.html**

Compile & run
==============
Just clone or download code and add youtube api key. You will need to define **YOUTUBE_API_KEY** in
your **local.properties**
```
YOUTUBE_API_KEY=api_key
```

License
=====

```
            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
                    Version 2, December 2004

 Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>

 Everyone is permitted to copy and distribute verbatim or modified
 copies of this license document, and changing it is allowed as long
 as the name is changed.

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

  0. You just DO WHAT THE FUCK YOU WANT TO.
