package ETC.P2293_coin_sum_DP

import java.io.FileInputStream
import java.util.*

private var N = 0 //동전의 종류 (1 ≤ n ≤ 100)
private var K = 0 //가치의 합 (1 ≤ k ≤ 10,000)
private lateinit var dp: IntArray
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2293_coin_sum_DP/input"))
    val br = System.`in`.bufferedReader()
    val st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    K = st.nextToken().toInt()

    //dp[i] = i원을 만들 수 있는 경우의 수
    //가치가 1인 동전으로 i원을 만드는 경우의 수 == dp[i-1]
    //가치가 2인 동전으로 i원을 만드는 경우의 수 == dp[i-2]
    // ...
    //가치가 k인 동전으로 i원을 만드는 경우의 수 == dp[i-k]
    //dp[i] =  위 경우의 수들의 총합
    //dp[i] += dp[i-k] (1 ≤ k ≤ 10,000)
    dp = IntArray(K + 1)

    //동전의 가치 (1 <= 가치 <= 100,000)
    val coins = Array(N) { br.readLine().toInt() }.sortedArray()

    //가장 작은 동전부터 j원을 만드는 경우의 수를 구하고
    //다음 동전의 경우의 수를 점점 쌓는 방식
    dp[0] = 1
    for (i in coins.indices) {
        for (j in coins[i]..K) {
            dp[j] += dp[j - coins[i]]
        }
    }
    println(dp[K])
}