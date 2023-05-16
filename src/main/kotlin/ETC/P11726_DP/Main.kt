package ETC.P11726_DP

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0 //(1 ≤ n ≤ 1,000)
private lateinit var dp: IntArray
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P11726_DP/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    dp = IntArray(N + 1)

    if (N == 1) {
        println(1)
        return
    }

    dp[1] = 1
    dp[2] = 2
    for (i in 3..N) {
        dp[i] = (dp[i - 1] + dp[i - 2]) % 10_007
    }
    println(dp[N])
}