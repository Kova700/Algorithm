package ETC.P1149_DP

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.min

private var N = 0 //집의 수(2 ≤ N ≤ 1,000)
private lateinit var colors: Array<IntArray>
private lateinit var dp: Array<IntArray>
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1149_DP/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    colors = Array(N) { IntArray(3) }
    dp = Array(N) { IntArray(3) { Int.MAX_VALUE } }

    repeat(N) { i ->
        colors[i] = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }

    dp[0][0] = colors[0][0]
    dp[0][1] = colors[0][1]
    dp[0][2] = colors[0][2]

    for (i in 1 until N) {
        for (j in 0 until 3) {
            for (k in 0 until 3) {
                if (j == k) continue
                dp[i][j] = min(dp[i][j], dp[i - 1][k] + colors[i][j])
            }
        }
    }
    println(dp[N-1].minOf { it })
}