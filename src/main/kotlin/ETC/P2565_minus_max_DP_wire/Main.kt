package ETC.P2565_minus_max_DP_wire

import java.io.FileInputStream
import java.util.*

private var N = 0 //전깃줄의 개수 (1 <= N <= 100)
private lateinit var wireList: Array<IntArray>
private lateinit var dp: IntArray
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2565_minus_max_DP_wire/input"))
    val br = System.`in`.bufferedReader()
    N = br.readLine().toInt()
    wireList = Array(N + 1) { IntArray(2) { 0 } }
    //dp[i] = i번 전깃줄을 포함헀을 때, 겹치지 않는 전깃줄 최대 개수
    dp = IntArray(N + 1) { 1 }

    repeat(N) {
        var st = StringTokenizer(br.readLine())
        wireList[it + 1][0] = st.nextToken().toInt()
        wireList[it + 1][1] = st.nextToken().toInt()
    }
    wireList.sortBy { it[0] }

    //정렬을 했기 때문에 나오는 전깃줄의 목표 번호(wireList[i][1])는 항상 증가해야한다.
    //만약 목표 번호가 감소하는 전깃줄이 나온다면 교차가 발생 (같은 목표 번호는 발생하지 않음)
    //쌓는값 = 현재 전깃줄보다 이전에 연결된 전깃줄 중에서 현재 전깃줄과 교차되지 않는 줄이 쌓아온 전깃줄 개수 + 1
    for (i in 1..wireList.lastIndex) {
        for (j in 1 until i) {
            if (wireList[i][1] > wireList[j][1]) {
                dp[i] = maxOf(dp[i], dp[j] + 1)
            }
        }
    }
    println(N - dp.maxOrNull()!!)
}