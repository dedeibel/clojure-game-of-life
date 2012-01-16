(ns game_of_life.test.cell
  (:use [clojure.test])
  (:use [game_of_life.cell])
)

(deftest test_survives
  ; Underpopulation
  (is (= false (survives 0)))
  ; Good
  (is (= true  (survives 2)))
  (is (= true  (survives 3)))
  ; Overpopulation
  (is (= false (survives 4)))
)

(deftest test_comes_to_life
  (is (= false (comes_to_life 0)))
  (is (= true  (comes_to_life 3)))
) 

(deftest test_next_cell_state
  (is (= false (next_cell_state false 0)))
  (is (= true  (next_cell_state :alive 2)))
  (is (= false (next_cell_state :alive 1)))
  (is (= false (next_cell_state :alive 4)))
  (is (= true  (next_cell_state :alive 3)))
)

