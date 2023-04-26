package ETC.P17609_TwoPointer_DFS

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var T = 0
private var s = ""
private var answer = 0
private var findAnswer = false
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P17609_TwoPointer_DFS/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    T = br.readLine().toInt()
    repeat(T) {
        s = br.readLine()
        answer = 0
        findAnswer = false
        dfs(0, s.lastIndex, true)
        println(answer)
    }
}

private fun dfs(
    start: Int,
    end: Int,
    haveChance: Boolean
) {
    if (start > end) {
        if (answer in 0..1) findAnswer = true
        return
    }

    if (s[start] != s[end]) {

        var flag = true
        if (haveChance && (start + 1 <= end) && s.getOrNull(start + 1) == s[end]) {
            answer = 1
            flag = false
            dfs(start + 1, end, false)
        }
        if (haveChance && (start <= end - 1) && s[start] == s.getOrNull(end - 1)) {
            answer = 1
            flag = false
            dfs(start, end - 1, false)
        }
        if (flag) {
            if (!findAnswer) answer = 2
        }
        return
    }

    dfs(start + 1, end - 1, haveChance)
}