(ns game_of_life.world_builder
  (:use [game_of_life.world :only (new_world invigorate)])
)

(defprotocol CellBuilder
  (linebreak [this])
  (dead      [this])
  (living    [this])
)

(defrecord CellBuilderCursor [x y world new_cell]
  CellBuilder
  (linebreak [this] (CellBuilderCursor. 0 (inc y) world new_cell))
  (dead      [this] (CellBuilderCursor. (inc x) y world new_cell))
  (living    [this] (CellBuilderCursor. (inc x) y
                  (invigorate world (new_cell x y)) new_cell))
)

(defn new_builder [world new_cell]
  (CellBuilderCursor. 0 0  world new_cell)
)

(defn- parse_character [builder world_desc]
  (if-let [desc (seq world_desc)]
    (recur 
      (case (first desc)
        \X (living builder)
        \x (living builder)
        \. (dead builder)
        \space (dead builder)
        \newline (linebreak builder)
        \, builder
        (throw (IllegalArgumentException.
          (str "Invalid input char" (first desc))))
      )
      (rest desc)
    )
    (:world builder)
  )
)

(defn from_string [world_desc new_cell]
  (parse_character (new_builder (new_world) new_cell) world_desc)
)

