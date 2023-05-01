package ETC.P1010_DP_Combination

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var T = 0
private val dp = Array(30 + 1) { LongArray(30 + 1) }
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1010_DP_Combination/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    T = br.readLine().toInt()
    repeat(T) {
        val (N, M) = br.readLine().split(" ").map { it.toInt() }
        println(combination(M, N))
    }
}

private fun combination(N: Int, R: Int): Long {
    if ((R == 0) || (N == R)) return 1L
    if (R == 1) return N.toLong()
    if (dp[N][R] != 0L) return dp[N][R]

    dp[N][R] = combination(N - 1, R - 1) + combination(N - 1, R)
    return dp[N][R]
}