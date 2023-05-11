package ETC.P2156_DP_GoodIdea

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.max

private var N = 0 //포도주 잔의 개수 (1 ≤ n ≤ 10,000)
private lateinit var dp: IntArray
private lateinit var wineList: IntArray
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2156_DP_GoodIdea/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    dp = IntArray(N + 1)
    wineList = IntArray(N + 1)
    repeat(N) {
        //포도주의 양은 1,000 이하의 음이 아닌 정수
        wineList[it + 1] = br.readLine().toInt()
    }

    //잔이 2개 이하일 때는 다 마시는게 최대로 많이 마시는 것
    //잔이 1개일 경우
    //1
    //잔이 2개일 경우
    //12

    if (N == 1) {
        println(wineList[1])
        return
    }
    dp[1] = wineList[1]
    dp[2] = wineList[1] + wineList[2]
    //잔이 3개 이상일 때부터 선택지가 3가지로 갈림 이 중에서 최대로 마시는 경우를 찾아야함
    //① 현재 음료수를 안마시는 경우
    //② 이전 음료를 마시지 않고, 현재 음료수를 마시는 경우 (연속 X)
    //③ 현재 + 이전 음료수를 마시는 경우 (연속으로 마시는 경우)

    //잔이 3개일 경우
    //12  ①
    //1 3 ②
    // 23 ③

    //잔이 4개일 경우
    //12   ①
    //1 3  ①
    // 23  ①
    //12 4 ②
    //1 34 ③

    //잔이 5개일 경우
    //12    ①
    // 23   ①
    //1 3   ①
    //12 4  ①
    //1 34  ①
    //12  5 ②
    //1 3 5 ②
    // 23 5 ②
    //12 45 ③

    //찾은 규칙성
    //① ==> dp[i] = dp[i-1]
    //② ==> dp[i] = max(dp[i], wineList[i] + dp[i-2])
    //③ ==> dp[i] = max(dp[i], wineList[i] + wineList[i-1] + dp[i-3])

    for (i in 3..N) {
        dp[i] = dp[i-1]
        dp[i] = max(dp[i], wineList[i] + dp[i-2])
        dp[i] = max(dp[i], wineList[i] + wineList[i-1] + dp[i-3])
    }

    println(dp[N])
}