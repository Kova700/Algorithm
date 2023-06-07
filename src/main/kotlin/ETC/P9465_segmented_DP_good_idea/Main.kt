package ETC.P9465_segmented_DP_good_idea

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.max

private var T = 0 //테스트 케이스 개수
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P9465_segmented_DP_good_idea/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    T = br.readLine().toInt()
    repeat(T) {
        val n = br.readLine().toInt() // 스티커 개수 (1 ≤ n ≤ 100,000)
        val dp = Array(3) { IntArray(n) }
        val stickers = Array(2) { IntArray(n) }
        // 점수는 0보다 크거나 같고, 100보다 작거나 같은 정수
        stickers[0] = br.readLine().split(" ").map { it.toInt() }.toIntArray()
        stickers[1] = br.readLine().split(" ").map { it.toInt() }.toIntArray()

        dp[0][0] = 0
        dp[1][0] = stickers[0][0]
        dp[2][0] = stickers[1][0]

        //현재 칸에서 선택하지 않은 경우 -> 쌓일 점수가 없기때문에 이전칸 dp 중에서 가장 큰 값 가져옴
        // dp[0][i] = maxOf(dp[0][i - 1], dp[1][i - 1], dp[2][i - 1])
        //현재 칸에서 윗칸을 선택하는 경우 -> 이전은 아랫칸 혹은 선택하지 않은칸 이어야함
        // dp[1][i] = stickers[0][i] + max(dp[2][i - 1], dp[0][i - 1])
        //현재 칸에서 아랫칸을 선택한 경우 -> 이전은 윗 칸 혹은 선택하지 않은칸 이어야함
        // dp[2][i] = stickers[1][i] + max(dp[1][i - 1], dp[0][i - 1])

        for (i in 1 until n) {
            dp[0][i] = maxOf(dp[0][i - 1], dp[1][i - 1], dp[2][i - 1])
            dp[1][i] = stickers[0][i] + max(dp[2][i - 1], dp[0][i - 1])
            dp[2][i] = stickers[1][i] + max(dp[1][i - 1], dp[0][i - 1])
        }
        println(maxOf(dp[0][n - 1], dp[1][n - 1], dp[2][n - 1]))
    }
}