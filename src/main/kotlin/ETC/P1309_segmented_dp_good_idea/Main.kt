package ETC.P1309_segmented_dp_good_idea

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0 //우리의 크기 N(1≤N≤100,000)
private lateinit var dp: Array<IntArray>
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1309_segmented_dp_good_idea/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    dp = Array(N) { IntArray(3) { 1 } }
    //dp[i][0] (사자 배치안하는 경우 dp) = 이전 칸에서 배치 안하는 경우 + 왼쪽 + 오른쪽 배치하는 경우
    //dp[i][1] (사자를 왼쪽에 배치하는 경우 dp) = 이전 칸에서 배치 안하는 경우 + 이전 칸에서 오른쪽 배치하는 경우
    //dp[i][2] (사자를 오른쪽에 배치하는 경우 dp) = 이전 칸에서 배치 안하는 경우 + 이전 칸에서 왼쪽 배치하는 경우
    for (i in 1 until N) {
        dp[i][0] = (dp[i - 1][0] + dp[i - 1][1] + dp[i - 1][2]) % 9901
        dp[i][1] = (dp[i - 1][0] + dp[i - 1][2]) % 9901
        dp[i][2] = (dp[i - 1][0] + dp[i - 1][1]) % 9901
    }
    //나머지를 쌓아왔더라도 구해야하 는 값이 합이라면 한 번더 나눠주는거 잊지말자
    println(dp[N - 1].sum() % 9901)
}