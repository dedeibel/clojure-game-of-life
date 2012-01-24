(ns game_of_life.core
  (:use [game_of_life.cell :only (new_cell)])
  (:use [game_of_life.world_printer :only (to_string)])
  (:use [game_of_life.world_builder :only (from_string)])
  (:use [game_of_life.game :only (next_generation)])
  (:gen-class)
)

(def blinker
" x 
, x
, x"
)

(def bipole
"xx 
,xx
,  xx
,  xx"
)

(def oscyl
"   xx
,  x  x
, x    x
,x      x
,x      x
, x    x
,  x  x
,   xx"
)

(def term54
"xxx
,x x
,x x
,    
,x x
,x x
,xxx"
)

(defn next_gen [world]
  (to_string world)
  (read-line)
  (recur (next_generation world))
)

(defn -main []
  (next_gen (from_string oscyl new_cell))
)

