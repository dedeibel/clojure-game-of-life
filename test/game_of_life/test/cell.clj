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

(deftest test_next_cell_action
  (is (= :stay-dead  (next_cell_action (new_cell 0 0 false) 0)))
  (is (= :revive     (next_cell_action (new_cell 0 0 false) 3)))
  (is (= :keep-alive (next_cell_action (new_cell 0 0) 2)))
  (is (= :keep-alive (next_cell_action (new_cell 0 0) 3)))
  (is (= :kill       (next_cell_action (new_cell 0 0) 1)))
  (is (= :kill       (next_cell_action (new_cell 0 0) 4)))
)

