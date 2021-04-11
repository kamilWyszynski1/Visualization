package com.example.visualization.const

// INDICES contain list of points that relaying on node's position should be its neigbor
val INDICES = arrayOf(
    arrayOf(1, 1),
    arrayOf(1, 0),
    arrayOf(1, -1),
    arrayOf(-1, 1),
    arrayOf(-1, 0),
    arrayOf(-1, -1),
    arrayOf(0, -1),
    arrayOf(0, 1)
)

// FULL_SCREEN indicates that tiles should be placed on etire screen, requires size
val FULL_SCREEN = -1