## 0.2.1
 * Adds ultra.repl/pprint-source, a pretty-printer for trusted source code.

## 0.2.0
 * Moves Java function injection to be a hook sitting on clojure.tools.nrepl.server/start-server, which is a total hack but it seems to actually work, unlike literally everything else I've tried. 
 * Removes Vinyasa dependency.
 * Moving test activation from a defonce, which seemed to screw up Kibit.
 * General cleanup and refactoring.

## 0.1.9
 * Added the ability to disable Ultra components
 * Modified namespace loading for some REPL-oriented things to avoid Whidbey's import side effects

## 0.1.8
 * Removes robert hooke and humane-test-output deps
 * Adds more Java reflection tools
 * Minor tweaks to tests

## 0.1.7
 * Adding functions for Java class introspection and reflection
 * Killing unused ultra.util ns
 * General cleanup
 * Bugfix for small map diffs
 * Better diffs for list and vector comparisons

## 0.1.6
 * Bugfix release.

## 0.1.5
 * Colorscheme bugfix
 * Changed stacktrace printing to use colorscheme

## 0.1.4
 * Added support for default colorschemesa
 * Options loading from the `:ultra` key

## 0.1.3
 * Better string-diffing for tests
 * Map diffs are now aligned properly
 * Better output for test failures resulting from comparing objects of different classes

## 0.1.2
 * Added colorized, pretty-printed test output with data structure diffs

## 0.1.1
 * Started adding images to README
 * Added Aviso's `Pretty` library for pretty-printing exceptions

## 0.1.0
 * Initial release with Whidbey support
