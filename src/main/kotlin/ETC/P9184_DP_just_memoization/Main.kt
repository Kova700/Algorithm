package ETC.P9184_DP_just_memoization

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private val dp = Array(21) { Array(21) { IntArray(21) { 0 } } }
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P9184_just_memoization/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    while (true) {
        //-50 ≤ a, b, c ≤ 50
        val (a, b, c) = br.readLine().split(" ").map { it.toInt() }
        if (a == -1 && b == -1 && c == -1) break
        println("w($a, $b, $c) = ${w(a, b, c)}")
    }
}

private fun w(a: Int, b: Int, c: Int): Int {
    if (a <= 0 || b <= 0 || c <= 0) return 1
    if (a > 20 || b > 20 || c > 20) return w(20, 20, 20)
    if (dp[a][b][c] != 0) return dp[a][b][c]
    if (b in (a + 1) until c) {
        dp[a][b][c] = w(a, b, c - 1) + w(a, b - 1, c - 1) - w(a, b - 1, c)
        return dp[a][b][c]
    }
    dp[a][b][c] = w(a - 1, b, c) + w(a - 1, b - 1, c) + w(a - 1, b, c - 1) - w(a - 1, b - 1, c - 1)
    return dp[a][b][c]
}