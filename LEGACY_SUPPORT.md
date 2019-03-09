# Legacy Support

I'm honestly not sure Ultra supports Lein 2.8.3. So use that at your own risk.

As of Ultra `0.4.0`, Clojure 1.7.x is required due to reader conditional usage in Ultra's dependencies. Ultra `0.3.4` is the last version supporting Clojure 1.6.x. Ultra `0.4.1`+ will intelligently fall back to Ultra `0.3.4` if it detects a Clojure version that is below `1.7.0`.

Leiningen version 2.5.2+  
JDK 8

Note that versions of Ultra prior to 0.5.0 will not play nicely with Clojure 1.9.

