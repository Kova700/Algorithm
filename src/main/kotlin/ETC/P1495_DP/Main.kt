package ETC.P1495_DP

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private lateinit var V: IntArray
private lateinit var dp: Array<HashSet<Int>>
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1495_DP/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    //N = 곡의 개수, S = 시작 볼륨 , M = 최대 볼륨
    val (N, S, M) = br.readLine().split(" ").map { it.toInt() }
    V = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    dp = Array(N) { hashSetOf() }

    dp[0] = hashSetOf<Int>().apply {
        if (S + V[0] in 0..M) add(S + V[0])
        if (S - V[0] in 0..M) add(S - V[0])
    }

    for (i in 1 until N) {
        for (num in dp[i - 1]) {
            if (num + V[i] in 0..M) dp[i].add(num + V[i])
            if (num - V[i] in 0..M) dp[i].add(num - V[i])
        }
        if (dp[i].isEmpty()) {
            println(-1)
            return
        }
    }
    println(dp[N - 1].maxOfOrNull { it } ?: -1)
}