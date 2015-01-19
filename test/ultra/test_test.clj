(ns ^:demo ultra.test-test
  "More comprehensive tests for screenshot purposes."
  (:require [clojure.test :refer :all]))

(deftest string-comparisons
  (is (= "strings equal" "strings equal"))
  (is (= "balloon" "baloon"))
  (is (= "animal" "imal")))

(deftest default-comparison-large
  (let [long-vector [:id
                     :other-id
                     :third-id
                     :you-tired-of-ids-yet
                     :no?
                     :not-yet?
                     :well-how-about-some-more?
                     :howd-you-like-them-apples!]]
    (is (every? (set (keys {:a 1 :b 5})) long-vector))))

(deftest comparing-maps
  (is (= {:gamma {:beta "Other thing"
                  :boogaloo "Is a mighty fine shoe."
                  :wizards "Are the mightest kings of yore."}
          :zeta {:hehe "feh"}
          :bonk true
          :wonk false}
         {:gamma {:zeta "Wizards"
                  :wimpidy-womp "Is no place to romp."
                  :orcs "Are totally lame dudes you should not invite to your parties."}
          :zeta {:feta "heh"}
          :bonk true
          :wonk false})))

(deftest smaller-map-diffs
  (is (= {:gamma true}
         {:gamma true
          :beta false}))
  (is (= {:alpha 1
          :beta 2}
         {:alpha 1})))
