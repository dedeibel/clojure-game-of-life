(ns game_of_life.world)

(defprotocol World
  (neighbours_of [this x y])
  (living_cells [this])
  (invigorate [this x y])
  (alive [this x y])
  (kill [this x y])
  (retrieve [this x y])
)

(defn- make_key [x y]
  [x y]
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
  (invigorate [this x y]
    (new_world (conj grid (make_key x y)))
  )
  (alive [this x y]
    (get grid (make_key x y) false)
  )
  (kill [this x y]
    (new_world (disj grid (make_key x y)))
  )
  (retrieve [this x y]
    (get grid (make_key x y))
  )
  (neighbours_of [this x y]
    (zipmap
      (neighbour_keywords)
      (for 
        [
          xOffset [-1 0 1] yOffset [-1 0 1]
          :when (not (= 0 xOffset yOffset))
        ]
        (retrieve this (+ x xOffset) (+ y yOffset))
      )
    )
  )
)

(defn new_world
  ([] (SimpleWorld. #{}))
  ([world] (SimpleWorld. world))
)

