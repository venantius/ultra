(ns ^:demo ultra.demo.map-test
  "Tests for maps intended to fail for diff purposes."
  (:require [clojure.test :refer :all]))

(deftest comparing-maps-1
  (is (= {:alpha "Thing"
          :gamma "Triple thing"}
         {:alpha "Thing"
          :beta "Other thing"})))

(deftest comparing-maps-2
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

(deftest comparing-maps-3
  (is (= {:gamma true}
         {:gamma true
          :beta false}))
  (is (= {:alpha 1
          :beta 2}
         {:alpha 1})))

