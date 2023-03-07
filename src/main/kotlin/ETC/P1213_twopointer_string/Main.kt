package ETC.P1213_twopointer_string

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private lateinit var charMap: Map<Char, Int> //알파벳 대문자로만 된 최대 50글자
private lateinit var charArray: CharArray
private var name = ""
private val charList = Array(26){0}

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1213/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    name = br.readLine()
    charArray = name.toCharArray()
    charMap = name.groupBy { it }.mapValues { it.value.count() }

    charMap.forEach {
        charList[it.key - 'A'] = it.value
    }

    if (charList.count { it%2 != 0 } > 1) {
        println("I'm Sorry Hansoo")
        return
    }

    getAnswer()
    println(charArray.joinToString(""))
}

private fun getAnswer() {
    var p1 = 0
    var p2 = charArray.lastIndex
    var tempChar: Char = ' '

    while (true) {
        //포인터가 엇갈리거나, 같으면 종료
        if (p1 > p2) return

        var idx = charList.indexOfFirst { it > 1 }
        if (idx == -1) {
            idx = charList.indexOfFirst { it == 1 }
            tempChar = (idx + 'A'.code).toChar()
            charList[idx] = 0
        } else {
            charList[idx] -= 2
            tempChar = (idx + 'A'.code).toChar()
        }

        charArray[p1] = tempChar
        charArray[p2] = tempChar

        p1 += 1
        p2 -= 1
    }
}