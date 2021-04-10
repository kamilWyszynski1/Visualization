package com.example.visualization

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import kotlin.math.min

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_first, container, false)
        val cl = root.findViewById<ConstraintLayout>(R.id.cl)
        val bt = Button(context)
        bt.text = "elo"
        cl.addView(bt)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)

        println(
            "${Resources.getSystem().displayMetrics.heightPixels}, ${Resources.getSystem().displayMetrics.widthPixels}"
        )

        val height = Resources.getSystem().displayMetrics.heightPixels
        val width = Resources.getSystem().displayMetrics.widthPixels

        val VERTICAL_GAP = 100
        val HORIZONTAL_GAP = 200
        ///
        val HORIZONTAL_SIZE = 10
        val VERTIVAL_SIZE = 10

        val bitmap: Bitmap = Bitmap.createBitmap(700, 1000, Bitmap.Config.ARGB_8888)
        val canvas: Canvas = Canvas(bitmap)

        var shapeDrawable: ShapeDrawable

        val a = (height - 2 * HORIZONTAL_GAP) / HORIZONTAL_GAP
        // rectangle positions
        val left = VERTICAL_GAP
        val top = 0
        val gap = 10
        val size = min(
            (height - 2 * HORIZONTAL_GAP - HORIZONTAL_SIZE * gap) / HORIZONTAL_SIZE,
            (width - 2 * VERTICAL_GAP - VERTIVAL_SIZE * gap) / VERTIVAL_SIZE
        )

//        shapeDrawable = ShapeDrawable(RectShape())
//
//        shapeDrawable.setBounds(
//            50,
//            100,
//            110,
//            110
//        )
//        shapeDrawable.getPaint().setColor(Color.parseColor("#009944"))
//        shapeDrawable.draw(canvas)

        for (i in 0 until HORIZONTAL_SIZE) {
            for (j in 0 until VERTIVAL_SIZE) {
                // draw rectangle shape to canvas
                shapeDrawable = ShapeDrawable(RectShape())
                val leftUP = left + (size + gap) * j // 100 210 320
                val top2 = top + (size + gap) * i //
                val right = leftUP + size
                val bottom = top2 + size
                shapeDrawable.setBounds(
                    leftUP,
                    top2,
                    right,
                    bottom
                )
                shapeDrawable.getPaint().setColor(Color.parseColor("#009944"))
                shapeDrawable.draw(canvas)
            }
        }

        val iView: ImageView = view.findViewById<ImageView>(R.id.imageV)
        println("${iView.resources.displayMetrics.heightPixels}, ${iView.resources.displayMetrics.widthPixels}")
        iView.setImageBitmap(bitmap)
    }
}