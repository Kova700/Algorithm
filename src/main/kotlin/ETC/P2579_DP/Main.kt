package ETC.P2579_DP

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.max

private var N = 0 // 계단의 개수 (300이하의 자연수)
private lateinit var stairsList: IntArray
private lateinit var dp: IntArray
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2579_DP/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    stairsList = IntArray(N + 1)
    dp = IntArray(N + 1)

    repeat(N) {
        stairsList[it + 1] = br.readLine().toInt()
    }

    if (N == 1) {
        println(stairsList[1])
        return
    }

    dp[1] = stairsList[1]
    dp[2] = stairsList[1] + stairsList[2]

    for (i in 3..N) {
        dp[i] = max((stairsList[i] + dp[i - 2]), (stairsList[i] + stairsList[i - 1] + dp[i - 3]))
    }
    println(dp[N])
}