package ETC.P16928_BFS

import java.io.FileInputStream
import java.util.*

private val board = IntArray(101)
private var N = 0 // 사다리의 수 (1 ≤ N ≤ 15)
private var M = 0 // 뱁의 수 (1 ≤ M ≤ 15)
private var answer = Int.MAX_VALUE
private val isVisited = BooleanArray(101)

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P16928_BFS/input"))
    val br = System.`in`.bufferedReader()
    val st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()

    repeat(N) {
        //사다리의 정보를 의미하는 x, y (x < y)
        //x번 칸에 도착하면, y번 칸으로 이동한다는 의미
        val (x, y) = br.readLine().split(" ").map { it.toInt() }
        board[x] = y //이동해야하는 칸 번호
    }
    repeat(M) {
        //뱀의 정보를 의미하는 u, v (u > v)
        //u번 칸에 도착하면, v번 칸으로 이동한다는 의미이다.
        val (u, v) = br.readLine().split(" ").map { it.toInt() }
        board[u] = v //이동해야하는 칸 번호
    }
    bfs()
    println(answer)
}

private fun bfs() {
    val Q: Queue<Point> = LinkedList()
    Q.add(Point(1, 0))
    isVisited[1] = true

    while (Q.isNotEmpty()) {
        val current = Q.poll()

        if (current.num == 100) {
            answer = minOf(answer, current.count)
            continue
        }

        for (i in 1..6) {
            var nextNum = current.num + i
            if (!isInMap(nextNum)) break

            if (board[nextNum] != 0) nextNum = board[nextNum]
            if (isVisited[nextNum]) continue
            isVisited[nextNum] = true
            Q.add(Point(nextNum, current.count + 1))
        }
    }
}

private data class Point(
    val num: Int,
    val count: Int
)

private fun isInMap(num: Int) = num in 1..100