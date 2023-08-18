package ETC.P17822_implement_BFS

import java.io.FileInputStream
import java.util.*

private var N = 0// 원판의 개수 (2 ≤ N ≤ 50)
private var M = 0// 원판에 적힌 정수의 개수 (2 ≤ M ≤ 50)
private var T = 0// 원판 회전 횟수(1 ≤ T ≤ 50)
private lateinit var board: Array<IntArray>
private var isFindNearNum = false
private val MY = arrayOf(1, -1, 0, 0)//상하좌우
private val MX = arrayOf(0, 0, -1, 1)//상하좌우
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P17822/input"))
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()
    T = st.nextToken().toInt()

    board = Array(N + 1) { IntArray(M + 1) }
    for (i in 1..N) {
        st = StringTokenizer(br.readLine())
        for (j in 1..M) {
            //1 ≤ 원판에 적힌 수 ≤ 1,000
            board[i][j] = st.nextToken().toInt()
        }
    }
    //시간 복잡도 : T * ( rotate(N*k*M) + deleteNearNum( N*M*bfs( 1 )))
    //여기서 bfs의 시간복잡도는 일반적인 bfs공식처럼 노드 수 + 간선 수 가아니다 (모든 점은 1번씩 가보고 더 이상 탐색하지 않음으로)
    // = T * [kNM + NM] = 50 * 2500(50 + 1) = 125,000(51) = 6,375,000 (충분)
    repeat(T) {
        //번호가 x의 배수인 원판을 d방향으로 k칸 회전시킨다. (d가 0인 경우는 시계 방향, 1인 경우는 반시계 방향이다.)
        //(2 ≤ x ≤ N), (0 ≤ d ≤ 1), (1 ≤ k < M)
        val (x, d, k) = br.readLine().split(" ").map { it.toInt() }
        rotate(x, d, k)
        deleteNearNum()
    }

    var sum = 0
    for (i in 1..N) {
        sum += board[i].sum()
    }
    println(sum)
}

private fun rotate(x: Int, d: Int, k: Int) {

    for (diskNum in 1..N) {
        if (diskNum % x != 0) continue

        for (i in 0 until k) {
            when (d) {
                0 -> {
                    //시계방향
                    val temp = board[diskNum][M]
                    for (j in M downTo 2) {
                        board[diskNum][j] = board[diskNum][j - 1]
                    }
                    board[diskNum][1] = temp
                }

                1 -> {
                    //반 시계방향
                    val temp = board[diskNum][1]
                    for (j in 1 until M) {
                        board[diskNum][j] = board[diskNum][j + 1]
                    }
                    board[diskNum][M] = temp
                }
            }
        }

    }
}

private fun deleteNearNum() {
    isFindNearNum = false
    for (diskNum in 1..N) {
        for (i in 1..M) {
            if (board[diskNum][i] == 0) continue
            bfs(diskNum, i, board[diskNum][i])
        }
    }

    //그러한 수가 없는 경우
    if (isFindNearNum) return
    var count = 0
    var sum = 0.0
    for (diskNum in 1..N) {
        for (i in 1..M) {
            if (board[diskNum][i] == 0) continue
            count++
            sum += board[diskNum][i]
        }
    }
    val average = sum / count
    for (diskNum in 1..N) {
        for (i in 1..M) {
            when {
                board[diskNum][i] == 0 -> continue
                board[diskNum][i] > average -> board[diskNum][i]--
                board[diskNum][i] < average -> board[diskNum][i]++
            }
        }
    }
}

private fun bfs(diskNum: Int, numIndex: Int, targetNum: Int) {
    val Q: Queue<Point> = LinkedList()
    Q.add(Point(diskNum, numIndex))
    while (Q.isNotEmpty()) {
        val cPoint = Q.poll()
        for (direction in 0 until 4) {
            val nextY = cPoint.y + MY[direction]
            val nextX = when {
                cPoint.x + MX[direction] < 1 -> M
                cPoint.x + MX[direction] > M -> 1
                else -> cPoint.x + MX[direction]
            }
            if (!isInMap(nextY) || board[nextY][nextX] != targetNum) continue

            isFindNearNum = true
            board[nextY][nextX] = 0
            board[cPoint.y][cPoint.x] = 0
            Q.add(Point(nextY, nextX))
        }
    }
}

private fun isInMap(x: Int) = x in 1..N

private data class Point(
    val y: Int,
    val x: Int
)