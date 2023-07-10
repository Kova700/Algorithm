package ETC.P14500_DFS_tetris_rotation

import java.io.FileInputStream
import java.util.*

private var N = 0 //세로 (4 ≤ N ≤ 500)
private var M = 0 //가로 (4 ≤ M ≤ 500)
private lateinit var board: Array<List<Int>>
private lateinit var isVisited: Array<BooleanArray>
private var answer = 0
private val MY = arrayOf(1, -1, 0, 0) //상하좌우
private val MX = arrayOf(0, 0, -1, 1) //상하좌우

//모든 도형 회전, 대칭시킨 경우의 수 구해서 풀려고 했는데,
//마지막도형(ㅜ)을 제외하고 전부 돌려보면 그냥 DFS로 한 depth씩 들어가는 구조라서
//DFS를 이용한 탐색으로 해결할 수 있음을 알게된 문제
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P14500_DFS_tetris_rotation/input"))
    val br = System.`in`.bufferedReader()

    val st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()
    isVisited = Array(N) { BooleanArray(M) }
    board = Array(N) { listOf(M) }
    repeat(N) { i ->
        //입력으로 주어지는 수는 1,000을 넘지 않는 자연수이다.
        board[i] = br.readLine().split(" ").map { it.toInt() }
    }

    for (y in 0 until N) {
        for (x in 0 until M) {
            dfs(y, x, board[y][x], 1)
            ㅜcheck(y, x)
        }
    }

    println(answer)
}

private val ㅜShapeList = arrayOf(
    arrayOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(1, 1)),
    arrayOf(Pair(0, 0), Pair(1, 0), Pair(1, 1), Pair(2, 0)),
    arrayOf(Pair(0, 0), Pair(0, 1), Pair(-1, 1), Pair(0, 2)),
    arrayOf(Pair(0, 0), Pair(0, 1), Pair(1, 1), Pair(-1, 1))
)

private fun ㅜcheck(y: Int, x: Int) {
    for (shape in ㅜShapeList) {
        var sum = 0
        for (i in shape.indices) {
            if (!isInMap(y + shape[i].first, x + shape[i].second)) break
            sum += board[y + shape[i].first][x + shape[i].second]
        }
        answer = maxOf(answer, sum)
    }
}

private fun dfs(y: Int, x: Int, sum: Int, count: Int) {
    isVisited[y][x] = true
    if (count == 4) {
        answer = maxOf(answer, sum)
        isVisited[y][x] = false
        return
    }
    for (i in 0 until 4) {
        val nextY = MY[i] + y
        val nextX = MX[i] + x
        if (isInMap(nextY, nextX) && !isVisited[nextY][nextX]) {
            dfs(nextY, nextX, sum + board[nextY][nextX], count + 1)
        }
    }
    isVisited[y][x] = false
}

private fun isInMap(y: Int, x: Int) =
    (y in 0 until N) && (x in 0 until M)
