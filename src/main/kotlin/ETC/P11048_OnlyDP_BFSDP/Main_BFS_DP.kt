package ETC.P11048_OnlyDP_BFSDP

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

private var N = 0
private var M = 0
private lateinit var maze: Array<IntArray>
private lateinit var dp: Array<IntArray>
private val MY = intArrayOf(0, 1, 1) //우 ,대각, 하
private val MX = intArrayOf(1, 1, 0) //우 ,대각, 하
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P11048_OnlyDP_BFSDP/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    br.readLine().split(" ").map { it.toInt() }.apply {
        N = this[0]
        M = this[1]
    }
    maze = Array(N + 1) { IntArray(M + 1) }
    dp = Array(N + 1) { IntArray(M + 1) { -1 } }
    for (i in 1..N) {
        maze[i] = intArrayOf(0) + br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }
    bfs()
    println(dp[N][M])
}

private fun bfs() {
    val Q: Queue<Point> = LinkedList()
    Q.add(Point(1, 1))
    dp[1][1] = maze[1][1]
    while (Q.isNotEmpty()) {
        val point = Q.poll()
        for (i in 0 until 3) {
            val nY = point.y + MY[i]
            val nX = point.x + MX[i]
            if (isInMap(nY, nX) && (dp[nY][nX] < maze[nY][nX] + dp[point.y][point.x])) {
                dp[nY][nX] = maze[nY][nX] + dp[point.y][point.x]
                Q.add(Point(nY, nX))
            }
        }
    }
}

private fun isInMap(y: Int, x: Int) =
    (y in 1..N) && (x in 1..M)

private data class Point(
    val y: Int,
    val x: Int
)