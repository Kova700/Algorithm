package ETC.P2193_DP

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private lateinit var dp: LongArray
private var N = 0 //자리 수 N(1 ≤ N ≤ 90)
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2193_DP/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    dp = LongArray(N + 2) { -1 }.apply {
        this[1] = 1
        this[2] = 1
    }

//    println(bottomUp(N))
    println(topDown(N))
}

private fun bottomUp(n: Int): Long {
    for (i in 2..n) {
        dp[i] = dp[i - 1] + dp[i - 2]
    }
    return dp[n]
}

private fun topDown(n: Int): Long {
    if (dp[n] != -1L) return dp[n]
    dp[n] = topDown(n - 1) + topDown(n - 2)
    return dp[n]
}
