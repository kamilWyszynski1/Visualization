package com.example.visualization

import org.junit.Test
import java.lang.Exception


class AStarKtTest {

    @Test
    fun test_AStar() {
        val SIZE = 3
        val nodes = List(SIZE) {row ->
            List(SIZE) {col -> Node(row, col)
            }
        }
//        nodes[0][0].neighbors.addAll(listOf(nodes[0][1], nodes[1][0]))
//        nodes[1][0].neighbors.addAll(listOf(nodes[1][1], nodes[2][0]))
//        nodes[0][1].neighbors.addAll(listOf(nodes[1][1], nodes[0][2]))
//        nodes[1][1].neighbors.addAll(listOf(nodes[2][1], nodes[1][2]))
//
//        nodes[0][2].neighbors.addAll(listOf(nodes[1][2]))
//        nodes[2][0].neighbors.addAll(listOf(nodes[2][1]))
//
//        nodes[2][1].neighbors.addAll(listOf(nodes[2][2]))
//        nodes[1][2].neighbors.addAll(listOf(nodes[2][2]))

        val indicies = arrayOf(
            arrayOf(1,1),
            arrayOf(1,0),
            arrayOf(1,-1),
            arrayOf(-1,1),
            arrayOf(-1,0),
            arrayOf(-1,-1),
            arrayOf(0,-1),
            arrayOf(0,1)
        )

        for (r in 0 until SIZE) {
            for (c in 0 until SIZE) {
                indicies.forEach { pair ->
                    run {
                        try {
                            nodes[r][c].neighbors.add(nodes.getOrNull(r + pair[0])?.getOrNull(c + pair[1])!!)
                        } catch (e: Exception) {
                            null
                        }
                    }
                }

            }
        }

        println(nodes)

//        nodes.forEach { row -> row.forEach { ele -> println(ele) } }
        val path = aStar(nodes[0][0], nodes[SIZE-1][SIZE-1], h = fun(n1: Node): Double = manhattan(n1, nodes[SIZE-1][SIZE-1]), d= ::manhattan)
        println(path)
    }
}