(ns game_of_life.test.cell
  (:use [clojure.test])
  (:use [game_of_life.cell])
)

(deftest test_survives
  ; Underpopulation
  (is (= false (-> (newDeadCell) (survives 0))))
  ; Good
  (is (= true  (-> (newDeadCell) (survives 2))))
  (is (= true  (-> (newDeadCell) (survives 3))))
  ; Overpopulation
  (is (= false (-> (newDeadCell) (survives 4))))
)

(deftest test_comes_to_life
  (is (= false (-> (newDeadCell) (comes_to_life 0))))
  (is (= true  (-> (newDeadCell) (comes_to_life 3))))
) 

(deftest test_next_cell_state
  (is (= false (next_cell_state (newDeadCell) 0)))
  (is (= true  (next_cell_state (newLivingCell) 2)))
  (is (= false (next_cell_state (newLivingCell) 1)))
  (is (= false (next_cell_state (newLivingCell) 4)))
  (is (= true  (next_cell_state (newDeadCell) 3)))
)