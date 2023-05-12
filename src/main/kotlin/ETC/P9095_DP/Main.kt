package ETC.P9095_DP

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var T = 0 //케이스의 개수
private val dp = IntArray(11)
private var lastIndex = 4
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P9095_DP/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    T = br.readLine().toInt()
    dp[1] = 1
    dp[2] = 2
    dp[3] = 4
    repeat(T) {
        //정수 n이 주어진다. n은 양수이며 11보다 작다.
        println(getAnswer(br.readLine().toInt()))
    }
}

//n=1일 때 (1),
//1
//n=2일 떄 (2),
//1     +1
//2
//n=3일 때 (4),
//1+1   +1 ①
//2     +1 ①
//1     +2 ②
//3        ③

//n=4일 때 (7),
//1+1+1 +1 ①
//2+1   +1 ①
//1+2   +1 ①
//3     +1 ①

//1+1   +2 ②
//2     +2 ②

//1     +3 ③

//n=5일 때 (13),
//1+1+1+1   +1 ①
//2+1+1     +1 ①
//1+2+1     +1 ①
//1+1+2     +1 ①
//2+2       +1 ①
//3+1       +1 ①
//1+3       +1 ①

//1+1+1     +2 ②
//1+2       +2 ②
//2+1       +2 ②
//3         +2 ②

//1+1       +3 ③
//2         +3 ③

//찾아낸 규칙
//① dp[i] = ①dp[i - 1] + ②dp[i - 2] + ③dp[i - 3]

private fun getAnswer(n: Int): Int {
    if (dp[n] != 0) return dp[n]

    for (i in lastIndex..n) {
        dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3]
    }
    lastIndex = n
    return dp[n]
}