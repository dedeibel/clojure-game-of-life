(ns game_of_life.world
  (:use game_of_life.cell)
  (:use name.benjaminpeter.util)
)

(defprotocol World
  (neighbours_of [this cell] [this x y])
  (all_neighbours_of [this cell create_cell] [this x y create_cell])
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

(defrecord SimpleWorld [
    grid
  ]

  World
  (living_cells [this] (vals grid))
  (invigorate [this cell]
    (SimpleWorld. (assoc grid (make_key cell) cell))
  )
  (alive [this x y]
    (if-let [cell (get grid (WorldKey. x y))]
      (:alive cell)
      false
    )
  )
  (kill [this cell]
    (SimpleWorld. (dissoc grid (make_key cell)))
  )
  (kill [this x y]
    (kill this (WorldKey. x y))
  )
  (retrieve [this x y]
    (get grid (WorldKey. x y))
  )
  (all_neighbours_of [this cell create_cell]
  ; bpeter todo macro the zipmap thing?
    (zipmap
      (neighbour_keywords)
      (for [
          xOffset [-1 0 1] yOffset [-1 0 1]
          :when (not (= 0 xOffset yOffset))
        ]
        (let [cur_x (+ (:x cell) xOffset)
              cur_y (+ (:y cell) yOffset)]
          (if-let [living_cell (retrieve this cur_x cur_y)]
            living_cell
            (create_cell cur_x cur_y false)
          )
        )
      )
    )
  )
  (all_neighbours_of [this x y create_cell]
    (all_neighbours_of this (WorldKey. x y) create_cell)
  )
  (neighbours_of [this cell]
    (zipmap
      (neighbour_keywords)
      (for [
          xOffset [-1 0 1] yOffset [-1 0 1]
          :when (not (= 0 xOffset yOffset))
        ]
        (let [cur_x (+ (:x cell) xOffset)
              cur_y (+ (:y cell) yOffset)]
          (retrieve this cur_x cur_y)
        )
      )
    )
  )
  (neighbours_of [this x y]
    (neighbours_of this (WorldKey. x y))
  )
)

(defn new_world
  ([] (SimpleWorld. {}))
)

(defn number_of [neighbours]
  (count (vals neighbours))
)

(defn living [neighbours]
  (filter-map [key val] (:alive val) neighbours)
)

(defn dead [neighbours]
  (filter-map [key val] (not (:alive val)) neighbours)
)

