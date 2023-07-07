package ETC.P2448_starPrint_hard

import java.io.FileInputStream

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2448_starPrint_hard/input"))
    val br = System.`in`.bufferedReader()
    // N은 항상 3×2^k 수이다. (3, 6, 12, 24, 48, ...) (0 ≤ k ≤ 10, k는 정수)
    printStar(br.readLine().toInt())
}

private fun printStar(N: Int) {
    val starSet = mutableListOf(
        "  *   ",
        " * *  ",
        "***** "
    )
    var lineCount = 3

    while (lineCount < N) {
        for (i in starSet.indices) {
            starSet.add(starSet[i] + starSet[i])

            val sb = StringBuilder()
            for (j in 0 until lineCount) {
                sb.append(" ")
            }
            starSet[i] = sb.toString() + starSet[i] + sb.toString()
        }
        lineCount *= 2
    }
    println(starSet.joinToString("") { "$it\n" })
}
//삼각형이 늘어날 수록 위에 삼각형을 그대로 x2한 형태의 삼각형이 아래에 붙음을 이용