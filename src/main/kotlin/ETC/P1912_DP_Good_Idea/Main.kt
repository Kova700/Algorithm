package ETC.P1912_DP_Good_Idea

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.max

private var N = 0 //(1 ≤ n ≤ 100,000)
private lateinit var inputs: IntArray
private lateinit var dp: IntArray
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1912_DP_Good_Idea/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    dp = IntArray(N)
    inputs = br.readLine().split(" ").map { it.toInt() }.toIntArray()

    dp[0] = inputs[0]
    var answer = dp[0]

    //dp = 1 ~ i까지 구간 중에서 연속되게 최대로 얻을 수 있는 값 배열
    //그 값은 1~i구간의 값 전부 합일 수도, i칸만의 값일 수도 있음
    //현재 칸 이전의 최대로 얻을 수 있는값 (dp[i-1]) == 값을 계속 쌓거나, 현재 부터 새로 쌓거나
    for (i in 1 until N) {
        dp[i] = max(dp[i - 1] + inputs[i], inputs[i])
        answer = max(answer, dp[i])
    }

    println(answer)
}