package ETC.P11053_forLoop_DP

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.max

private var answer = 1
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P11053_forLoop_DP/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val N = br.readLine().toInt()
    val dp = IntArray(N) { 1 }
    val sequence = br.readLine().split(" ").map { it.toInt() }

    for (i in 0 until N) {
        for (j in 0 until i) {
            if (sequence[j] < sequence[i]) {
                dp[i] = max(dp[i], dp[j] + 1)
                answer = max(answer, dp[i])
            }
        }
    }
    println(answer)
}