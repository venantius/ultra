# Ultra
[![Build Status](https://travis-ci.org/venantius/ultra.svg?branch=adding_java)](https://travis-ci.org/venantius/ultra)
[![Dependency Status](https://www.versioneye.com/user/projects/54b5674f050646ca5c000068/badge.svg?style=flat)](https://www.versioneye.com/user/projects/54b5674f050646ca5c000068)

Hey, you know who has two thumbs and strong opinions? That's right, it's me.

Ultra is a Leiningen plugin aiming to provide an absolutely kick-ass development environment.

## Installation and Configuration

To install and configure Ultra, add something like the following to your `~/.lein/profiles.clj`

```clojure
{:user {:plugins [[venantius/ultra "0.1.6"]]
        :ultra {:color-scheme :solarized_dark}}}
```

## Features

*A colorized REPL!*
![colorized repl](https://venantius.github.io/ultra/images/colorized-repl.png)

*Clearer test output!*
![test output demo](https://venantius.github.io/ultra/images/test-output.png)

*Better stacktraces!*
![test stacktrace demo](https://venantius.github.io/ultra/images/colorized-test-stacktrace.png)

*Java object introspection via [Iroh](https://github.com/zcaudate/iroh)!*
![java introspection](https://venantius.github.io/ultra/images/java-interop.png)

### Color schemes

At the moment Ultra supports the following color schemes:
 - `:solarized_dark`
 - `:default`

If you want to set the colors yourself instead of using a theme you can configure them directly, e.g.:

```clojure
{:user {:plugins [[venantius/ultra "0.1.6"]]
        :ultra {:color-scheme {:delimiter [:red]
                               :tag [:red]
                               :nil [:cyan]
                               :boolean [:cyan]
                               :number [:cyan]
                               :string [:cyan]
                               :character [:cyan]
                               :keyword [:green]
                               :symbol nil
                               :function-symbol [:blue]
                               :class-delimiter [:blue]
                               :class-name nil
                               :exception nil}}}}
```

## Todo

- [ ] Intern Iroh stuff into ultra.reflect
http://stackoverflow.com/questions/20831029/how-is-it-possible-to-intern-macros-in-clojure
- [ ] Only run add-ultra once
- [ ] prn-diffs for lists / vectors
- [ ] prn-diffs for sets
- [ ] Jig / component?
- [ ] Pomegranate for hot dependency loading

wishlist?
- [ ] lein-kibit
- [ ] lein-bikeshed
- [ ] lein-eastwood
- [ ] repetition-hunter

## Contributing

Please open an issue here before submitting pull requests; I prefer to have documentation and consensus that either of our time will be well spent by working on it. 

Bug fixes are always appreciated and won't get too much pushback; new features will be held to a higher standard - this whole project is a massive exercise in ego, after all.

Pull Requests for new color schemes welcome; please include screenshots in your submission.

## Motivation

...or, why didn't you just put all of this stuff in your `~/.lein/profiles.clj`?

Great question, hypothetical asker! In short, my `:user` profile was starting to 
become bloated. It was difficult to tell whether plugins were interfering with 
each other, and my `:injections` key in particular was 
starting to look a little unwieldy. 

As I got further down the road of configuring my environment, I started to re-write
the core functionality of my other tools, and eventually ended up deep
in the guts of things. At some point it just made sense to split
it out into its own project.

## Special Thanks

Ultra wraps, calls, or draws inspiration from the following libraries, and their owners and authors deserve credit for doing most of the hard work:

 - [AvisoNovate/pretty](https://github.com/AvisoNovate/pretty) - Better exceptions
 - [brentonashworth/lein-difftest](https://github.com/brentonashworth/lein-difftest) - Lein-difftest - test diffs using difform
 - [greglook/puget](https://github.com/greglook/puget) - Colorized pretty-printing
 - [greglook/whidbey](https://github.com/greglook/whidbey) - Puget nREPL middleware
 - [jaycfields/expectations](https://github.com/jaycfields/expectations) - The Expectations testing library
 - [pjstadig/humane-test-output](https://github.com/pjstadig/humane-test-output) - Diffs in tests using clojure.test
 - [zcaudate/iroh](https://github.com/zcaudate/iroh) - Java reflection made easy

## License

Copyright © 2015 W. David Jarvis for anything that someone above didn't write first.

Distributed under the Eclipse Public License 1.0, the same as Clojure.
