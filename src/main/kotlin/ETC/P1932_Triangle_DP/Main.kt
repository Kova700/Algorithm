package ETC.P1932_Triangle_DP

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.max

private var N = 0 //삼각형의 크기 n(1 ≤ n ≤ 500)
private lateinit var triangle: Array<IntArray>
private lateinit var dp: Array<IntArray>
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1932_Triangle_DP/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    triangle = Array(N) { IntArray(it + 1) }
    dp = Array(N) { IntArray(it + 1) }

    repeat(N) { i ->
        triangle[i] = br.readLine().split(" ").map { it.toInt() }.toIntArray()
        dp[i] = triangle[i].copyOf()
    }

    for (i in 0 until N - 1) {
        for (j in 0 until triangle[i].size) {
            dp[i + 1][j] = max(dp[i + 1][j], triangle[i + 1][j] + dp[i][j])
            dp[i + 1][j + 1] = max(dp[i + 1][j + 1], triangle[i + 1][j + 1] + dp[i][j])
        }
    }
    println(dp.last().maxOf { it })
}