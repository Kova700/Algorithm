package ETC.P9935_String_replace_slice_good_idea

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var inputString = ""
private var boomText = ""
private val sb = StringBuilder()

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P9935_String_replace_slice_good_idea/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    inputString = br.readLine()
    boomText = br.readLine()

    boom()
    val answer = sb.toString().ifEmpty { "FRULA" }
    println(answer)
}

private fun boom() {
    out@ for (c in inputString) {
        sb.append(c)
        for (i in boomText.indices) {
            if (sb.getOrNull(sb.lastIndex - i) != boomText[boomText.lastIndex - i]) continue@out
        }
        sb.delete(sb.length - boomText.length, sb.length)
    }
}