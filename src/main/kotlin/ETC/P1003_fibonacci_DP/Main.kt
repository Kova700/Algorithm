package ETC.P1003_fibonacci_DP

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var T = 0
private val dp = Array(40 + 1) { Pair(-1, -1) }
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1003_fibonacci_DP/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    T = br.readLine().toInt()
    repeat(T) {
        getAnswer(br.readLine().toInt())
    }
}

private fun getAnswer(n: Int) {
    println("${fibonacci(n).first} ${fibonacci(n).second}")
}

private fun fibonacci(n: Int): Pair<Int, Int> {
    if (n == 0) return Pair(1, 0)
    if (n == 1) return Pair(0, 1)
    if (dp[n].first != -1 && dp[n].second != -1) return dp[n]
    dp[n] = fibonacci(n - 1) + fibonacci(n - 2)
    return dp[n]
}

private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(this.first + other.first, this.second + other.second)
}