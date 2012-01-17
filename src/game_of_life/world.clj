(ns game_of_life.world)

(defprotocol World
  (neighbours_of [this cell] [this x y])
  (living_cells [this])
  (invigorate [this cell] [this x y])
  (alive [this cell] [this x y])
  (kill [this cell] [this x y])
  (retrieve [this x y])
)

(defn- neighbour_keywords []
  [:nw :n :ne :w :e :sw :s :se]
)

(declare new_world)

(defrecord SimpleWorld [
    grid
  ]

  World
  (living_cells [this] (seq grid))
  (invigorate
    ([this cell]
      (new_world (conj grid cell))
    )
    ([this x y]
      (recur this (new_cell x y))
    )
  )
  (alive 
    ([this x y]
      (recur this (new_cell x y))
    )
    ([this cell]
      (get grid cell false)
    )
  )
  (kill
    ([this x y]
      (recur this (new_cell x y))
    )
    ([this cell]
      (new_world (disj grid cell))
    )
  )
  (retrieve
    ([this x y]
      (get grid (new_cell x y))
    )
  )
  (neighbours_of
    ([this x y]
      (recur this (new_cell x y))
    )
    ([this cell]
      (zipmap
        (neighbour_keywords)
        (for 
          [
            xOffset [-1 0 1] yOffset [-1 0 1]
            :when (not (= 0 xOffset yOffset))
          ]
          (retrieve this (+ (:x cell) xOffset) (+ (:y cell) yOffset))
        )
      )
    )
  )
)

(defn new_world
  ([] (SimpleWorld. #{}))
  ([world] (SimpleWorld. world))
)
