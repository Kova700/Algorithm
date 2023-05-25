package ETC.P11051_DP_Pascals_Triangle

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private lateinit var dp: Array<IntArray>
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P11051/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    // (1 ≤ N ≤ 1,000) (1 ≤ K ≤ N)
    val (N, K) = br.readLine().split(" ").map { it.toInt() }
    dp = Array(N + 1) { IntArray(N + 1) }

    //[0C0 == 1]
    for (i in 0..N) {
        dp[i][0] = 1 //[nC0 == 1]
        for (j in 1..i) {
            //파스칼의 삼각형 성질 이용해서 DP 값 쌓음 [nCr = n-1Cr-1 + n-1Cr]
            dp[i][j] = (dp[i - 1][j - 1] + dp[i - 1][j]) % 10_007
        }
    }

    println(dp[N][K])
}