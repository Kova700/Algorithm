package ETC.P20056_Implement

import java.io.FileInputStream
import java.util.*
import kotlin.math.abs

private var N = 0// 격자 크기 (4 ≤ N ≤ 50)
private var M = 0// 파이어볼의 개수 (0 ≤ M ≤ N^2)
private var K = 0// 이동 명령 횟수(1 ≤ K ≤ 1,000)
private val MY = arrayOf(-1, -1, 0, 1, 1, 1, 0, -1)//하,우하,우,우상,상,좌상,좌,좌하
private val MX = arrayOf(0, 1, 1, 1, 0, -1, -1, -1)//하,우하,우,우상,상,좌상,좌,좌하
private lateinit var board: Array<Array<MutableList<FireBall>>>
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P20056_Implement/input"))
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()
    K = st.nextToken().toInt()
    board = Array(N + 1) { Array(N + 1) { mutableListOf() } }
    //시간 복잡도 = K * [move(N * N) + combineAndSplit(N * N * 4)] = 5KN^2 = 대략 12,500,000 (충분)
    repeat(M) {
        //서로 다른 두 파이어볼의 위치가 같은 경우는 입력으로 주어지지 않는다.
        // m 질량 (1 ≤ m ≤ 1,000)
        // s 속력 (1 ≤ s ≤ 1,000)
        // d 방향 (0 ≤ d ≤ 7)
        val (r, c, m, s, d) = br.readLine().split(" ").map { it.toInt() }
        board[r][c].add(FireBall(m, s, d))
    }
    for (i in 0 until K) {
        move()
        combineAndSplit()
    }

    var sum = 0
    for (r in 1..N) {
        for (c in 1..N) {
            if (board[r][c].size == 0) continue
            for (cFireball in board[r][c]) {
                sum += cFireball.mass
            }
        }
    }
    println(sum)

}

private fun move() {
    val tempBoard = Array(N + 1) { Array(N + 1) { mutableListOf<FireBall>() } }

    for (r in 1..N) {
        for (c in 1..N) {
            if (board[r][c].isEmpty()) continue

            for (cFireball in board[r][c]) {
                var nextY = r + MY[cFireball.direction] * (cFireball.speed % N)
                when {
                    nextY > N -> nextY = if (nextY % N == 0) 1 else nextY % N
                    nextY < 1 -> nextY = N - (abs(nextY) % N)
                }
                var nextX = c + MX[cFireball.direction] * (cFireball.speed % N)
                when {
                    nextX > N -> nextX = if (nextX % N == 0) 1 else nextX % N
                    nextX < 1 -> nextX = N - (abs(nextX) % N)
                }
                tempBoard[nextY][nextX].add(cFireball)
            }
        }
    }

    for (r in 1..N) {
        for (c in 1..N) {
            board[r][c] = tempBoard[r][c].toMutableList()
        }
    }
}

private fun combineAndSplit() {
    for (r in 1..N) {
        for (c in 1..N) {
            if (board[r][c].size < 2) continue

            var cMass = 0
            var cSpeed = 0
            var isAllOdd = true
            var isAllEven = true
            for (cFireball in board[r][c]) {
                cMass += cFireball.mass
                cSpeed += cFireball.speed
                isAllOdd = isAllOdd && cFireball.direction % 2 != 0
                isAllEven = isAllEven && cFireball.direction % 2 == 0
            }

            if (cMass / 5 == 0) {
                board[r][c].clear()
                continue
            }

            val count = board[r][c].size

            val direction = if (isAllOdd || isAllEven) 0 else 1
            val splitedFireBalls = MutableList(4) { FireBall(cMass / 5, cSpeed / count, direction + 2 * it) }
            board[r][c] = splitedFireBalls
        }
    }
}

private data class FireBall(
    val mass: Int,
    val speed: Int,
    val direction: Int
)