package com.example.visualization

import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.Math.pow
import kotlin.math.pow
import kotlin.math.sqrt

class Node(val x:Int, val y:Int) {

    var neighbors: MutableList<Node> = mutableListOf<Node>()
//    val x: Int? = 0
//    val y: Int? = 0

    override fun toString(): String = "($x:$y) with neighbors: ${neighbors.size}"
}

fun reconstructPath(cameFrom: Map<Node, Node>, current: Node): List<Node> {
    var currentCopy = current
    val totalPath = mutableListOf(current)
    while (currentCopy in cameFrom.keys) {
        currentCopy = cameFrom[currentCopy]!!
        totalPath.add(0, currentCopy)
    }
    return totalPath
}


fun d(n1:Node, n2:Node): Double = (n1.x - n2.x).toDouble().pow(2.0).plus((n1.y - n2.y).toDouble().pow(2.0)).pow(0.5)

// h is heuristic function. h(n) estimates the cost to reach goal from node n
@RequiresApi(Build.VERSION_CODES.N)
fun aStar(start: Node, goal: Node, h: (n1: Node) -> Double): List<Node> {
    // The set of discovered nodes that may need to be (re-)expanded.
    // Initially, only the start node is known.
    // This is usually implemented as a min-heap or priority queue rather than a hash-set.
    val openSet =  mutableSetOf<Node>(start)

    // For node n, cameFrom[n] is the node immediately preceding it on the cheapest path from start
    // to n currently known.
    val cameFrom = mutableMapOf<Node, Node>()

    // For node n, gScore[n] is the cost of the cheapest path from start to n currently known.
    val gScore = mutableMapOf<Node, Int>(start to 0)

    val fScore = mutableMapOf<Node, Double>(start to h(start))

    while (openSet.isNotEmpty()) {
        val current = openSet.minWith(Comparator.comparingDouble { h(start) })

        if (current == goal) {
            return reconstructPath(cameFrom, current)
        }
        openSet.remove(current)

        current?.neighbors?.forEach { neighbor ->
            run {
                val tenatativeGScore = gScore[current]?.plus(d(current, neighbor))
                if (tenatativeGScore != null) {
                    if (tenatativeGScore < gScore[neighbor] ?: error("gScore of it is null")) {
                        cameFrom[neighbor] = current
//                        gScore[neighbor] = tenatativeGScore
                        val score =  gScore[neighbor]?.plus(h(neighbor))
                        if (score != null) {
                            fScore[neighbor] = score
                        }
                        if (neighbor !in openSet) {
                            openSet.add(neighbor)
                        }
                    }
                }
            }
        }
    }
    return emptyList()
}