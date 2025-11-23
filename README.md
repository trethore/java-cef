# Java Chromium Embedded Framework (JCEF)

The Java Chromium Embedded Framework (JCEF) is a simple framework for embedding Chromium-based browsers in Java applications.

## Quick Links
* Building JCEF - https://bitbucket.org/chromiumembedded/java-cef/wiki/BranchesAndBuilding
* Support Forum - http://magpcss.org/ceforum/viewforum.php?f=17
* Issue Tracker - https://github.com/chromiumembedded/java-cef/issues
* Downloads - https://github.com/jcefmaven/jcefbuild
* Maven/Gradle Artifacts - https://github.com/jcefmaven/jcefmaven
* Donations - http://www.magpcss.org/ceforum/donate.php

## Introduction
CEF is a BSD-licensed open source project founded by Marshall Greenblatt in 2008 and based on the Google Chromium project. Unlike Chromium itself, which focuses on Google Chrome development, CEF targets embedded browser use cases in third-party applications. It hides Chromium/Blink complexity behind stable APIs, release branches that track specific Chromium releases, and ready-made binary distributions. Most features ship with sensible defaults, so minimal integration is needed. CEF is embedded in hundreds of products worldwide (see the CEF Wikipedia page for examples) and is used to:

* Embed an HTML5-compliant browser control in an existing native application.
* Create a lightweight native “shell” that hosts a UI built with web tech.
* Render web content off-screen inside custom drawing frameworks.
* Host automated testing of web properties and apps.

CEF supports multiple languages and operating systems with performance and ease of use in mind. JCEF provides the Java wrapper around CEF’s native APIs, offering deep integration points for custom protocols, JavaScript bindings, resource control, printing, context menus, and more.

## Building JCEF
JCEF extends the Chromium Embedded Framework (CEF) project at https://bitbucket.org/chromiumembedded/cef/. The development branch tracks the latest CEF3 release branch. Source can be downloaded, built, and packaged into a binary distribution that you can ship standalone without further CEF/Chromium source dependencies. See the [BranchesAndBuilding](https://bitbucket.org/chromiumembedded/java-cef/wiki/BranchesAndBuilding) wiki for detailed build steps.

> Note for Linux builds: the AWT/off-screen renderer and the generated Javadoc rely on the JogAmp
> JOGL jars in `third_party/jogamp/jar`. If you build with the GLFW backend only, you can skip the
> AWT pieces, but running `ant doc` or compiling the AWT/OSR classes still requires those JOGL
> artifacts to be present on the classpath.

## Helping Out
JCEF is still evolving. You can help by:

- Voting for issues important to you in the [JCEF issue tracker](https://github.com/chromiumembedded/java-cef/issues).
- Filing well-documented bugs and feature requests (include JCEF/CEF version, OS, compiler, and repro details). Use the [JCEF Forum](http://magpcss.org/ceforum/viewforum.php?f=17) for usage questions.
- Writing unit tests for new or existing functionality.
- Contributing patches or pull requests (follow the [Chromium coding style](http://www.chromium.org/developers/coding-style); target the current JCEF master unless fixing a release branch).

## Thanks To
This fork builds on the work of these repositories and their maintainers:

- https://github.com/chromiumembedded/java-cef
- https://github.com/CCBlueX/java-cef
- https://github.com/CinemaMod/mcef
