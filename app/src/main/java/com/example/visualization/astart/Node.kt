package com.example.visualization.astart

interface Node {
    val xIndex: Int
    val yIndex: Int

    var neighbors: MutableList<Node>?

    fun recalculateNeighbors()

    fun describe(): String = "($xIndex:$yIndex) with ${neighbors?.size} neighbors"

    fun equals(n: Node): Boolean = xIndex == n.xIndex && yIndex == n.yIndex
}