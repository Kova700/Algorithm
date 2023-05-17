package ETC.P11727_DP

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0 //(1 ≤ n ≤ 1,000)
private lateinit var dp: IntArray
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P11727_DP/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    dp = IntArray(N + 2)

    dp[1] = 1
    dp[2] = 3
    println(getAnswer(N))
}

private fun getAnswer(n: Int): Int {
    for (i in 3..n) {
        if (i % 2 != 0) {
            dp[i] = (dp[i - 1] * 2 - 1) % 10_007
            continue
        }
        dp[i] = (dp[i - 1] * 2 + 1) % 10_007
    }
    return dp[n]
}