package ETC.P17070_DP_DFS

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0 //집의 크기 (3 ≤ N ≤ 16)
private lateinit var board: Array<IntArray>
private lateinit var dp: Array<Array<IntArray>>
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P17070_DP_DFS/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    board = Array(N + 1) { IntArray(N + 1) }
    dp = Array(N + 1) { Array(N + 1) { IntArray(3) } }

    repeat(N) {
        //집의 상태가 주어진다. 빈 칸은 0, 벽은 1로 주어진다. (1, 1)과 (1, 2)는 항상 빈 칸이다.
        board[it + 1] = intArrayOf(0) + br.readLine().split(" ").map { i -> i.toInt() }.toIntArray()
    }

    //파이프를 놓을 수 있는 방법 3가지(가로, 대각선, 세로)
    //각각의 경우의 수를 저장할 수 있는 3차원배열 dp
    //dp[i][j][k] == i행, j열에 파이프가 k(가로 혹은 대각 혹은 세로)상태로 있는 경우의 수
    //가로 방향(0) : 이전 파이프를 가로 ,대각선으로 놓는 경우
    //      dp[i][j][0] = dp[i][j-1][0] + dp[i][j-1][1]
    //대각선 방향(1) : 이전 파이프를 가로 ,세로 ,대각선으로 놓는 경우
    //      dp[i][j][1] = dp[i-1][j-1][0] + dp[i-1][j-1][1] + dp[i-1][j-1][2])
    //세로 방향(2) : 이전 파이프를 세로 ,대각선으로 놓는 경우
    //      dp[i][j][2] = dp[i-1][j][1] + dp[i-1][j][2]

    dp[1][2][0] = 1
    for (i in 1..N) {
        for (j in 2..N) {
            if (board[i][j] == 1) continue

            dp[i][j][0] += dp[i][j - 1][0] + dp[i][j - 1][1]
            dp[i][j][2] += dp[i - 1][j][1] + dp[i - 1][j][2]

            if (board[i - 1][j] == 1 || board[i][j - 1] == 1) continue
            dp[i][j][1] += dp[i - 1][j - 1][0] + dp[i - 1][j - 1][1] + dp[i - 1][j - 1][2]
        }
    }

    println(dp[N][N].sum())
}