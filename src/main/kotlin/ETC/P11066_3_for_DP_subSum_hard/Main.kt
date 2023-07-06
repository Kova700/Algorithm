package ETC.P11066_3_for_DP_subSum_hard

import java.io.FileInputStream

private var T = 0 //테스트 케이스 개수
private lateinit var dp: Array<IntArray> //dp[i][j] = i부터 j까지 합쳤을 때, 최소 비용
private lateinit var sum: IntArray //sum[i] = 1번째부터 i번째까지 비용합
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

        //{dp[0][1]}, {dp[1][2], dp[0][2]}, {dp[2][3], dp[1][3], dp[0][3]}
        // ... {dp[K-2][K-1], dp[K-3][K-1] ... dp[0][K-1]}
        for (j in 1 until K) { //~까지
            for (i in j - 1 downTo 0) { // ~부터
                dp[i][j] = Int.MAX_VALUE
                //i 부터 j 사이값 중 기준점을 잡고, 비용합이 가장 작은 값 찾기
                for (q in i until j) {
                    dp[i][j] = minOf(dp[i][j], dp[i][q] + dp[q + 1][j])
                }
                //합칠 때 발생하는 비용 (== i ~ j 범위에있는 비용 합) 추가
                if (i != 0) dp[i][j] += sum[j] - sum[i - 1]
                else dp[i][j] += sum[j]
            }
        }

        println(dp[0][K - 1])
    }
}
