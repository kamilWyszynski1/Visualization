package com.example.visualization


import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import com.example.visualization.states.PickState
import com.example.visualization.states.TileState
import java.lang.Exception

class Tile(
    context: Context,
    xVal: Float,
    yVal: Float,
    size: Int,
    private val xIndex: Int,
    private val yIndex: Int
) :
    androidx.appcompat.widget.AppCompatButton(context) {

    var tileState: TileState = TileState.OPEN

    override fun toString(): String {
        return "$xIndex:$yIndex"
    }

    init {
        x = xVal
        y = yVal
        layoutParams = LinearLayout.LayoutParams(size, LinearLayout.LayoutParams.WRAP_CONTENT)
        setPadding(0, 0, 0, 0)
        backgroundTintList = ColorStateList.valueOf(Color.GRAY)

        setOnClickListener { customListener() }
    }

    private fun reset() {
        backgroundTintList = ColorStateList.valueOf(Color.GRAY)
    }

    private fun customListener() {
        println("clicked: $xIndex:$yIndex, x: $x, y: $y, with: ${Settings.PICK_STATE}")
        tileState = if (tileState == TileState.OPEN) TileState.BLOCKED else TileState.OPEN

        // TODO do not allow changing start/stop to wall e.g.
        when (Settings.PICK_STATE) {
            PickState.START -> {
                if (Settings.PICKED_START != null) {
                    Settings.PICKED_START?.reset()
                }
                Settings.PICKED_START = this
                backgroundTintList = ColorStateList.valueOf(Settings.COLOR_START)
                tileState = TileState.START
            }
            PickState.STOP -> {
                if (Settings.PICKED_STOP != null) {
                    Settings.PICKED_STOP?.reset()
                }
                Settings.PICKED_STOP = this
                backgroundTintList = ColorStateList.valueOf(Settings.COLOR_STOP)\
                tileState = TileState.STOP
            }
            PickState.WALL -> {
                when (tileState) {
                    TileState.BLOCKED -> backgroundTintList = ColorStateList.valueOf(Color.DKGRAY)
                    TileState.OPEN -> backgroundTintList = ColorStateList.valueOf(Color.GRAY)
                    else -> {
                    }
                }
            }
        }
    }
}