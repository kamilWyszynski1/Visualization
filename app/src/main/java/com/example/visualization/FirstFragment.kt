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
import com.example.visualization.const.FULL_SCREEN
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
    private val HORIZONTAL_SIZE = FULL_SCREEN
    private val VERTIVAL_SIZE = FULL_SCREEN
    private val SIZE = 80
    private val GAP = 10

    private var fragmentWidth: Int = 0
    private var fragmentHeight: Int = 0

    private var cLayout: ConstraintLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_first, container, false)
        val cl = root.findViewById<ConstraintLayout>(R.id.cl)

        root.post {
            fragmentHeight = root.measuredHeight
            fragmentWidth = root.measuredWidth

            generateGrid(cl)
        }

        cLayout = cl
        return root
    }

    private fun generateGrid(cl: ConstraintLayout) {
        // INITIALIZE TILES
        val tiles = mutableListOf<List<Tile>>()

        if (HORIZONTAL_GAP == FULL_SCREEN || VERTIVAL_SIZE == FULL_SCREEN) {
            val horizontal_amount = (fragmentHeight - 2 * HORIZONTAL_GAP) / (SIZE + GAP)
            val vertical_amount = (fragmentWidth - 2 * VERTICAL_GAP) / (SIZE + GAP)

            for (i in 0 until horizontal_amount) {
                val row = mutableListOf<Tile>()
                for (j in 0 until vertical_amount) {
                    // draw rectangle shape to canvas
                    val leftUP = HORIZONTAL_GAP + (SIZE + GAP) * j  // 100 210 320
                    val top2 = VERTICAL_GAP + (SIZE + GAP) * i
                    val right = leftUP + SIZE
                    val bottom = top2 + SIZE

                    val bt = Tile(
                        requireContext(),
                        leftUP.toFloat(),
                        top2.toFloat(),
                        SIZE,
                        i,
                        j,
                        mutableListOf()
                    )
                    row.add(bt)
                    cl.addView(bt)
                }
                tiles.add(row)
            }
        } else {
            val left = VERTICAL_GAP
            val top = HORIZONTAL_GAP
            val size = min(
                (fragmentHeight - 2 * HORIZONTAL_GAP - HORIZONTAL_SIZE * GAP) / HORIZONTAL_SIZE,
                (fragmentWidth - 2 * VERTICAL_GAP - VERTIVAL_SIZE * GAP) / VERTIVAL_SIZE
            )

            println("size: $size")

            val paint = Paint()
            paint.color = Color.RED

            for (i in 0 until HORIZONTAL_SIZE) {
                val row = mutableListOf<Tile>()
                for (j in 0 until VERTIVAL_SIZE) {
                    // draw rectangle shape to canvas
                    val leftUP = left + (size + GAP) * j  // 100 210 320
                    val top2 = top + (size + GAP) * i
                    val right = leftUP + size
                    val bottom = top2 + size

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
        }
        Settings.TILES = tiles
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)


    }
}