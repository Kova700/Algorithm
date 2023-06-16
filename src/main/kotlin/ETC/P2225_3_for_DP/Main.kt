package ETC.P2225_3_for_DP

import java.io.FileInputStream
import java.util.*

private var N = 0 //숫자 범위(0 ~ N), 목표 숫자(1 ≤ N ≤ 200)
private var K = 0 //숫자 개수(1 ≤ K ≤ 200)
private lateinit var dp: Array<LongArray>
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2225_3_for_DP/input"))
    val br = System.`in`.bufferedReader()
    val st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    K = st.nextToken().toInt()
    dp = Array(K + 1) { LongArray(N + 1) }

    //dp[i][j] = 숫자의 개수가 i개 일때, 합이 j가 되는 경우의 수
    dp[1] = LongArray(N + 1) { 1 }
    for (i in 1..K) {
        for (j in N downTo 0) {
            for (k in 0..j) {
                dp[i][j] += dp[i - 1][j - k]
            }
            dp[i][j] %= 1_000_000_000L
        }
    }
    println(dp[K][N])
}