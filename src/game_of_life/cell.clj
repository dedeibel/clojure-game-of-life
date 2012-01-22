(ns game_of_life.cell)

(defrecord Cell [x y alive])

(defn new_cell 
  ([x y] (Cell. x y true))
  ([x y alive] (Cell. x y alive))
)

(defn survives [number_of_neighbours]
  (cond
    (= 2 number_of_neighbours) true
    (= 3 number_of_neighbours) true
    true false
  )
)

(defn comes_to_life [number_of_neighbours]
    (= number_of_neighbours 3)
)

(defn next_cell_state [cell number_of_neighbours]
  (if (:alive cell)
		(survives number_of_neighbours)
		(comes_to_life number_of_neighbours)
  )
)

(defn next_cell_state [cell number_of_neighbours]
  (if (:alive cell)
		(survives number_of_neighbours)
		(comes_to_life number_of_neighbours)
  )
)

(defn next_cell_action [cell number_of_neighbours]
  (cond
    (and      (:alive cell)  (not (survives number_of_neighbours))) :kill
    (and (not (:alive cell)) (comes_to_life number_of_neighbours))  :revive
    (:alive cell)       :keep-alive
    (not (:alive cell)) :stay-dead
  )
)

(defn revive [cell]
  (Cell. (:x cell) (:y cell) :alive)
)

