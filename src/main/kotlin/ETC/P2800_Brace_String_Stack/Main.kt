package ETC.P2800_Brace_String_Stack

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

private var data = StringBuilder()
private val braceList = mutableListOf<Brace>()
private val answerSet = mutableSetOf<String>()
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2800/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    data.append(br.readLine())
    val frontIndexStack = Stack<Int>()
    for (i in data.indices) {
        when (data[i]) {
            '(' -> frontIndexStack.add(i)
            ')' -> braceList.add(Brace(frontIndexStack.pop(), i))
        }
    }

    dfs(0)
    answerSet.sorted().drop(1)
        .forEach { println(it) }
}

private fun dfs(braceIndex: Int) {
    if (braceIndex > braceList.lastIndex) return
    val brace = braceList[braceIndex]

    data[brace.leftIndex] = '.'
    data[brace.rightIndex] = '.'
    answerSet.add(data.toString().filter { it != '.' })
    dfs(braceIndex + 1)

    data[brace.leftIndex] = '('
    data[brace.rightIndex] = ')'
    answerSet.add(data.toString().filter { it != '.' })
    dfs(braceIndex + 1)
}

private data class Brace(
    val leftIndex: Int,
    val rightIndex: Int
)
