package ETC.P11066_3_for_DP_subSum_hard

import java.io.FileInputStream

private var T = 0 //테스트 케이스 개수
private lateinit var dp: Array<IntArray> //dp[i][j] = i부터 j까지 합쳤을 때, 최소 비용
private lateinit var sum: IntArray //sum[i] = 1번째부터 i번 째까지 비용합
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P11066_3_for_DP_subSum_hard/input"))
    val br = System.`in`.bufferedReader()
    T = br.readLine().toInt()

    repeat(T) {
        val K = br.readLine().toInt() //(3 ≤ K ≤ 500)
        //(0 < size[i] < 10,000)
        val price = br.readLine().split(" ").map { it.toInt() }

        dp = Array(K) { IntArray(K) }
        sum = IntArray(K).apply { this[0] = price[0] }

        for (i in 1 until K) {
            sum[i] = sum[i - 1] + price[i]
        }

        for (j in 1 until K) {
            for (i in j - 1 downTo 0) {
                dp[i][j] = Int.MAX_VALUE
                for (q in i until j) {
                    dp[i][j] = minOf(dp[i][j], dp[i][q] + dp[q + 1][j])
                }
                if (i != 0) dp[i][j] += sum[j] - sum[i - 1]
                else dp[i][j] += sum[j]
            }
        }

        println(dp[0][K - 1])
    }
}
