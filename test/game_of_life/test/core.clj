(ns game_of_life.test.core
  (:use [game_of_life.core])
  (:use [clojure.test]))

(deftest test-one
  (is (= 1 (one)))
)
