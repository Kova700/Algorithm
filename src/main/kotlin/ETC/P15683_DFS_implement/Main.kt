package ETC.P15683_DFS_implement

import java.io.FileInputStream
import java.util.*

private var N = 0 //세로(1 ≤ N ≤ 8)
private var M = 0 //가로(1 ≤ M ≤ 8)
private lateinit var board: Array<IntArray>
private var answer = Int.MAX_VALUE
private val MY = arrayOf(0, -1, 0, 1) //우,하,좌,상
private val MX = arrayOf(1, 0, -1, 0) //우,하,좌,상
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P15683_DFS_implement/input"))
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()
    board = Array(N) { IntArray(M) }
    repeat(N) { i ->
        //0은 빈 칸, 6은 벽, 1~5는 CCTV 종류
        //CCTV의 최대 개수는 8개를 넘지 않는다.
        board[i] = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }
    dfs(0, 0, board)
    println(answer)
}

private fun dfs(y: Int, x: Int, tempBoard: Array<IntArray>) {
    if (y == N - 1 && x == M - 1) {
        var sum = 0
        tempBoard.forEach {
            sum += it.count { i -> i == 0 }
        }
        answer = minOf(answer, sum)
        return
    }

    var currentY = y
    var currentX = x
    //편의상 #을 9로 표시
    //find next point
    while (true) {
        if (tempBoard[currentY][currentX] != 0 &&
            tempBoard[currentY][currentX] != 9 &&
            tempBoard[currentY][currentX] != 6
        ) break

        if (currentX == M - 1) {
            currentY += 1
            currentX = 0
            if (currentY == N) {
                dfs(N - 1, M - 1, tempBoard)
                return
            }
            continue
        }
        currentX += 1
    }

    when (board[currentY][currentX]) {
        1 -> {
            for (i in 0 until 4) {
                val temp = makeCopyBoard(tempBoard)
                temp[currentY][currentX] = 9
                val nY = currentY + MY[i]
                val nX = currentX + MX[i]
                paint9(i, nY, nX, temp)
                dfs(currentY, currentX, temp)
            }
        }
        2 -> {
            for (i in 0 until 2) {
                val temp = makeCopyBoard(tempBoard)
                temp[currentY][currentX] = 9
                val nY = currentY + MY[i]
                val nX = currentX + MX[i]
                val nY2 = currentY + MY[i + 2]
                val nX2 = currentX + MX[i + 2]
                paint9(i, nY, nX, temp)
                paint9(i + 2, nY2, nX2, temp)
                dfs(currentY, currentX, temp)
            }
        }
        3 -> {
            for (i in 0 until 4) {
                val temp = makeCopyBoard(tempBoard)
                temp[currentY][currentX] = 9
                val nY = currentY + MY[i]
                val nX = currentX + MX[i]
                val nY2 = currentY + MY[(i + 1) % 4]
                val nX2 = currentX + MX[(i + 1) % 4]
                paint9(i, nY, nX, temp)
                paint9((i + 1) % 4, nY2, nX2, temp)
                dfs(currentY, currentX, temp)
            }
        }
        4 -> {
            for (i in 0 until 4) {
                val temp = makeCopyBoard(tempBoard)
                temp[currentY][currentX] = 9
                val nY = currentY + MY[i]
                val nX = currentX + MX[i]
                val nY2 = currentY + MY[(i + 1) % 4]
                val nX2 = currentX + MX[(i + 1) % 4]
                val nY3 = currentY + MY[(i + 2) % 4]
                val nX3 = currentX + MX[(i + 2) % 4]
                paint9(i, nY, nX, temp)
                paint9((i + 1) % 4, nY2, nX2, temp)
                paint9((i + 2) % 4, nY3, nX3, temp)
                dfs(currentY, currentX, temp)
            }
        }
        5 -> {
            val temp = makeCopyBoard(tempBoard)
            temp[currentY][currentX] = 9
            val nY = currentY + MY[0]
            val nX = currentX + MX[0]
            val nY2 = currentY + MY[1]
            val nX2 = currentX + MX[1]
            val nY3 = currentY + MY[2]
            val nX3 = currentX + MX[2]
            val nY4 = currentY + MY[3]
            val nX4 = currentX + MX[3]
            paint9(0, nY, nX, temp)
            paint9(1, nY2, nX2, temp)
            paint9(2, nY3, nX3, temp)
            paint9(3, nY4, nX4, temp)
            dfs(currentY, currentX, temp)
        }
    }
}

private fun paint9(direction: Int, y: Int, x: Int, tempBoard: Array<IntArray>) {
    var nextY = y
    var nextX = x
    while (isInMap(nextY, nextX)) {
        if (tempBoard[nextY][nextX] == 6) break
        if (tempBoard[nextY][nextX] == 0) {
            tempBoard[nextY][nextX] = 9
        }
        nextY += MY[direction]
        nextX += MX[direction]
    }
}

private fun makeCopyBoard(board: Array<IntArray>): Array<IntArray> {
    val temp = Array(N) { intArrayOf() }
    for (i in 0 until N) {
        temp[i] = board[i].copyOf()
    }
    return temp
}

private fun isInMap(y: Int, x: Int) =
    y in 0 until N && x in 0 until M