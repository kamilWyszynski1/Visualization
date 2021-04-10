package com.example.visualization

import android.graphics.Color
import com.example.visualization.states.PickState

object Settings {
    // PICK_START indicates if user want to pick
    var PICK_STATE: PickState = PickState.NONE

    var PICKED_START: Tile? = null
    var PICKED_STOP: Tile? = null

    val COLOR_START = Color.GREEN
    val COLOR_STOP = Color.RED

    fun elo() {

    }
}