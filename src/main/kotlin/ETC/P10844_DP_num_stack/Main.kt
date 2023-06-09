package ETC.P10844_DP_num_stack

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0 //계단 수 길이(1<=N<=100)
private lateinit var dp: Array<LongArray>
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P10844_DP_num_stack/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    dp = Array(N + 1) { LongArray(10) }

    if (N == 1) {
        println(9)
        return
    }

    for (j in 1..9) {
        dp[1][j] = 1
    }
    //dp[i][j] = 길이가 i일때 마지막 숫자가 j인 수의 개수
    //dp[i][j] = dp[i-1][j-1] + dp[i-1][j+1]

    for (i in 2..N) {
        for (j in 0..9) {
            when (j) {
                0 -> dp[i][j] = dp[i - 1][1] // 0은 1 뒤에만 추가할 수 있음
                9 -> dp[i][j] = dp[i - 1][8] // 9는 8 뒤에만 추가할 수 있음
                else -> dp[i][j] = (dp[i - 1][j - 1] + dp[i - 1][j + 1]) % 1_000_000_000
            }
        }
    }
    println(dp[N].sum() % 1_000_000_000)
}