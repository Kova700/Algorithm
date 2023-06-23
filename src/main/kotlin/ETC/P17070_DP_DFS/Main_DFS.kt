package ETC.P17070_DP_DFS

import java.io.FileInputStream

private var N = 0 //집의 크기 (3 ≤ N ≤ 16)
private lateinit var board: Array<IntArray>
private lateinit var dp: Array<IntArray>
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P17070_DP_DFS/input"))
    val br = System.`in`.bufferedReader()
    N = br.readLine().toInt()
    board = Array(N + 1) { IntArray(N + 1) }
    dp = Array(N + 1) { IntArray(N + 1) }
    repeat(N) {
        //집의 상태가 주어진다. 빈 칸은 0, 벽은 1로 주어진다. (1, 1)과 (1, 2)는 항상 빈 칸이다.
        board[it + 1] = intArrayOf(0) + br.readLine().split(" ").map { i -> i.toInt() }.toIntArray()
    }
    dfs(1, 2, 1)

    println(dp[N][N])
}

//type (1 == 가로, 2 == 세로, 3 == 대각)
private fun dfs(y: Int, x: Int, type: Int) {
    if (!isInMap(y, x) || board[y][x] == 1) return
    if (type == 3 && (board[y][x] == 1 || board[y - 1][x] == 1 || board[y][x - 1] == 1)) return

    dp[y][x] += 1

    if (y == N && x == N) {
        return
    }
    when (type) {
        1 -> {
            dfs(y, x + 1, 1)
            dfs(y + 1, x + 1, 3)
        }
        2 -> {
            dfs(y + 1, x, 2)
            dfs(y + 1, x + 1, 3)
        }
        3 -> {
            dfs(y, x + 1, 1)
            dfs(y + 1, x, 2)
            dfs(y + 1, x + 1, 3)
        }
    }
}

private fun isInMap(y: Int, x: Int) =
    (y in 1..N) && (x in 1..N)