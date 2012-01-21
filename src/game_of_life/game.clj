(ns game_of_life.game
  (:use [game_of_life.world])
  (:use [game_of_life.cell])
)

(defn next_generation_of_cell [world unchecked_cells]
  (if (seq unchecked_cells)
    (let [examined_cell (first unchecked_cells)
          number_of_living_neighbours (number_of (living
                          (neighbours_of world examined_cell)))]
      (recur
        (cond
          (and (:alive examined_cell) (not (survives number_of_living_neighbours)))
            (kill world examined_cell)
          (and (not (:alive examined_cell)) (comes_to_life number_of_living_neighbours))
            (invigorate world (revive examined_cell))
          :default world
        ) 
        (rest unchecked_cells)
      )
    )
    world
  )
)

(defn next_generation [world]
  (next_generation_of_cell
    world
    (filter #(not (nil? %1)) (vals (living_cells world)))
  )
)

