(ns game_of_life.cell)

(defprotocol Cell
  ; private methods?
  (survives [this neighbours])
  (comes_to_life [this neighbours])
  (next_cell_state [this neighbours])
)

(defrecord SimpleCell [
		alive
  ]
  
  Cell
  (survives [this neighbours]
    (cond
      (= 2 neighbours) true
      (= 3 neighbours) true
      true false
    )
  )
  (comes_to_life [this neighbours]
    (= neighbours 3)
  )
  (next_cell_state [this neighbours]
    (if (:alive this)               
			(survives this neighbours)
  		(comes_to_life this neighbours)
    )
  )
)

(defn newDeadCell [] (SimpleCell. false))
(defn newLivingCell [] (SimpleCell. true))

