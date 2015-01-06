# Ultra

Hey, you know who has two thumbs and strong opinions? That's right, it's me.

Ultra is a Leiningen plugin that collects and configures a number of disparate
libraries and plugins all in a single place. It aims to provide an absolutely 
kick-ass development environment by providing the following:

*A colorized REPL!*
![colorized repl](https://venantius.github.io/ultra/images/colorized-repl.png)

*Better REPL stacktraces!*
>> INSERT IMAGE OF EXCEPTION THROWN IN REPL

*Colorized diffs of your test failures!*
>> INSERT IMAGE OF TEST FAILURE

*Better test stacktraces!*
![test stacktrace demo](https://venantius.github.io/ultra/images/colorized-test-stacktrace.png)

*Java object introspection at hand!*
>> INSERT JAVA OBJECT INTROSPECTION

## Motivation

...or, why didn't you just put all of this stuff in your `~/.lein/profiles.clj`?

Great question, hypothetical asker! Here's my response:

1. My `:user` profile was starting to become bloated. It was difficult to tell
whether plugins were interfering with each other to create problems, and my 
`:injections` key in particular was starting to look a little unwieldy

2. 

## Demo

A picture is worth a thousand words, so several pictures are worth many 
thousands of words.



## Usage

Add the following to your `:user` profile:

```clojure
[venantius/ultra "0.1.0"]
```

## 





## TODO

- [x] Pretty integration for REPL
- [ ] Pretty integration for tests
- [ ] lein difftest or humane-test-output for tests

wishlist?
- [ ] lein-kibit
- [ ] lein-bikeshed
- [ ] lein-eastwood

slamhound?
iroh
repetition-hunter
lein-ancient

## Contributing

Please open an issue here before submitting pull requests; I prefer to have documentation and agreement that either of our time will be well spent by working on it. 

Although the above applies equally to bugfixes and feature requests, a different standard will be held for merging in PRs. I won't be too fussy about bugfixes, but since this whole project is a massive exercise in ego and opinion I'm liable to be a bit more picky about new feature requests. 

## Special Thanks

Ultra wraps or calls the following libraries, and their owners and authors deserve all the credit for doing the hard work:

 - [AvisoNovate/pretty](https://github.com/AvisoNovate/pretty) - Better exceptions
 - [greglook/puget](https://github.com/greglook/puget) - Colorized pretty-printing
 - [greglook/whidbey](https://github.com/greglook/whidbey) - Puget nREPL middleware

## License

Distributed under the Eclipse Public License 1.0, the same as Clojure.
