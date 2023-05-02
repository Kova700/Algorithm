package ETC.P9655

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P9655/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    getAnswer(br.readLine().toInt()) //돌 개수 (1 ≤ N ≤ 1000)
}

private fun getAnswer(n: Int) {
    var isSK = true
    val temp = n / 3
    if (temp % 2 != 0) isSK = false

    if ((n % 3) % 2 == 0) isSK = !isSK
    if (isSK) println("SK") else println("CY")
}