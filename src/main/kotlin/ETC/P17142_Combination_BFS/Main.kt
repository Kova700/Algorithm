package ETC.P17142_Combination_BFS

import java.io.FileInputStream
import java.util.*

private var N = 0 //연구소의 크기 (4 ≤ N ≤ 50)
private var M = 0 //활성 상태로 변경할 바이러스 수 (1 ≤ M ≤ 10)
private val MY = arrayOf(1, -1, 0, 0)//상하좌우
private val MX = arrayOf(0, 0, -1, 1)//상하좌우
private lateinit var board: Array<IntArray>
private var answer = Int.MAX_VALUE
private var zeroCount = 0 // 빈칸 개수
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P17142_Combination_BFS/input"))
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()
    board = Array(N) { IntArray(N) }
    //시간복잡도 : 최대 10C5(252) * 50 * 50 * 4 = 252 * 10000 = 2,520,000 임으로 충분 함

    for (i in 0 until N) {
        st = StringTokenizer(br.readLine())
        for (j in 0 until N) {
            //0은 빈 칸, 1은 벽, 2는 비활성 바이러스의 위치이다.
            //2의 개수는 M보다 크거나 같고, 10보다 작거나 같은 자연수이다.
            board[i][j] = st.nextToken().toInt()
            if (board[i][j] == 0) zeroCount++
        }
    }

    if (zeroCount == 0) {
        println(0)
        return
    }

    for (i in 0 until N) {
        for (j in 0 until N) {
            if (board[i][j] != 2) continue
            board[i][j] = 3
            dfs(i, j, 1)
            board[i][j] = 2
        }
    }

    println(if (answer == Int.MAX_VALUE) -1 else answer)
}

//활성 바이러스 위치 선택
private fun dfs(y: Int, x: Int, count: Int) {
    if (count == M) {
        val tempBoard = Array(N) { i -> board[i].copyOf() }
        //수정된 board를 복사한 tempBoard를 bfs에게 전달
        bfs(tempBoard)
        return
    }
    //비활성 바이러스(2)를 활성 바이러스(3)로 수정
    for (i in y until N) {
        for (j in 0 until N) {
            //if (y == i && j <= x) continue 이거 없으면
            //조합이 아니라 순열이 되어버려서 최악의 경우 문제상 5!(20)배 정도 차이남
            // == 시간 초과가 남
            if (y == i && j <= x) continue
            if (board[i][j] != 2) continue
            board[i][j] = 3
            dfs(i, j, count + 1)
            board[i][j] = 2
        }
    }
}

//바이러스 퍼트리기
private fun bfs(tempBoard: Array<IntArray>) {
    var tempZeroCount = zeroCount
    val Q: Queue<Point> = LinkedList()

    //활성 바이러스 Q에 집어넣기
    for (i in 0 until N) {
        for (j in 0 until N) {
            if (tempBoard[i][j] != 3) continue
            Q.add(Point(i, j, 0, 3))
        }
    }

    //BFS 시작
    var finishTime = 0
    while (Q.isNotEmpty()) {
        val nowPoint = Q.poll()

        if (nowPoint.type == 0) {
            tempZeroCount--
            //맵에 0이 사라지면 큐에 노드가 아직 남아있더라도 종료,
            if (tempZeroCount <= 0) {
                finishTime = nowPoint.time
                break
            }
        }

        for (i in 0 until 4) {
            val nextY = nowPoint.y + MY[i]
            val nextX = nowPoint.x + MX[i]
            if (!isInMap(nextY, nextX) || tempBoard[nextY][nextX] == 3
                || tempBoard[nextY][nextX] == 1
            ) continue
            Q.add(Point(nextY, nextX, nowPoint.time + 1, tempBoard[nextY][nextX]))
            tempBoard[nextY][nextX] = 3
        }
    }

    //큐를 전부 비웠는데 finishTime이 갱신되어있지 않다면, 바이러스를 전부 퍼트리지 못한 것
    if (finishTime == 0) return
    answer = minOf(answer, finishTime)
}

private fun isInMap(y: Int, x: Int) =
    (y in 0 until N) && (x in 0 until N)

private data class Point(
    val y: Int,
    val x: Int,
    val time: Int,
    val type: Int
)