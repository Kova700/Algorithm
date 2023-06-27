package ETC.P9252_LCS_DP_find_string

import java.io.FileInputStream

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P9252_LCS_DP_find_string/input"))
    val br = System.`in`.bufferedReader()
    //최대 1000글자로 이루어져 있다.
    val s1 = "-" + br.readLine()
    val s2 = "-" + br.readLine()
    val dp = Array(s1.length) { IntArray(s2.length) }

    for (i in 1..dp.lastIndex) {
        for (j in 1..dp[0].lastIndex) {
            if (s1[i] == s2[j]) {
                dp[i][j] = dp[i - 1][j - 1] + 1
                continue
            }
            dp[i][j] = maxOf(dp[i][j - 1], dp[i - 1][j])
        }
    }

    val sb = StringBuilder()
    var y = dp.lastIndex
    var x = dp[0].lastIndex
    while (dp[y][x] != 0) {
        if (dp[y][x - 1] == dp[y][x]) {
            x -= 1
            continue
        }
        if (dp[y - 1][x] == dp[y][x]) {
            y -= 1
            continue
        }
        sb.append(s1[y])
        x -= 1
        y -= 1
    }

    println(dp.last().last())
    if (dp.last().last() != 0) {
        println(sb.toString().reversed())
    }
}