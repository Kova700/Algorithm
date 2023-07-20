package ETC.P14888_DFS

import java.io.FileInputStream

private var N = 0 //수의 개수 (2 ≤ N ≤ 11)
private lateinit var sequence: IntArray //주어진 수열
private lateinit var operators: IntArray //+ , - , x, ÷ 순서로 연산자별로 남은 개수 카운팅 (개수 총 합 = N-1)
private var maxAnswer = Int.MIN_VALUE
private var minAnswer = Int.MAX_VALUE
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P14888_DFS/input"))
    val br = System.`in`.bufferedReader()
    N = br.readLine().toInt()
    sequence = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    operators = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    for (i in 0 until 4) {
        if (operators[i] == 0) continue
        operators[i] -= 1
        dfs(2, calculate(i, sequence[0], sequence[1]))
        operators[i] += 1
    }
    println("$maxAnswer\n$minAnswer")
}

private fun dfs(start: Int, sum: Int) {
    if (start == N) {
        maxAnswer = maxOf(maxAnswer, sum)
        minAnswer = minOf(minAnswer, sum)
        return
    }

    //남은 연산자찾아서 dfs
    for (i in 0 until 4) {
        if (operators[i] == 0) continue
        operators[i] -= 1
        dfs(start + 1, calculate(i, sum, sequence[start]))
        operators[i] += 1
    }
}

private fun calculate(index: Int, a: Int, b: Int) =
    when (index) {
        0 -> a + b // +
        1 -> a - b // -
        2 -> a * b // x
        3 -> a / b // ÷
        else -> 0
    }