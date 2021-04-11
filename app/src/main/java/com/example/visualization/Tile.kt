package com.example.visualization


import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
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
    View(context),
    Node {

    var tileState: TileState = TileState.OPEN
        set(value) {
            field = value
        }

    // staticNeighbors contains initialized neighbors
    // Tile calculates its neighbors once in a while so we need to keep
    // track of real, initialized ones
    private val staticNeighbors = neighbors

    private fun initPant(): Paint {
        val p = Paint()
        p.color = Settings.COLOR_DEFAULT
        return p
    }

    private val paint: Paint = initPant()

    init {
        tileState = TileState.OPEN

        setOnClickListener { customListener() }
        layoutParams = ViewGroup.LayoutParams(size, size)
        x = xVal
        y = yVal
        setBackgroundColor(Color.RED)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun recalculateNeighbors() {
        neighbors?.removeIf { t -> (t as Tile).tileState == TileState.BLOCKED }
    }

    override fun toString(): String {
        return "$xIndex:$yIndex"
    }

    fun reset() {
        colorWithAnimation(Color.GRAY)
        tileState = TileState.OPEN
        neighbors = staticNeighbors
    }

    private fun customListener() {
        println("clicked: $xIndex:$yIndex")
        colorWithAnimation(Color.GREEN)

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
        val from: Int = (background as ColorDrawable).color

        val animator = ObjectAnimator.ofInt(
            this,
            "background",
            from,
            color
        )
        animator.duration = 1000L
        animator.setEvaluator(ArgbEvaluator())
        animator.interpolator = DecelerateInterpolator(2F)
        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int
            this.backgroundTintList = ColorStateList.valueOf(animatedValue)
        }
        animator.start()

    }
}