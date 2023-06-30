package ETC.P14002_LIS_new_idea

import java.io.FileInputStream

private var N = 0 //A의 크기 N (1 ≤ N ≤ 1,000)
private lateinit var data: IntArray
private lateinit var dp: IntArray
private var maxLISSize = 1
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P14002/input"))
    val br = System.`in`.bufferedReader()
    N = br.readLine().toInt()
    //(1 ≤ data[i] ≤ 1,000)
    data = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    dp = IntArray(N) { 1 }

    for (i in data.indices) {
        for (j in 0 until i) {
            if (data[i] > data[j]) {
                if (dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1
                    maxLISSize = maxOf(maxLISSize, dp[i])
                }
            }
        }
    }

    println(maxLISSize)
    val answer = IntArray(maxLISSize)
    for (i in N - 1 downTo 0) {
        if (dp[i] != maxLISSize) continue
        answer[maxLISSize - 1] = data[i]
        maxLISSize--
    }
    println(answer.joinToString(" "))
}