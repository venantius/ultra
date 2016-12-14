# Ultra

[![CircleCI](https://circleci.com/gh/venantius/ultra.svg?style=svg)](https://circleci.com/gh/venantius/ultra)
[![Join the chat at https://gitter.im/venantius/ultra](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/venantius/ultra?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Dependency Status](https://www.versioneye.com/user/projects/54b5674f050646ca5c000068/badge.svg?style=flat)](https://www.versioneye.com/user/projects/54b5674f050646ca5c000068)

Hey, you know who has two thumbs and strong opinions? That's right, it's me.

Ultra is a Leiningen plugin for an absolutely kick-ass development environment.

I've written a blog post describing Ultra in greater depth [here](http://blog.venanti.us/ultra).

## Installation

To install Ultra, just add the following to your `~/.lein/profiles.clj`

```clojure
{:user {:plugins [[venantius/ultra "0.5.0"]]}}
```

### Requirements

As of Ultra `0.4.0`, Clojure 1.7.x is required due to reader conditional usage in Ultra's dependencies. Ultra `0.3.4` is the last version supporting Clojure 1.6.x. Ultra `0.4.1`+ will intelligently fall back to Ultra `0.3.4` if it detects a Clojure version that is below `1.7.0`.

Leiningen version 2.5.2+
JDK 8

Note that versions of Ultra prior to 0.5.0 will not play nicely with current alpha releases of Clojure 1.9 (and therefore are unlikely to play nicely with Clojure 1.9 when it is finally released).

#### ClojureScript Support

At the moment, Ultra doesn't have ClojureScript support at the REPL. The relevant upstream issue to track work on this is https://github.com/greglook/puget/issues/27; from there, Whidbey will need to be updated, and then Ultra will be able to consume the changes.

## Features
For a detailed list of features, check out the [wiki](https://github.com/venantius/ultra/wiki). Here's the highlight reel:

*A colorized REPL!*
![colorized repl](https://venantius.github.io/ultra/images/colorized-repl.png)

*Syntax-highlighted `source`!*
![colorized source](https://venantius.github.io/ultra/images/colorized-source.png)

*[Clearer test output](https://github.com/venantius/ultra/wiki/Tests)!*
![test output demo](https://venantius.github.io/ultra/images/test-output.png)

*Better stacktraces!*
![test stacktrace demo](https://venantius.github.io/ultra/images/colorized-test-stacktrace.png)

## Configuration

All of the above features are enabled by default, but can be turned off by setting a `false` flag in your profile. If you wanted Ultra to essentially no-op, your configuration map would look like this:

```clojure
{:ultra {:repl         false
         :stacktraces  false
         :tests        false}}}}
```
### REPL Configuration

Ultra uses [Whidbey](https://github.com/greglook/whidbey) as its pretty-printing engine, and supports all of Whidbey's configuration flags.

```clojure
{:ultra {:repl {:width 180
                :map-delimiter ""
                :extend-notation true
                :print-meta true
                 ...}}}
```

###### `:width`

Number of characters to try to wrap pretty-printed forms at.

###### `:print-meta`

If true, metadata will be printed before values.

###### `:sort-keys`

Print maps and sets with ordered keys. Defaults to true, which will sort all collections. If a number, counted collections will be sorted up to the set size. Otherwise, collections are not sorted before printing.

###### `:map-delimiter`

The text placed between key-value pairs in a map.

###### `:map-coll-separator`

The text placed between a map key and a collection value. The keyword :line will cause line breaks if the whole map does not fit on a single line.

###### `:seq-limit`

If set to a positive number, then lists will only render at most the first n elements. This can help prevent unintentional realization of infinite lazy sequences.

## Contributing

Please open an issue here before submitting pull requests; I prefer to have documentation and consensus that either of our time will be well spent by working on it. When opening an issue -- particularly for bugs -- please refer to [CONTRIBUTING.md](https://github.com/venantius/ultra/blob/master/CONTRIBUTING.md)

Bug fixes and code cleanup are always appreciated and won't get too much pushback; new features will be held to a higher standard - this whole project is something of a massive exercise in ego, after all.

## Motivation

...or, why didn't you just put all of this stuff in your `~/.lein/profiles.clj`?

In short, my `:user` profile was starting to become bloated. It was difficult to tell whether plugins were interfering with each other, and my `:injections` key in particular was starting to look a little unwieldy.

At some point I realized I was up to my neck in alligators and that it was time to push things into a standalone repository.

## Special Thanks

Ultra wraps, calls, or draws inspiration from the following libraries, and their owners and authors deserve credit for doing most of the hard work.

 - [AvisoNovate/pretty](https://github.com/AvisoNovate/pretty) - Better exceptions
 - [brentonashworth/lein-difftest](https://github.com/brentonashworth/lein-difftest) - Lein-difftest - test diffs using difform
 - [greglook/puget](https://github.com/greglook/puget) - Colorized pretty-printing
 - [greglook/whidbey](https://github.com/greglook/whidbey) - Puget nREPL middleware
 - [jaycfields/expectations](https://github.com/jaycfields/expectations) - The Expectations testing library
 - [pjstadig/humane-test-output](https://github.com/pjstadig/humane-test-output) - Diffs in tests using clojure.test
 - [zcaudate/vinyasa](https://github.com/zcaudate/vinyasa) - Give your Clojure workflow more flow

## License

In some cases, I've borrowed code snippets from libraries above and re-written them. Where that is the case, the Copyright of the original author[s] remains in effect. Any modifications to their code, as well as all original content, is Copyright © 2016 W. David Jarvis.

Distributed under the Eclipse Public License 1.0, the same as Clojure.
