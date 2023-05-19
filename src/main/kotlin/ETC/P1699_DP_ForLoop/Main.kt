package ETC.P1699_DP_ForLoop

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

private var N = 0 //(1 ≤ N ≤ 100,000)
private lateinit var dp: IntArray
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1699_DP_ForLoop/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    dp = IntArray(N + 1) { Int.MAX_VALUE }

    for (i in 1..N) {
        if (sqrt(i.toFloat()).isInt()) {
            dp[i] = 1
            continue
        }
        val maxSqrtNum = sqrt(i.toFloat()).toInt()
        for (sn in maxSqrtNum downTo 1) {
            dp[i] = min(dp[i], dp[sn.toDouble().pow(2.0).toInt()] + dp[i - sn.toDouble().pow(2.0).toInt()])
        }
    }
    println(dp[N])
}

private fun Float.isInt() = (this % 1) == 0.0F