package com.example.visualization.settings

import android.graphics.Color
import com.example.visualization.Tile
import com.example.visualization.states.PickState

object Settings {
    // PICK_START indicates if user want to pick
    var PICK_STATE: PickState = PickState.NONE

    var PICKED_START: Tile? = null
    var PICKED_STOP: Tile? = null

    val COLOR_DEFAULT = Color.parseColor("#f4f2f8")
    val COLOR_START = Color.parseColor("#8AC926")
    val COLOR_STOP = Color.parseColor("#FF595E")
    val COLOR_WALL = Color.parseColor("#251d35")
    val COLOR_CALCULATED = Color.parseColor("#6FBCEC")
    val COLOR_PATH = Color.parseColor("#D4EFA9")

    var TILES: List<List<Tile>>? = null
}