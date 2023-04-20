package ETC.P5582_LCString

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.max

private lateinit var s1: CharArray
private lateinit var s2: CharArray
private lateinit var dp: Array<IntArray>
private var LCS = 0
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P5582_LCString/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    s1 = charArrayOf('-') + br.readLine().toCharArray()
    s2 = charArrayOf('-') + br.readLine().toCharArray()
    dp = Array(s1.size) { IntArray(s2.size) }

    getLCS()
    println(LCS)
}

private fun getLCS() {
    for (i in s1.indices) {
        for (j in s2.indices) {
            if (i == 0 || j == 0) continue

            if (s1[i] == s2[j]) {
                dp[i][j] = dp[i - 1][j - 1] + 1
                LCS = max(dp[i][j], LCS)
            }
        }
    }
}