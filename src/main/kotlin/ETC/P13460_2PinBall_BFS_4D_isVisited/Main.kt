package ETC.P13460_2PinBall_BFS_4D_isVisited

import java.io.FileInputStream
import java.util.*

private var N = 0 //세로 (3 ≤ N ≤ 10)
private var M = 0 //가로 (3 ≤ M ≤ 10)
private val MY = arrayOf(1, -1, 0, 0) //상하좌우
private val MX = arrayOf(0, 0, -1, 1) //상하좌우
private lateinit var board: Array<CharArray>

//Ry,Rx,By,BX 방문 여부 체크
private lateinit var isVisited: Array<Array<Array<BooleanArray>>>
private val Q: Queue<Move> = LinkedList()
private var answer = -1
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P13460_2PinBall_BFS_4D_isVisited/input"))
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()
    board = Array(N) { CharArray(M) }
    isVisited = Array(N) { Array(M) { Array(N) { BooleanArray(M) } } }

    repeat(N) { i ->
        st = StringTokenizer(br.readLine())
        board[i] = st.nextToken().toCharArray()
    }

    bfs()
    println(answer)
}

private fun bfs() {
    var rp = Point(0, 0)
    var bp = Point(0, 0)
    var holeP = Point(0, 0)
    for (i in 0 until N) {
        for (j in 0 until M) {
            // '.'은 빈 칸을 의미하고
            // '#'은 공이 이동할 수 없는 장애물 또는 벽
            // 'O'는 구멍의 위치
            //'R'은 빨간 구슬의 위치, 'B'는 파란 구슬의 위치
            when (board[i][j]) {
                'R' -> rp = Point(i, j)
                'B' -> bp = Point(i, j)
                'O' -> holeP = Point(i, j)
            }
        }
    }
    isVisited[rp.y][rp.x][bp.y][bp.x] = true

    Q.add(Move(rp, bp, 0))

    while (Q.isNotEmpty()) {
        val current = Q.poll()
        if (current.time > 10) return

        if (current.rP == holeP) {
            answer = current.time
            return
        }
        //움직이려는 방향에서 더 앞에 있는 구슬을 먼저 움직이면 문제가 발생하지 않음
        for (direction in 0 until 4) {
            when (direction) {
                0 -> {
                    //상 (y값이 더 크면 first)
                    if (current.rP.y > current.bP.y) moveFirstRpSecondBp(current, direction)
                    else moveFirstBpSecondRp(current, direction)
                }
                1 -> {
                    //하 (y값이 더 작으면 first)
                    if (current.rP.y < current.bP.y) moveFirstRpSecondBp(current, direction)
                    else moveFirstBpSecondRp(current, direction)
                }
                2 -> {
                    //좌 (x값이 더 작으면 first)
                    if (current.rP.x < current.bP.x) moveFirstRpSecondBp(current, direction)
                    else moveFirstBpSecondRp(current, direction)
                }
                3 -> {
                    //우 (x값이 더 크면 first)
                    if (current.rP.x > current.bP.x) moveFirstRpSecondBp(current, direction)
                    else moveFirstBpSecondRp(current, direction)
                }
            }
        }
    }
}

private fun moveFirstRpSecondBp(current: Move, direction: Int) {
    val nextRP = getEndPointInCurrentLine(current.rP.y, current.rP.x, direction)
    var nextBP = getEndPointInCurrentLine(current.bP.y, current.bP.x, direction)
    if (board[nextBP.y][nextBP.x] == 'O') return
    if (nextRP == nextBP) {
        nextBP.apply {
            y -= MY[direction]
            x -= MX[direction]
        }
    }
    if (isVisited[nextRP.y][nextRP.x][nextBP.y][nextBP.x]) return
    isVisited[nextRP.y][nextRP.x][nextBP.y][nextBP.x] = true
    Q.add(Move(nextRP, nextBP, current.time + 1))
}

private fun moveFirstBpSecondRp(current: Move, direction: Int) {
    val nextBP = getEndPointInCurrentLine(current.bP.y, current.bP.x, direction)
    var nextRP = getEndPointInCurrentLine(current.rP.y, current.rP.x, direction)
    if (board[nextBP.y][nextBP.x] == 'O') return
    if (nextRP == nextBP) {
        nextRP.apply {
            y -= MY[direction]
            x -= MX[direction]
        }
    }
    if (isVisited[nextRP.y][nextRP.x][nextBP.y][nextBP.x]) return
    isVisited[nextRP.y][nextRP.x][nextBP.y][nextBP.x] = true
    Q.add(Move(nextRP, nextBP, current.time + 1))
}

private fun getEndPointInCurrentLine(y: Int, x: Int, direction: Int): Point {
    var nextRY = y
    var nextRX = x

    while (true) {
        nextRY += MY[direction]
        nextRX += MX[direction]
        if (!isInMap(nextRY, nextRX)) {
            nextRY -= MY[direction]
            nextRX -= MX[direction]
            break
        }
        if (board[nextRY][nextRX] == 'O') return Point(nextRY, nextRX)
    }
    return Point(nextRY, nextRX)
}

private fun isInMap(y: Int, x: Int) =
    board[y][x] != '#'

private data class Move(
    val rP: Point,
    val bP: Point,
    val time: Int,
)

private data class Point(
    var y: Int,
    var x: Int,
)