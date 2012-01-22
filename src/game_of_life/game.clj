(ns game_of_life.game
  (:use [game_of_life.world])
  (:use [game_of_life.cell])
)

(defn next_generation_of_cell [new_world world unchecked_cells]
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

(defn cells_to_check_step [world living_cells]
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
  (next_generation_of_cell
    world
    world
    (cells_to_check_step world (vals (living_cells world)))
  )
)

