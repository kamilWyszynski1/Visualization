package com.example.visualization

import android.content.res.Resources
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.visualization.const.INDICES
import com.example.visualization.settings.Settings
import java.lang.Exception
import kotlin.math.min

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private val VERTICAL_GAP = 10
    private val HORIZONTAL_GAP = 10
    private val HORIZONTAL_SIZE = 17
    private val VERTIVAL_SIZE = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_first, container, false)
        val cl = root.findViewById<ConstraintLayout>(R.id.cl)
        generateGrid(cl)

        return root
    }

    private fun generateGrid(cl: ConstraintLayout) {
        val height = Resources.getSystem().displayMetrics.heightPixels
        val width = Resources.getSystem().displayMetrics.widthPixels

        val left = VERTICAL_GAP
        val top = 0
        val gap = 0
        val size = min(
            (height - 2 * HORIZONTAL_GAP - HORIZONTAL_SIZE * gap) / HORIZONTAL_SIZE,
            (width - 2 * VERTICAL_GAP - VERTIVAL_SIZE * gap) / VERTIVAL_SIZE
        )

        println("size: $size")

        val paint = Paint()
        paint.color = Color.RED

        // INITIALIZE TILES
        val tiles = mutableListOf<List<Tile>>()
        for (i in 0 until HORIZONTAL_SIZE) {
            val row = mutableListOf<Tile>()
            for (j in 0 until VERTIVAL_SIZE) {
                // draw rectangle shape to canvas
                val leftUP = left + (size) * j + gap // 100 210 320
                val top2 = top + (size) * i + gap //

                val bt = Tile(
                    requireContext(),
                    leftUP.toFloat(),
                    top2.toFloat(),
                    size,
                    i,
                    j,
                    mutableListOf()
                )
                row.add(bt)
                cl.addView(bt)
            }
            tiles.add(row)
        }

        // INITIALIZE TILES' NEIGHBORS
        for (i in 0 until HORIZONTAL_SIZE) {
            for (j in 0 until VERTIVAL_SIZE) {
                INDICES.forEach { pair ->
                    run {
                        try {
                            tiles[i][j].neighbors?.add(
                                tiles.getOrNull(i + pair[0])?.getOrNull(j + pair[1])!!
                            )
                        } catch (e: Exception) {
                            null
                        }
                    }
                }
            }
        }

        Settings.TILES = tiles
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)
    }
}