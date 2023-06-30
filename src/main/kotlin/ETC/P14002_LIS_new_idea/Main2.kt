package ETC.P14002_LIS_new_idea

import java.io.FileInputStream

private var N = 0 //A의 크기 N (1 ≤ N ≤ 1,000)
private lateinit var data: IntArray
private lateinit var dp: Array<List<Int>>
private var answerIndex = 0
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P14002_LIS_new_idea/input"))
    val br = System.`in`.bufferedReader()
    N = br.readLine().toInt()
    //(1 ≤ data[i] ≤ 1,000)
    data = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    dp = Array(N) { listOf(data[it]) }

    for (i in data.indices) {
        for (j in 0 until i) {
            if (data[i] > data[j]) {
                if (dp[i].size < dp[j].size + 1) {
                    dp[i] = dp[j] + data[i]
                    if (dp[answerIndex].size < dp[i].size) {
                        answerIndex = i
                    }
                }
            }
        }
    }
    println(dp[answerIndex].size)
    println(dp[answerIndex].joinToString(" "))
}