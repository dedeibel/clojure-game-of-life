(ns game_of_life.world
  (:use game_of_life.cell)
)

(defprotocol World
  (neighbours_of [this cell] [this x y])
  (living_cells [this])
  (invigorate [this cell])
  (alive [this x y])
  (kill [this cell] [this x y])
  (retrieve [this x y])
)

(defrecord WorldKey [x y])
(defn- make_key [cell]
  (WorldKey. (:x cell) (:y cell))
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
  (invigorate [this cell]
    (new_world (assoc grid (make_key cell) cell))
  )
  (alive [this x y]
    (if-let [cell (get grid (WorldKey. x y))]
      (:alive cell)
      false
    )
  )
  (kill [this cell]
    (new_world (dissoc grid (make_key cell)))
  )
  (kill [this x y]
    (kill this (WorldKey. x y))
  )
  (retrieve [this x y]
    (get grid (WorldKey. x y))
  )
  (neighbours_of [this cell]
    (zipmap
      (neighbour_keywords)
      (for [
          xOffset [-1 0 1] yOffset [-1 0 1]
          :when (not (= 0 xOffset yOffset))
        ]
        (retrieve this (+ (:x cell) xOffset) (+ (:y cell) yOffset))
      )
    )
  )
  (neighbours_of [this x y]
    (neighbours_of this (WorldKey. x y))
  )
)

(defn number_of [neighbours]
  (count (vals neighbours))
)

(defmacro filter-map [bindings pred m]
  `(select-keys ~m
    (for [~bindings ~m
      :when ~pred]
      ~(first bindings)
    )
  )
)

(defn living [neighbours]
  (filter-map [key val] (:alive val) neighbours)
)

(defn new_world
  ([] (SimpleWorld. {}))
  ([world] (SimpleWorld. world))
)

