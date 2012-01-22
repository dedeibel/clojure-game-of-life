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
  (linebreak [this])
  (dead      [this])
  (living    [this])
)

(defrecord PrintControlerImpl [curx cury cells]
  ; X und Y werden zunehmend erhoeht
  ; Wenn Y nicht uebereinstimmt, wird linebreak ausgegeben
  ; Wenn X nicht uebereinstimmt, wird leerstelle ausgegeben
  ; Wenn X und Y mit first cells ubereinstimmt wird 'X' ausgegeben
)

(defn new_printer [world new_cell]
  (CliWorldPrinter.)
)

(defn- print_cell [startx starty printer cells]
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

(defn sort-lo-ru [cells]
  (sort-by #(vector (:y %) (:x %)) cells)
)

(defn to_string [world]
  (print_cell (new_printer) (sort-lo-ru (living_cells world))
)

