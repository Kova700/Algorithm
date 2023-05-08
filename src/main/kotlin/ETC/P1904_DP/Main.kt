package ETC.P1904_DP

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0 //수의 길이 (1 ≤ N ≤ 1,000,000)
private lateinit var dp: IntArray
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1904_DP/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    dp = IntArray(N + 2) { -1 }
    dp[1] = 1; dp[2] = 2

    println(topDown(N))
//    println(bottomUp(N))
}

private fun topDown(n: Int): Int {
    if (dp[n] != -1) return dp[n]

    dp[n] = (topDown(n - 1) + topDown(n - 2)) % 15746
    return dp[n]
}

private fun bottomUp(n: Int): Int {
    for (i in 3..n) {
        dp[i] = (dp[i - 2] + dp[i - 1]) % 15746
    }
    return dp[n]
}