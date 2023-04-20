package ETC.P9251_LCSubsequence

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Math.max

private lateinit var dp: Array<IntArray>
private var s1 = charArrayOf()
private var s2 = charArrayOf()
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P9251_LCSubsequence/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    s1 = charArrayOf('-') + br.readLine().toCharArray()
    s2 = charArrayOf('-') + br.readLine().toCharArray()
    dp = Array(s1.size) { IntArray(s2.size) }
    getLCS()
    println(dp.last().last())
}

private fun getLCS() {
    for (i in s1.indices) {
        for (j in s2.indices) {
            if (i == 0 || j == 0) continue

            if (s1[i] == s2[j]) {
                dp[i][j] = dp[i - 1][j - 1] + 1
                continue
            }
            dp[i][j] = max(dp[i][j - 1], dp[i - 1][j])
        }
    }
}