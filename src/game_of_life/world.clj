(ns game_of_life.world)

(defprotocol World
  (neighboursOf [this x y])
  (allCells [this])
  (put [this x y cell])
  (retrieve [this x y])
)

(defn- makeKey [x y]
  (str x "x" y)
)

(declare newWorld)

(defrecord SimpleWorld [
    grid
  ]

  World
  (allCells [this] grid)
  (put [this x y cell]
    (newWorld (assoc grid (makeKey x y) cell))
  )
  (retrieve [this x y]
    (grid (makeKey x y))
  )
  (neighboursOf [this x y]
    (for 
      [
        xOffset [-1 0 1] yOffset [-1 0 1]
        :when (not (= 0 xOffset yOffset))
      ]
      (retrieve this (+ x xOffset) (+ y yOffset))
    )
  )
)

(defn newWorld
  ([] (SimpleWorld. {}))
  ([world] (SimpleWorld. world))
)

