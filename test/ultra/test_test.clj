(ns test.ultra.test-test
  (:require [clojure.test :refer :all]))

(deftest exception-is-thrown
  (is (contains? 5 :b)))

(deftest string-comparisons
  (is (= "strings equal" "strings equal"))
  (is (= "space" "spice" "spork"))
  (is (= "balloon" "baloon"))
  (is (= "animal" "imal")))

(deftest types-that-dont-match
  (is (= "1" 2)))

(deftest comparing-maps
  (is (= {:alpha "Thing"
          :gamma "Triple thing"}
         {:alpha "Thing"
          :beta "Other thing"}))
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

(deftest comparing-things
  (is (true? true))
  (is (false? true)))
