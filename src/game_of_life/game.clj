(ns game_of_life.game
  (:use [game_ofe_life.world])
  (:use [game_ofe_life.cell])
)

(defn next_generation [world]
  (next_generation_of_cell world (living_cells world))
)

(defn next_generation_of_cell [world unchecked_cells]
  nil
)
