package ETC.P14502_DFS_BFS

import java.io.FileInputStream
import java.util.*

private var N = 0 //가로 (3 ≤ N ≤ 8)
private var M = 0 //세로 (3 ≤ M ≤ 8)
private val MY = arrayOf(1, -1, 0, 0) //상하좌우
private val MX = arrayOf(0, 0, -1, 1) //상하좌우
private lateinit var board: Array<IntArray>
private val virusPosition = mutableListOf<Point>()
private var answer = Int.MIN_VALUE
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P14502_DFS_BFS/input"))
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()

    board = Array(N) { IntArray(M) }
    repeat(N) { y ->
        st = StringTokenizer(br.readLine())
        for (x in 0 until M) {
            board[y][x] = st.nextToken().toInt()
            if (board[y][x] == 2) virusPosition.add(Point(y, x))
        }
        //2의 개수는 2보다 크거나 같고, 10보다 작거나 같은 자연수이다.
        //빈 칸의 개수는 3개 이상이다.
    }
    makeWallDFS(0)
    println(answer)
}

private fun makeWallDFS(count: Int) {
    if (count == 3) {
        spreadVirusBFS()
        return
    }

    for (i in 0 until N) {
        for (j in 0 until M) {
            if (board[i][j] != 0) continue
            board[i][j] = 1
            makeWallDFS(count + 1)
            board[i][j] = 0
        }
    }
}

private fun spreadVirusBFS() {
    val tempBoard = Array(N) { IntArray(M) }
    for (i in 0 until N) {
        tempBoard[i] = board[i].copyOf()
    }
    val Q: Queue<Point> = LinkedList()
    for (virusPoint in virusPosition) Q.add(virusPoint)

    while (Q.isNotEmpty()) {
        val current = Q.poll()
        for (i in 0 until 4) {
            val nextY = current.y + MY[i]
            val nextX = current.x + MX[i]
            if (!isInMap(nextY, nextX) || tempBoard[nextY][nextX] != 0) continue
            tempBoard[nextY][nextX] = 2
            Q.add(Point(nextY, nextX))
        }
    }
    countSafeArea(tempBoard)
}

private fun countSafeArea(tempBoard: Array<IntArray>) {
    var sum = 0
    for (i in 0 until N) {
        for (j in 0 until M) {
            if (tempBoard[i][j] == 0) sum++
        }
    }
    answer = maxOf(answer, sum)
}

private fun isInMap(y: Int, x: Int) =
    (y in 0 until N) && (x in 0 until M)

private data class Point(
    val y: Int,
    val x: Int
)