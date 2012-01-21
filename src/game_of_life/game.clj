(ns game_of_life.game
  (:use [game_of_life.world])
  (:use [game_of_life.cell])
)

(defn next_generation [world]
  (next_generation_of_cell
    world
    (filter #(not (nil? %1)) (vals (living_cells world)))
  )
)

(defn next_generation_of_cell [world unchecked_cells]
  (let [examined_cell (first unchecked_cells)]
    (recur (if (next_cell_state (:alive examined_cell)
)
