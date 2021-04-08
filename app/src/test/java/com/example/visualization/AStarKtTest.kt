package com.example.visualization

import org.junit.Test


class AStarKtTest {

    @Test
    fun test_AStar() {
        val nodes: Array<Array<Node>> = Array(10) {row ->
            Array(10) {col -> Node(row, col)
            }
        }

        val M = 10
        val N = 10
        for (r in 0 until M) {
            for (c in 0 until N) {
                val n = nodes[r][c]
                val neighbors: MutableList<Node> = n.neighbors
                if (r > 0) {     // has north
                    neighbors.add(nodes[r - 1][c])
                }
                if (r < M - 1) { // has south
                    neighbors.add(nodes[r + 1][c])
                }
                if (c > 0) {     // has west
                    neighbors.add(nodes[r][c - 1])
                }
                if (c < N - 1) { // has east
                    neighbors.add(nodes[r][c + 1])
                }
            }
        }

//        nodes.forEach { row -> row.forEach { ele -> println(ele) } }
        aStar(nodes[0][0], nodes[9][9], h = fun(n1: Node): Double = d(n1, nodes[9][9]))
    }
}