package ETC.P9461_DP

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var T = 0
private val dp = LongArray(100 + 1)
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P9461_DP/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    T = br.readLine().toInt()
    dp[1] = 1
    dp[2] = 1
    dp[3] = 1
    repeat(T) {
        println(getAnswer(br.readLine().toInt()))
    }
}

private fun getAnswer(n: Int): Long {
    for (i in 4..n) {
        dp[i] = dp[i - 2] + dp[i - 3]
    }
    return dp[n]
}