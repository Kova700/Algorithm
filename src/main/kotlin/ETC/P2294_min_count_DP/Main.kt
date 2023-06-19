package ETC.P2294_min_count_DP

import java.io.FileInputStream
import java.util.*

private var N = 0
private var K = 0
private lateinit var dp: IntArray

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2294_min_count_DP/input"))
    val br = System.`in`.bufferedReader()
    val st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    K = st.nextToken().toInt()
    //dp[i] = 합쳐서 i원이 되는 최소 동전 수
    dp = IntArray(K + 1) { Int.MAX_VALUE }

    val coins = Array(N) { br.readLine().toInt() }.toSet().sorted()

    //첫번째 동전부터 확인
    //현재 동전만 합쳐서 j원이 되는 경우 최소 동전수 = j / coins[i]
    //현재 동전만으로는 j원이 되지 않는 경우 = (j-현재 원)의 최소 동전수 + 현재 원의 최소 동전수
    // => dp[j - coins[i]] + dp[coins[i]]
    for (i in coins.indices) {
        for (j in coins[i]..K) {
            if ((j % coins[i]) == 0) {
                dp[j] = minOf(dp[j], j / coins[i])
                continue
            }
            if (dp[j - coins[i]] == Int.MAX_VALUE || dp[coins[i]] == Int.MAX_VALUE) continue
            dp[j] = minOf(dp[j], dp[j - coins[i]] + dp[coins[i]])
        }
    }

    val answer = if (dp[K] == Int.MAX_VALUE) -1 else dp[K]
    println(answer)
}