(ns game_of_life.test.world
  (:use [clojure.test])
  (:use [game_of_life.cell])
  (:use [game_of_life.world])
)

(deftest test_add
  (let [subject (newDeadCell)]
    (is (= subject (-> (newWorld) (put 0 0 subject) (retrieve 0 0))))
    (is (= nil     (-> (newWorld) (put 0 0 subject) (retrieve 0 1))))
    (is (= subject (-> (newWorld) (put 1 0 subject) (retrieve 1 0))))
    (is (= subject (-> (newWorld) (put 0 1 subject) (retrieve 0 1))))
  )
)

(deftest test_add_multi
  (is
    (= (newDeadCell)
       (-> (newWorld)
           (put 1 1 (newLivingCell))
           (put 2 3 (newDeadCell))
           (put 10 22 (newDeadCell))
           (retrieve 2 3)
       )
    )
  )
)

(deftest test_neighbours_not_self
  (let [world (-> (newWorld)
      (put 1 1 (newLivingCell))
      (put 0 0 (newLivingCell))
    )]
    (is (= 1
      (count
        (filter
          #(= (newLivingCell) %1)
          (vals (neighboursOf world 1 1))
        )
      )
    ))
    (is (= (newLivingCell) (:nw (neighboursOf world 1 1))))
  )
)

(deftest test_neighbours_keys
  (is (=
        (sort [:nw :n :ne :w :e :sw :s :se])
        (sort (keys (neighboursOf (newWorld) 1 1)))
      )
  )
)

(deftest test_neighbours_vals
  (is
    (= 8 (count (vals (neighboursOf (newWorld) 1 1))))
  )
)

(deftest test_neighbours_none
  (let [world (newWorld)]
    (is (not (contains? (vals (neighboursOf world 1 1)) (newLivingCell))))
    (is (not (contains? (vals (neighboursOf world 1 1)) (newDeadCell))))
  )
)

