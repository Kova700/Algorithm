package ETC.P2839

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0 //(3 ≤ N ≤ 5000)
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2839/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    println(getAnswer())
}

private fun getAnswer(): Int {
    var count = -1

    for (i in (N / 5) downTo 0) {
        if ((N - (5 * i)) % 3 == 0) {
            count = i + ((N - (5 * i)) / 3)
            return count
        }
    }

    return count
}