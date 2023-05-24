package ETC.P11048_OnlyDP_BFSDP

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P11048_OnlyDP_BFSDP/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N, M) = br.readLine().split(" ").map { it.toInt() }
    val dp = Array(N + 1) { IntArray(M + 1) }

    for (i in 1..N) {
        dp[i] = intArrayOf(0) + br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }

    for (i in 1..N) {
        for (j in 1..M) {
            dp[i][j] += maxOf(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1])
        }
    }
    println(dp[N][M])
}