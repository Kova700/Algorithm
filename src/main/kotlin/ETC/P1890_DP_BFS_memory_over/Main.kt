package ETC.P1890_DP_BFS_memory_over

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0 //판의 크기 N (4 ≤ N ≤ 100)
private lateinit var map: Array<IntArray>
private lateinit var dp: Array<LongArray>
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1890_DP_BFS_memory_over/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    map = Array(N) { IntArray(N) }
    dp = Array(N) { LongArray(N) }
    repeat(N) { i ->
        map[i] = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }
    dp[0][0] = 1

    for (y in 0 until N) {
        for (x in 0 until N) {
            if (map[y][x] == 0 || dp[y][x] == 0L) continue
            val nY = y + map[y][x]
            if (isInMap(nY)) {
                dp[nY][x] += dp[y][x]
            }
            val nX = x + map[y][x]
            if (isInMap(nX)) {
                dp[y][nX] += dp[y][x]
            }
        }
    }
    println(dp[N - 1][N - 1])
}

private fun isInMap(p: Int) = (p in 0 until N)