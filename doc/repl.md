### REPL Configuration Flags

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

