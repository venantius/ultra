(ns ^:demo test.ultra.test-test
  "More comprehensive tests for screenshot purposes."
  (:require [clojure.test :refer :all]))

(deftest string-comparisons
  (is (= "strings equal" "strings equal"))
  (is (= "balloon" "baloon"))
  (is (= "animal" "imal")))

(deftest comparing-maps
  (is (= {:gamma {:beta "Other thing"
                  :boogaloo "Is a mighty fine shoe."
                  :wizards "Are the mightest kings of yore."
                  }
          :zeta {:hehe "feh"}
          :bonk true
          :wonk false}
         {:gamma {:zeta "Wizards"
                  :wimpidy-womp "Is no place to romp."
                  :orcs "Are totally lame dudes you should not invite to your parties."}
          :zeta {:feta "heh"}
          :bonk true
          :wonk false})))
