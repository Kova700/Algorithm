package ETC.P11057_DP

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0 //수의 길이 N (1 ≤ N ≤ 1,000)
private lateinit var dp: Array<IntArray>
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P11057_DP/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    dp = Array(N + 1) { IntArray(10) }
    dp[1] = IntArray(10) { 1 }

    for (i in 2..N) {
        for (j in 0 until 10) {
            for (k in j until 10) {
                dp[i][j] += dp[i - 1][k] % 10_007
            }
        }
    }
    //dp[i][j] = i길이 일때, 맨 앞 수가 j인 경우
    //dp[i][j] = dp[i - 1][k]의 합 (k >= j)
    println(dp.last().sum() % 10_007)
}