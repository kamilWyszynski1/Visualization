package com.example.visualization.astart

import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.math.pow

class AStarNode(override val xIndex: Int, override val yIndex: Int) : Node {

    override var neighbors: MutableList<Node>? = mutableListOf()

    // recalculateNeighbors should do nothing
    override fun recalculateNeighbors() {}

    override fun toString(): String = "($xIndex:$yIndex) with ${neighbors?.size} neigbors"
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


fun euclidean(n1: Node, n2: Node): Double =
    (n1.xIndex - n2.xIndex).toDouble().pow(2.0).plus((n1.yIndex - n2.yIndex).toDouble().pow(2.0))
        .pow(0.5)

fun manhattan(n1: Node, n2: Node): Double =
    (kotlin.math.abs(n1.xIndex - n2.xIndex) + kotlin.math.abs(n1.yIndex - n2.yIndex)).toDouble()


// h is heuristic function. h(n) estimates the cost to reach goal from node n
@RequiresApi(Build.VERSION_CODES.N)
fun aStar(
    start: Node,
    goal: Node,
    h: (Node) -> Double,
    d: (Node, Node) -> Double
): List<Node> {

    val INFINITY = 100000.0
    // The set of discovered nodes that may need to be (re-)expanded.
    // Initially, only the start node is known.
    // This is usually implemented as a min-heap or priority queue rather than a hash-set.
    val openSet = mutableSetOf<Node>(start)

    // For node n, cameFrom[n] is the node immediately preceding it on the cheapest path from start
    // to n currently known.
    val cameFrom = mutableMapOf<Node, Node>()

    // For node n, gScore[n] is the cost of the cheapest path from start to n currently known.
    val gScore = mutableMapOf(start to 0.0)

    val fScore = mutableMapOf(start to h(start))

    while (openSet.isNotEmpty()) {
        val current = openSet.minWith(Comparator.comparingDouble { h(start) })

//        println("\u001B[31m current: $current \u001B[0m")
        if (current == goal) {
            return reconstructPath(
                cameFrom,
                current
            )
        }
        openSet.remove(current)

        current?.neighbors?.forEach { neighbor ->
            run {
                val tenatativeGScore =
                    gScore.getOrDefault(current, INFINITY).plus(d(current, neighbor))
//                println("$neighbor with tenatativeScore $tenatativeGScore vs ${gScore.getOrDefault(neighbor, INFINITY)} d: ${h(current)}")
                if (tenatativeGScore < gScore.getOrDefault(neighbor, INFINITY)) {
                    cameFrom[neighbor] = current
//                        gScore[neighbor] = tenatativeGScore
                    gScore[neighbor] = tenatativeGScore
                    fScore[neighbor] = gScore.getOrDefault(neighbor, INFINITY).plus(h(neighbor))

                    if (neighbor !in openSet) {
                        openSet.add(neighbor)
                    }
                }
            }
        }
    }
    return emptyList()
}

@RequiresApi(Build.VERSION_CODES.N)
fun defaultAStart(
    start: Node,
    goal: Node
): List<Node> {
    return aStar(
        start,
        goal,
        h = fun(n1: Node): Double = manhattan(n1, goal),
        d = ::manhattan
    )
}