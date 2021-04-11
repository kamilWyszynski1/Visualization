package com.example.visualization


import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.example.visualization.astart.Node
import com.example.visualization.settings.Settings
import com.example.visualization.states.PickState
import com.example.visualization.states.TileState


class Tile(
    context: Context,
    xVal: Float,
    yVal: Float,
    size: Int,
    override val xIndex: Int,
    override val yIndex: Int,
    override var neighbors: MutableList<Node>?
) :
    androidx.appcompat.widget.AppCompatButton(context),
    Node {

    var tileState: TileState = TileState.OPEN
        set(value) {
            field = value
        }

    // staticNeighbors contains initialized neighbors
    // Tile calculates its neighbors once in a while so we need to keep
    // track of real, initialized ones
    val staticNeighbors = neighbors

    @RequiresApi(Build.VERSION_CODES.N)
    override fun recalculateNeighbors() {
        neighbors?.removeIf { t -> (t as Tile).tileState == TileState.BLOCKED }
    }

    override fun toString(): String {
        return "$xIndex:$yIndex"
    }

    init {
        x = xVal
        y = yVal

        tileState = TileState.OPEN
        layoutParams = LinearLayout.LayoutParams(size, LinearLayout.LayoutParams.WRAP_CONTENT)
        setPadding(0, 0, 0, 0)
        colorWithAnimation(Color.GRAY)

        setOnClickListener { customListener() }
    }

    fun reset() {
        colorWithAnimation(Color.GRAY)
        tileState = TileState.OPEN
        neighbors = staticNeighbors
    }

    private fun customListener() {
        when (Settings.PICK_STATE) {
            PickState.START -> {
                if (Settings.PICKED_START != null) {
                    Settings.PICKED_START?.reset()
                }
                Settings.PICKED_START = this
                colorWithAnimation(Settings.COLOR_START)
                tileState = TileState.START
            }
            PickState.STOP -> {
                if (Settings.PICKED_STOP != null) {
                    Settings.PICKED_STOP?.reset()
                }
                Settings.PICKED_STOP = this
                colorWithAnimation(Settings.COLOR_STOP)
                tileState = TileState.STOP
            }
            PickState.WALL -> {
                val color = if (tileState == TileState.BLOCKED) Color.GRAY else Color.DKGRAY
                tileState =
                    if (tileState == TileState.BLOCKED) TileState.OPEN else TileState.BLOCKED

                colorWithAnimation(color)
            }
        }
    }

    @SuppressLint("ObjectAnimatorBinding")
    fun colorWithAnimation(color: Int) {
        val from: Int = backgroundTintList?.defaultColor.hashCode()

        val animator = ObjectAnimator.ofInt(
            this,
            "backgroundTint",
            from,
            color
        )
        animator.duration = 2000L
        animator.setEvaluator(ArgbEvaluator())
        animator.interpolator = DecelerateInterpolator(2F)
        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int
            this.backgroundTintList = ColorStateList.valueOf(animatedValue)
        }
        animator.start()

    }
}