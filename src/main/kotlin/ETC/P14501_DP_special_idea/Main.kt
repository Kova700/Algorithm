package ETC.P14501_DP_special_idea

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.max

private var N = 0 //(1 ≤ N ≤ 15)
private lateinit var T: IntArray
private lateinit var P: IntArray
private lateinit var dp: IntArray
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P14501_DP_special_idea/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    T = IntArray(N + 1) //상담당 기간
    P = IntArray(N + 1) //상담당 급여
    dp = IntArray(N + 1)

    repeat(N) {
        br.readLine().split(" ").map { it.toInt() }.apply {
            T[N - it] = this[0]
            P[N - it] = this[1]
        }
    }

    //배열을 뒤집은 이유 : DP로 풀고 싶어서

    //배열을 뒤집지 않으면 (앞날의 상담 여부부터 확인하면)
    //마땅히 쌓을 만한 DP값도 없거니와
    //무작정 수익이 최대인 선택지를 고르는 것도 의미가 없음
    //ex : 1일, 4일 선택이 최대일 수 있는데, 2일의 급여가 1일보다 크다면
    // 2일을 최대로 인식해서 2일 상담 기간 때문에 4일 상담을 하지 못할 수 있기때문
    //고로 배열을 뒤집거나, 마지막 인덱스 부터 확인하는 방법이 아니라면
    //브루트 포스와 같이 모든 선택지의 최대값을 다 구해보고 그 중에서도 최대값을 구하는 방식으로 풀어야함

    //배열을 뒤집고 (뒷날의 상담 여부부터 확인하면)
    //각 날에 얻을 수 있는 최대 상담 수익을 점점 쌓아가면
    //DP 마지막 칸에 최대 상담 수익을 얻을 수 있다.
    //ex : 1일, 4일 선택이 최대일 때, 4일부터 각 날의 상담 가능 여부를 파악하고
    //각 날에서 얻을 수 있는 상담 수익의 최대값을 DP값으로 점점 쌓아 갈 수 있음
    // 3일의 최대값은 4일의 최대 수익 + 3일의 최대 수익 이런 식으로

    //그 날 상담을 할 수 있나/없나로 분기
    //상담을 할 수 있는지 확인하는 기준 : 그 날하는 상담의 소요기간이 근무 가능일 수보다 작은지

    //i == 근무가능 일 수 (남은 일 수) (i에 의미를 어떤 걸 두느냐가 DP의 관건)
    for (i in 1..N) {
        //근무 가능한 일 수가 해당 날짜의 상담기간보다 작아서 상담을 할 수 없다면
        //상담을 하지 않음으로, 당일 수익이 없음 (다음날 상담 수익과 같음)
        if (i < T[i]) {
            dp[i] = dp[i - 1]
            continue
        }
        //근무 가능한 일 수가 해당 날짜의 상담 소요시간보다 크거나 같아서 해당 상담을 진행 할 수 있다면
        //상담을 하지 않은 경우와 상담을 한 경우(현재 상담 수익 + 현재 상담 이후에 할 수 있는 상담들의 최대 수익 합) 중 최대값
        dp[i] = max(dp[i - 1], P[i] + dp[i - T[i]])
    }

    println(dp[N])
}