package ETC.P10942_Palindrome_DP_so_hard

import java.io.FileInputStream
import java.util.*

private var N = 0 //수열의 크기 N (1 ≤ N ≤ 2,000)
private var M = 0 //질문의 개수 M (1 ≤ M ≤ 1,000,000)
private lateinit var data: IntArray
private lateinit var dp: Array<IntArray>
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P10942_Palindrome_DP_so_hard/input"))
    val br = System.`in`.bufferedReader()
    N = br.readLine().toInt()

    data = IntArray(N + 1)
    val st = StringTokenizer(br.readLine())
    for (i in 1..N) {
        //칠판에 적은 수는 100,000보다 작거나 같은 자연수이다.
        data[i] = st.nextToken().toInt()
    }
    M = br.readLine().toInt()

    //dp[i][j] == i ~ j까지 펠린드롬인지 여부
    //길이가 1인 수열의 펠린드롬 여부 == 1
    dp = Array(N + 1) { i ->
        IntArray(N + 1) { j ->
            if (i == j) 1 else 0
        }
    }

    //길이가 2인 수열의 펠린드롬 여부 == 앞 숫자와 뒷 숫자가 같아야함
    for (i in 1 until N) {
        if (data[i] == data[i + 1]) dp[i][i + 1] = 1
    }

    //길이가 3이상인 수열의 펠린드롬 여부 ==
                //[(시작점의 값 == 끝의 값) && (시작점 + 1) ~ (끝 - 1)(시작과 끝 사이값들)값이 펠린드롬]
    for (k in 2 until N) { // k == 추가된 길이
        for (i in 1..N - k) { // i == 시작점
            if ((data[i] == data[i + k]) && (dp[i + 1][i + k - 1] == 1)) {
                dp[i][i + k] = 1
            }
        }
    }

    val sb = StringBuilder()
    repeat(M) {
        //(1 ≤ S ≤ E ≤ N)
        val (S, E) = br.readLine().split(" ").map { it.toInt() }
        if (dp[S][E] == 1) sb.append("1\n")
        else sb.append("0\n")
    }
    println(sb)
}