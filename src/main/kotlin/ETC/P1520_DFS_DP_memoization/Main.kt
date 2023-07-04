package ETC.P1520_DFS_DP_memoization

import java.io.FileInputStream
import java.util.*

private var M = 0 //세로 (500이하의 자연수)
private var N = 0 //가로 (500이하의 자연수)
private lateinit var board: Array<List<Int>>
private lateinit var dp: Array<IntArray>
private lateinit var isVisited: Array<BooleanArray>
private val MY = arrayOf(1, -1, 0, 0) //상하좌우
private val MX = arrayOf(0, 0, -1, 1) //상하좌우
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1520_DFS_DP_memoization/input"))
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())
    M = st.nextToken().toInt()
    N = st.nextToken().toInt()

    dp = Array(M) { IntArray(N) }
    isVisited = Array(M) { BooleanArray(N) }
    board = Array(M) { listOf<Int>(N) }
    repeat(M) { i ->
        //각 지점의 높이는 10000이하의 자연수
        board[i] = br.readLine().split(" ").map { it.toInt() }
    }
    dfs(0, 0)

    println(dp[0][0])
}

private fun dfs(y: Int, x: Int): Int {
    if (y == M - 1 && x == N - 1) {
        dp[y][x] = 1
        return 1
    }

    for (i in 0 until 4) {
        val nextY = y + MY[i]
        val nextX = x + MX[i]
        if (isInMap(nextY, nextX) && board[nextY][nextX] < board[y][x]) {
            if (isVisited[nextY][nextX]) {
                dp[y][x] += dp[nextY][nextX]
                continue
            }
            isVisited[nextY][nextX] = true
            dp[y][x] += dfs(nextY, nextX)
        }
    }
    return dp[y][x]
}

private fun isInMap(y: Int, x: Int) =
    (y in 0 until M) && (x in 0 until N)