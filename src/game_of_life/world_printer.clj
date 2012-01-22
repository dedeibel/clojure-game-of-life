(ns game_of_life.world_printer
  (:use [game_of_life.world :only (living_cells)])
)

(defprotocol WorldPrinter
  (linebreak [this])
  (dead      [this])
  (living    [this])
)

(defrecord CliWorldPrinter []
  WorldPrinter
  (linebreak [this] (println))
  (dead      [this] (print \space))
  (living    [this] (print \X))
)

(defprotocol PrintControler
  (out [this])
)

(defrecord PrintControlerImpl [curx cury cells printer]
  PrintControler
  (out [this]
    (prn curx cury cells printer)
  )
  ; X und Y werden zunehmend erhoeht
  ; Wenn Y nicht uebereinstimmt, wird linebreak ausgegeben
  ; Wenn X nicht uebereinstimmt, wird leerstelle ausgegeben
  ; Wenn X und Y mit first cells ubereinstimmt wird 'X' ausgegeben
)

(defn- find_minx
  ([cells] (find_minx nil cells))
  ([minx cells]
    (if (seq cells)
      (if (< minx (:x (first cells)))
        (recur (:x (first cells)) (rest cells))
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
    (PrintControlerImpl. minx miny cells (new_printer))
  )
)

(defn sort-lo-ru [cells]
  (sort-by #(vector (:y %) (:x %)) cells)
)

(defn to_string [world]
  (out (new_print_controler (sort-lo-ru (living_cells world))))
)

