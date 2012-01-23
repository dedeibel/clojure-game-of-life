(ns game_of_life.world_printer
  (:use [game_of_life.world :only (living_cells)])
)

(defprotocol WorldPrinter
  (linebreak    [this])
  (dead_cell    [this])
  (living_cell  [this])
)

(defrecord CliWorldPrinter []
  WorldPrinter
  (linebreak   [this] (println))
  (dead_cell   [this] (print \space))
  (living_cell [this] (print \X))
)

(defprotocol PrintControler
  (out [this])
  (step [this curx cury cells])
)

(defrecord PrintControlerImpl [minx curx cury cells printer]
  PrintControler
  (out [this]
    (do
      (step this curx cury cells)
      (linebreak printer)
    )
  )
  (step [this curx cury cells]
  ; X und Y werden zunehmend erhoeht
  ; Wenn Y nicht uebereinstimmt, wird linebreak ausgegeben
  ; Wenn X nicht uebereinstimmt, wird leerstelle ausgegeben
  ; Wenn X und Y mit first cells ubereinstimmt wird 'X' ausgegeben
    (if-let [current (first cells)]
      (if (not= cury (:y current))
        (do (linebreak printer) (recur minx (inc cury) cells))
        (if (not= curx (:x current))
          (do (dead_cell   printer) (recur (inc curx) cury cells))
          (do (living_cell printer) (recur (inc curx) cury (rest cells)))
        )
      )
    )
  )
)

(defn- find_minx
  ([cells] (find_minx (:x (first cells)) (rest cells)))
  ([minx cells]
    (if-let [current (first cells)]
      (if (< (:x current) minx)
        (recur (:x current) (rest cells))
        (recur minx (rest cells))
      )
      minx
    )
  )
)

(defn new_printer []
  (CliWorldPrinter.)
)

(defn new_print_controler [cells]
  (let [miny (:y (first cells)) minx (find_minx cells)]
    (PrintControlerImpl. minx minx miny cells (new_printer))
  )
)

(defn sort-lo-ru [cells]
  (sort-by #(vector (:y %) (:x %)) cells)
)

(defn to_string [world]
  (out (new_print_controler (sort-lo-ru (living_cells world))))
)

