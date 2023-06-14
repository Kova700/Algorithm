package ETC.P11660_square_DP

import java.io.FileInputStream
import java.util.*

private var N = 0 //표의 크기 (1 ≤ N ≤ 1024)
private var M = 0 //합을 구해야 하는 횟수 (1 ≤ M ≤ 100,000)
private lateinit var dp: Array<IntArray>
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P11660_square_DP/input"))
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()

    dp = Array(N + 1) { IntArray(N + 1) }
    //표에 채워져 있는 수는 1,000보다 작거나 같은 자연수이다. (x1 ≤ x2, y1 ≤ y2)
    for (i in 1..N) {
        st = StringTokenizer(br.readLine())
        for (j in 1..N) {
            //dp[i][j] == (1,1)에서 (i,j)까지 사각형 숫자 합
            dp[i][j] = dp[i][j - 1] + dp[i - 1][j] - dp[i - 1][j - 1] + st.nextToken().toInt()
        }
    }

    val sb = StringBuilder()
    for (i in 0 until M) {
        st = StringTokenizer(br.readLine())
        val y1 = st.nextToken().toInt()
        val x1 = st.nextToken().toInt()
        val y2 = st.nextToken().toInt()
        val x2 = st.nextToken().toInt()
        sb.append("${dp[y2][x2] - dp[y2][x1 - 1] - dp[y1 - 1][x2] + dp[y1 - 1][x1 - 1]}\n")
    }
    println(sb)
}