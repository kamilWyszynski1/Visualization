package com.example.visualization

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlin.math.min

class Board(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val VERTICAL_GAP = 100
        val HORIZONTAL_GAP = 200
        ///
        val HORIZONTAL_SIZE = 30
        val VERTIVAL_SIZE = 20

        val left = VERTICAL_GAP
        val top = 0
        val gap = 10
        val size = min(
            (height - 2 * HORIZONTAL_GAP - HORIZONTAL_SIZE * gap) / HORIZONTAL_SIZE,
            (width - 2 * VERTICAL_GAP - VERTIVAL_SIZE * gap) / VERTIVAL_SIZE
        )

        val paint = Paint()
        paint.color = Color.RED

        for (i in 0 until HORIZONTAL_SIZE) {
            for (j in 0 until VERTIVAL_SIZE) {
                // draw rectangle shape to canvas
                val leftUP = left + (size + gap) * j // 100 210 320
                val top2 = top + (size + gap) * i //
                val right = leftUP + size
                val bottom = top2 + size

                canvas?.drawRect(
                    leftUP.toFloat(),
                    top2.toFloat(),
                    right.toFloat(),
                    bottom.toFloat(),
                    paint
                )
            }
        }

//        addView(Tile(context, 0, 0))
    }
}

