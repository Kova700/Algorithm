package ETC.P23288_Implement_BFS_DICE

import java.io.FileInputStream
import java.util.*

private var N = 0 //세로 (2 ≤ N ≤ 20)
private var M = 0 //가로 (2 ≤ N ≤ 20)
private var K = 0 //이동하는 횟수 (1 ≤ K ≤ 1,000)
private lateinit var board: Array<IntArray>
private val MY = arrayOf(0, 1, 0, -1)//동남서북
private val MX = arrayOf(1, 0, -1, 0)//동남서북
private var direction = 0
private var cY = 1
private var cX = 1
private var dice = arrayOf(1, 2, 3, 5, 4, 6)//위[0],북[1],동[2],남[3],서[4],바닥[5]
private var point = 0
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P23288_Implement_BFS_DICE/input"))
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()
    K = st.nextToken().toInt()
    board = Array(N + 1) { IntArray(M + 1) }

    for (i in 1..N) {
        st = StringTokenizer(br.readLine())
        for (j in 1..M) {
            //0 < board[i][j] < 10 (자연수)
            board[i][j] = st.nextToken().toInt()
        }
    }

    repeat(K) {
        move()
    }
    println(point)
}

private fun move() {

    var nextY = cY + MY[direction]
    var nextX = cX + MX[direction]
    if (!isInMap(nextY, nextX)) {
        direction = (direction + 2) % 4
        nextY = cY + MY[direction]
        nextX = cX + MX[direction]
    }
    changeDice()
    point += getPoint(nextY, nextX)

    when {
        dice[5] > board[nextY][nextX] -> direction = (direction + 1) % 4
        dice[5] < board[nextY][nextX] -> direction = (direction + 3) % 4
    }
    cY = nextY
    cX = nextX
}

private fun changeDice() {
    //위[0],북[1],동[2],남[3],서[4],바닥[5]
    when (direction) {
        0 -> {
            //동
            val temp = dice[4]
            dice[4] = dice[5]
            dice[5] = dice[2]
            dice[2] = dice[0]
            dice[0] = temp
        }

        1 -> {
            //남
            val temp = dice[1]
            dice[1] = dice[5]
            dice[5] = dice[3]
            dice[3] = dice[0]
            dice[0] = temp
        }

        2 -> {
            //서
            val temp = dice[2]
            dice[2] = dice[5]
            dice[5] = dice[4]
            dice[4] = dice[0]
            dice[0] = temp
        }

        3 -> {
            //북
            val temp = dice[3]
            dice[3] = dice[5]
            dice[5] = dice[1]
            dice[1] = dice[0]
            dice[0] = temp
        }
    }
}

private fun getPoint(y: Int, x: Int): Int {
    val Q: Queue<Point> = LinkedList<Point>()
    val isVisited = Array(N + 1) { BooleanArray(M + 1) }
    isVisited[y][x] = true
    Q.add(Point(y, x))
    var sum = 0

    while (Q.isNotEmpty()) {
        val cPoint = Q.poll()
        sum += board[cPoint.y][cPoint.x]

        for (i in 0 until 4) {
            val nextY = cPoint.y + MY[i]
            val nextX = cPoint.x + MX[i]
            if (!isInMap(nextY, nextX) ||
                board[nextY][nextX] != board[y][x] ||
                isVisited[nextY][nextX]
            ) continue

            isVisited[nextY][nextX] = true
            Q.add(Point(nextY, nextX))
        }
    }
    return sum
}

private fun isInMap(y: Int, x: Int) =
    (y in 1..N) && (x in 1..M)

private data class Point(
    val y: Int,
    val x: Int
)