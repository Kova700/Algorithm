package ETC.P11052_DP_maxOf_stack

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0 //카드의 개수 (1 ≤ N ≤ 1,000)
private lateinit var cards: IntArray
private lateinit var dp: IntArray
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P11052_DP_maxOf_stack/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    dp = IntArray(N + 1)
    cards = intArrayOf(0) + br.readLine().split(" ").map { it.toInt() }.toIntArray()

    dp[1] = cards[1]
    for (i in 2..N) {
        for (j in 1 until i) {
            dp[i] = maxOf(cards[i], dp[i - j] + dp[j], dp[i])
        }
    }
    println(dp[N])
}