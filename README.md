# Ultra

Hey, you know who has two thumbs and strong opinions? That's right, it's me.

Ultra is a Leiningen plugin aiming to provide an absolutely kick-ass development environment.

"Sensible defaults" be damned.

## Installation

Add the following to your `:user` profile:

```clojure
[venantius/ultra "0.1.3"]
```

## Features

*A colorized REPL!*
![colorized repl](https://venantius.github.io/ultra/images/colorized-repl.png)

*Clearer test output!*
![test output demo](https://venantius.github.io/ultra/images/test-output.png)

*Better stacktraces! In both the REPL and tests!*
![test stacktrace demo](https://venantius.github.io/ultra/images/colorized-test-stacktrace.png)

*Java object introspection at hand!*
>> INSERT JAVA OBJECT INTROSPECTION

## Motivation

...or, why didn't you just put all of this stuff in your `~/.lein/profiles.clj`?

Great question, hypothetical asker! In short, my `:user` profile was starting to 
become bloated. It was difficult to tell whether plugins were interfering with 
each other to create problems, and my `:injections` key in particular was 
starting to look a little unwieldy. 

As I got further down the route of configuring my environment, I started to add
or re-write core functionality of other tools, and eventually ended up really
rummaging around in the guts of things. At some point it just made sense to split
it out into its own project.

## Todo

- [ ] prn-diffs for lists / vectors
- [ ] prn-diffs for sets
- [ ] Sync Aviso colorscheme to whidbey.render/puget-options
- [ ] Iroh
- [ ] Slamhound?

wishlist?
- [ ] lein-kibit
- [ ] lein-bikeshed
- [ ] lein-eastwood
- [ ] repetition-hunter

## Contributing

Please open an issue here before submitting pull requests; I prefer to have documentation and consensus that either of our time will be well spent by working on it. 

Bug fixes are always appreciated and won't get too much pushback; new features will be held to a higher standard - this whole project is a massive exercise in ego, after all.

PRs for new color schemes welcome; please include screenshots in your submission.

## Special Thanks

Ultra wraps, calls, or draws inspiration from the following libraries, and their owners and authors deserve credit for doing most of the hard work:

 - [AvisoNovate/pretty](https://github.com/AvisoNovate/pretty) - Better exceptions
 - [brentonashworth/lein-difftest](https://github.com/brentonashworth/lein-difftest) - Lein-difftest - test diffs using difform
 - [greglook/puget](https://github.com/greglook/puget) - Colorized pretty-printing
 - [greglook/whidbey](https://github.com/greglook/whidbey) - Puget nREPL middleware
 - [jaycfields/expectations](https://github.com/jaycfields/expectations) - The Expectations testing library
 - [pjstadig/humane-test-output](https://github.com/pjstadig/humane-test-output) - Diffs in tests using clojure.test

## License

Anything that isn't already copyright of one of the above is Copyright © 2015 W. David Jarvis.

Distributed under the Eclipse Public License 1.0, the same as Clojure.
