(ns game_of_life.test.world
  (:use [clojure.test])
  (:use [game_of_life.cell])
  (:use [game_of_life.world])
)

(deftest test_add
  (is (-> (new_world) (invigorate 0 0) (alive 0 0)))
  (is (not (-> (new_world) (invigorate 0 0) (alive 0 1))))
  (is (-> (new_world) (invigorate 1 0) (alive 1 0)))
)

(deftest test_add_multi
  (is
    (-> (new_world)
        (invigorate 1 1)
        (invigorate 2 3)
        (invigorate 10 22)
        (alive 2 3)
    )
  )
)

(deftest test_alive_kill
  (is (not (-> (new_world) (invigorate 0 1) (kill 0 1) (alive 0 1))))
)

(deftest test_neighbours_not_self
  (let [world (-> (new_world)
      (invigorate 1 1)
      (invigorate 0 0)
    )]
    (is (= 1
      (count
        (filter
          #(when %1 true)
          (vals (neighbours_of world 1 1))
        )
      )
    ))
    (is (:nw (neighbours_of world 1 1)))
  )
)

(deftest test_neighbours_keys
  (is (=
        (sort [:nw :n :ne :w :e :sw :s :se])
        (sort (keys (neighbours_of (new_world) 1 1)))
      )
  )
)

(deftest test_neighbours_vals
  (is
    (= 8 (count (vals (neighbours_of (new_world) 1 1))))
  )
)

(deftest test_neighbours_none
  (let [world (new_world)]
    (is (not (some #(true? %1) (vals (neighbours_of world 1 1)))))
    (is (not (some #(true? %1) (vals (neighbours_of world 1 1)))))
  )
)

