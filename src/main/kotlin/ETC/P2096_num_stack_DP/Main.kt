package ETC.P2096_num_stack_DP

import java.io.FileInputStream
import java.util.*

private var N = 0 // (1 ≤ N ≤ 100,000)
private lateinit var board: Array<IntArray>
private lateinit var minDp: Array<IntArray>
private lateinit var maxDp: Array<IntArray>
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2096_num_stack_DP/input"))
    val br = System.`in`.bufferedReader()
    N = br.readLine().toInt()
    board = Array(N) { IntArray(3) }
    minDp = Array(N) { IntArray(3) }
    maxDp = Array(N) { IntArray(3) }

    repeat(N) { i ->
        val st = StringTokenizer(br.readLine())
        repeat(3) { j ->
            board[i][j] = st.nextToken().toInt()
        }
    }

    //현재칸이 0번이라면 이전칸 0,1번에서 쌓아 온 값 사용
    //현재칸이 1번이라면 이전칸 0,1,2번에서 쌓아 온 값 사용
    //현재칸이 2번이라면 이전칸 2,3번에서 쌓아 온 값 사용
    minDp[0] = board[0].copyOf()
    maxDp[0] = board[0].copyOf()
    for (i in 1 until N) {
        maxDp[i][0] = board[i][0] + maxOf(maxDp[i - 1][0], maxDp[i - 1][1])
        maxDp[i][1] = board[i][1] + maxOf(maxDp[i - 1][0], maxDp[i - 1][1], maxDp[i - 1][2])
        maxDp[i][2] = board[i][2] + maxOf(maxDp[i - 1][1], maxDp[i - 1][2])

        minDp[i][0] = board[i][0] + minOf(minDp[i - 1][0], minDp[i - 1][1])
        minDp[i][1] = board[i][1] + minOf(minDp[i - 1][0], minDp[i - 1][1], minDp[i - 1][2])
        minDp[i][2] = board[i][2] + minOf(minDp[i - 1][1], minDp[i - 1][2])
    }
    println("${maxDp.last().maxOf { it }} ${minDp.last().minOf { it }}")
}