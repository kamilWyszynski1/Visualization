package com.example.visualization

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.visualization.astart.defaultAStart
import com.example.visualization.settings.Settings
import com.example.visualization.states.PickState
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            if (Settings.PICKED_START == null || Settings.PICKED_STOP == null) {
                Snackbar.make(view, "Start and Stop must be chosen", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            } else {

                Settings.TILES?.forEach { it -> it.forEach { it.recalculateNeighbors() } }

                val result = defaultAStart(Settings.PICKED_START!!, Settings.PICKED_STOP!!)
                println("result: $result")
                result.drop(1).dropLast(1).forEach {
                    Settings.TILES!![it.xIndex][it.yIndex].colorWithAnimation(Color.MAGENTA)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_start -> Settings.PICK_STATE = PickState.START
            R.id.action_stop -> Settings.PICK_STATE = PickState.STOP
            R.id.action_wall -> Settings.PICK_STATE = PickState.WALL
            R.id.action_reset -> resetTiles()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    // resetTiles invoke reset method on every Tile
    fun resetTiles() {
        Settings.TILES?.forEach { it -> it.forEach { it.reset() } }
    }
}