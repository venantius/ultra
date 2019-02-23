### Stacktrace Configuration Flags

As of version `0.6.0`, Ultra uses [Pyro](https://github.com/venantius/pyro) as a stacktrace engine. This means that arguments to Pyro's configuration are passed straight along.

Note that Pyro is still relatively new and this API is liable to change.

```clojure
{:ultra {:stacktraces {:show-source true
                       :drop-nrepl-elements true
                       :hide-clojure-elements true
                       :hide-lein-elements true}}}
```

All options are set to true by default.

###### `:show-source`

If `:show-source` is set to true, Pyro will try to print out the syntax-highlighted source code corresponding to each stacktrace element.

###### `:drop-nrepl-elements`

If `:drop-nrepl-elements` is set to true, Pyro will omit any stackframe elements up to the point that the REPL's read-eval-print-loop was actually invoked.

###### `:hide-clojure-elements`

If `:hide-clojure-elements` is set to true, Pyro will omit any stackframe elements from `clojure.core`, `clojure.test`, or the compiler (`clojure.lang.*`).

###### `:hide-lein-elements`

If `:hide-lein-elements` is set to true, Pyro will omit any stackframe elements from `leiningen.core.eval`, `leiningen.core.main`, or `leiningen.test`.
