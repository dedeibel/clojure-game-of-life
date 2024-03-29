(ns game_of_life.game
  (:use [game_of_life.world])
  (:use [game_of_life.cell])
)

(defn- next_generation_of_cell [new_world world unchecked_cells]
  (str "Calculates the next generation of each cell. It has the "
       "new world as input which will be base of the resulting "
       "output. The second parameter is the world from the last "
       "generation, the new world can't be used to calculate the "
       "new cell state. The last parameter is the list of cells "
       "that have to be checked. Ideally this is a set of all "
       "currently living cells and their neighbours.")
  (if (seq unchecked_cells)
    (let [examined_cell (first unchecked_cells)
          number_of_living_neighbours (number_of (living
                          (neighbours_of world examined_cell)))]
      (recur
        (let [action (next_cell_action examined_cell number_of_living_neighbours)]
          (case action
            :kill    (kill new_world examined_cell)
            :revive  (invigorate new_world (revive examined_cell))
            new_world
        ))
        world
        (rest unchecked_cells)
      )
    )
    new_world
  )
)

(defn- cells_to_check_step [world living_cells]
  (str "Calculates all living cells and their neighbours of the given "
       "world. This is usefull to narrow the set of cell you have to "
       "look at for the next generation of the world.")
  (lazy-seq
    (if (seq living_cells)
      (into
        [(first living_cells)]
        (into
          (vals (dead (all_neighbours_of world (first living_cells) new_cell)))
          (cells_to_check_step world (rest living_cells))
        )
      )
      []
    )
  )
)

(defn next_generation [world]
  "Calculates the next generation of the world."
  (next_generation_of_cell
    world
    world
    (cells_to_check_step world (living_cells world))
  )
)

