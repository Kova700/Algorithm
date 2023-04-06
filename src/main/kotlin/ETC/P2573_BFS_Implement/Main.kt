package ETC.P2573_BFS_Implement

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.Queue

private var N = 0 //3 이상 300 이하
private var M = 0 //3 이상 300 이하
private var MY = arrayOf(0, -1, 0, 1) //우하좌상
private var MX = arrayOf(1, 0, -1, 0) //우하좌상
private lateinit var graph: Array<IntArray>
private var time = 0
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2573_BFS_Implement/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    br.readLine().split(" ").map { it.toInt() }.apply {
        N = this[0]
        M = this[1]
    }
    graph = Array(N) { intArrayOf() }
    repeat(N) { i ->
        //각 칸에 들어가는 값은 0 이상 10 이하
        graph[i] = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }

    while (true) {
        meltIceberg()
        val leftOverIcebergCount = countIceberg()
        time++
        if (leftOverIcebergCount >= 2) {
            println(time)
            break
        }
        if (leftOverIcebergCount == 0) {
            println(0)
            break
        }
    }
}

private fun meltIceberg() {
    val isVisited = Array(N) { BooleanArray(M) { false } }
    for (y in 0 until N) {
        for (x in 0 until M) {
            if (graph[y][x] == 0) continue

            var icebergCount = 0
            for (k in 0 until 4) {
                val nextY = y + MY[k]
                val nextX = x + MX[k]
                if (isInMap(nextY, nextX) && (graph[nextY][nextX] == 0) && !isVisited[nextY][nextX]) {
                    icebergCount++
                }
            }
            isVisited[y][x] = true
            //빙산의 개수 만큼 빙산 녹이기
            graph[y][x] = maxOf(0, graph[y][x] - icebergCount)
        }
    }
}

//영역 개수 카운팅
private fun countIceberg(): Int {
    val Q: Queue<Point> = LinkedList()
    var leftOverIcebergCount = 0 // 여기서 관리
    val isVisited = Array(N) { BooleanArray(M) { false } }

    for (y in 0 until N) {
        for (x in 0 until M) {
            if (isVisited[y][x] || graph[y][x] == 0) continue

            leftOverIcebergCount++
            isVisited[y][x] = true
            Q.add(Point(y, x))

            while (Q.isNotEmpty()) {
                val point = Q.poll()
                for (i in 0 until 4) {
                    val nextY = point.y + MY[i]
                    val nextX = point.x + MX[i]
                    if (isInMap(nextY, nextX) &&
                        !isVisited[nextY][nextX] && (graph[nextY][nextX] != 0)
                    ) {
                        Q.add(Point(nextY, nextX))
                        isVisited[nextY][nextX] = true
                    }
                }
            }

        }
    }
    return leftOverIcebergCount
}

private fun isInMap(y: Int, x: Int) =
    (y in 0 until N) && (x in 0 until M)

private data class Point(
    val y: Int,
    val x: Int
)