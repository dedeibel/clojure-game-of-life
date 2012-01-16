(ns game_of_life.cell)

(defn survives [neighbours]
  (cond
    (= 2 neighbours) true
    (= 3 neighbours) true
    true false
  )
)


(defn comes_to_life [neighbours]
    (= neighbours 3)
)

(defn next_cell_state [is_currently_alive neighbours]
  (if is_currently_alive
		(survives neighbours)
		(comes_to_life neighbours)
  )
)

