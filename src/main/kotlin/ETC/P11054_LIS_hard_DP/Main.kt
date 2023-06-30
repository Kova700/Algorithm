package ETC.P11054_LIS_hard_DP

import java.io.FileInputStream

private var N = 0 //수열의 크기 (1 ≤ N ≤ 1,000)
private lateinit var data: IntArray
private lateinit var lDp: IntArray
private lateinit var rDp: IntArray
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P11054_LIS_hard_DP/input"))
    val br = System.`in`.bufferedReader()
    N = br.readLine().toInt()
    //수열 내부값 (1 ≤ Ai ≤ 1,000)
    lDp = IntArray(N) { 1 }
    rDp = IntArray(N) { 1 }
    data = br.readLine().split(" ").map { it.toInt() }.toIntArray()

    //lDp[i] == i 인덱스를 기준으로 좌측에 data[i]보다 작은 숫자들의 개수 (증가하는 부분 수열 최대 길이)
    //rDp[i] == i 인덱스를 기준으로 우측에 data[i]보다 작은 숫자들의 개수 (증가하는 부분 수열 최대 길이)
    for (i in data.indices) {
        for (j in 0 until i) {
            if (data[i] > data[j]) lDp[i] = maxOf(lDp[i], lDp[j] + 1)
        }
    }

    for (i in N - 1 downTo 0) {
        for (j in N - 1 downTo i) {
            if (data[i] > data[j]) rDp[i] = maxOf(rDp[i], rDp[j] + 1)
        }
    }

    var answer = 0
    for (i in 0 until N) {
        answer = maxOf(answer, rDp[i] + lDp[i])
    }
    //양쪽 모두 시작값이 1임으로 중복값제외 (-1)
    println(answer - 1)
}