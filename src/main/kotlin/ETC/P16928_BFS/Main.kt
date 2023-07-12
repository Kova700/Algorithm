package ETC.P16928_BFS

import java.io.FileInputStream
import java.util.*

private val board = IntArray(101)
private var N = 0 // 사다리의 수 (1 ≤ N ≤ 15)
private var M = 0 // 뱁의 수 (1 ≤ M ≤ 15)
private var answer = Int.MAX_VALUE
private val isVisited = BooleanArray(101)

//해당 문제를 DFS혹은 BruteForce로 풀려고하면 한 점에서 1~6칸을 넘어가는 경우의 수가 중복 방지 처리가 안되어서,
// 너무 많은 경우의 수가 생김으로 메모리초과 혹은 시간 초과가나옴으로 BFS 혹은 Dijkstra로 푸는 것이 적합하다.
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
            return
        }
        //dfs는 왜 안되는지도

        for (i in 1..6) {
            var nextNum = current.num + i
            if (!isInMap(nextNum)) break

            //여기서 사다리(혹은 뱀)를 타지 않으면 Q에서 각 depth에 맞지 않게 순서가 밀려서
            //주사위를 더 많이 굴렸음에도 Q 앞자리에 있다는 Point들에게 isVisited를 뺏기는 경우가 생긴다.
            //그래서 가장 빨리 100에 도착하는 경우의 수를 구할 수가 없음
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