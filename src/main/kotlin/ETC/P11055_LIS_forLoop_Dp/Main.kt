package ETC.P11055_LIS_forLoop_Dp

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.max

private var N = 0 //수열 A의 크기 N (1 ≤ N ≤ 1,000)
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P11055_LIS_forLoop_Dp/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    val sequence = br.readLine().split(" ").map { it.toInt() }
    val dp = IntArray(N) { sequence[it] }
    var answer = sequence[0]

    for (i in 0 until N) {
        for (j in 0 until i) {
            if (sequence[j] < sequence[i]) {
                dp[i] = max(dp[i], dp[j] + sequence[i])
            }
            answer = max(answer, dp[i])
        }
    }
    println(answer)
}