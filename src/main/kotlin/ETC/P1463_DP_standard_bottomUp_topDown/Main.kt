package ETC.P1463_DP_standard_bottomUp_topDown

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.min

private var N = 0
private lateinit var dp: IntArray
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1463_DP_standard_bottomUp_topDown/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    dp = IntArray(N + 1) { -1 }
    dp[1] = 0
    println(getAnswer())
}

private fun getAnswer(): Int {
//    return topDown(N)
    return bottomUp(N)
}

private fun topDown(n: Int): Int {
    if (dp[n] != -1) return dp[n]

    var min = Int.MAX_VALUE
    if (n % 3 == 0) min = min(min, topDown(n / 3))
    if (n % 2 == 0) min = min(min, topDown(n / 2))
    min = min(min, topDown(n - 1))
    dp[n] = min + 1 //+1을 왜 하는지 파악하면 이해가 쉬움
    // (이전에 헀던 연산수 보다 + 1만큼 연산(/3 , /2, -1 중 하나)을 더 했음 으로)
    return dp[n]
}

private fun bottomUp(n: Int): Int {
    for (i in 2..n) {
        dp[i] = dp[i - 1] + 1
        if (i % 2 == 0) dp[i] = min(dp[i], dp[i / 2] + 1)
        if (i % 3 == 0) dp[i] = min(dp[i], dp[i / 3] + 1)
    }
    return dp[n]
}
