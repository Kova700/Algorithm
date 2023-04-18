package ETC.P14425_String

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0
private var M = 0
private var SGroup = HashMap<String, Boolean>()
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P14425_String/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    br.readLine().split(" ").map { it.toInt() }.apply {
        N = this[0]
        M = this[1]
    }
    repeat(N) { SGroup[br.readLine()] = true }
    var count = 0
    repeat(M) { if (SGroup[br.readLine()] != null) count++ }
    println(count)
}