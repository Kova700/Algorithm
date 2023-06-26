package ETC.P2133_so_hard_rule_find_DP

import java.io.FileInputStream

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2133_so_hard_rule_find_DP/input"))
    val br = System.`in`.bufferedReader()
    val N = br.readLine().toInt() //(1 ≤ N ≤ 30)
    val dp = IntArray(N + 1)

    //타일의 1개의 크기는 2칸임으로 전체 칸 수가 홀수라면 채울 수가 없음
    dp[0] = 1
    for (i in 2..N step 2) {
        //이전에 만들어진 짝수번째 타일의 개수 X 3
        dp[i] = dp[i - 2] * 3
        for (j in i - 4 downTo 0 step 2) {
            dp[i] += dp[j] * 2
        }
    }
    println(dp[N])
}