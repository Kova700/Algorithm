package ETC.P17143_Implement_direction_change_very_hard

import java.io.FileInputStream
import java.util.*
import kotlin.math.abs

private var R = 0 //세로 (2 ≤ R ≤ 100)
private var C = 0 //가로 (2 ≤ C ≤ 100)
private var M = 0 //상어의 수(0 ≤ M ≤ R×C)
private lateinit var board: Array<Array<Shark?>>
private lateinit var temp: Array<Array<Shark?>>
private val MY = arrayOf(-1, 1, 0, 0)//위,아래,오른,왼
private val MX = arrayOf(0, 0, 1, -1)//위,아래,오른,왼
private var fisherIndex = 1
private var answer = 0
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P17143_Implement_direction_change_very_hard/input"))
    val br = System.`in`.bufferedReader()
    val st = StringTokenizer(br.readLine())
    R = st.nextToken().toInt()
    C = st.nextToken().toInt()
    M = st.nextToken().toInt()
    board = Array(R + 1) { Array(C + 1) { null } }
    repeat(M) {
        //입력
        //(1 ≤ r ≤ R, 1 ≤ c ≤ C, 0 ≤ s ≤ 1000, 1 ≤ d ≤ 4, 1 ≤ z ≤ 10000)
        //(r, c)는 상어의 위치,
        // s는 속력,
        // d는 이동 방향,
        // z는 크기이다.

        // d가 1인 경우는 위, 2인 경우는 아래, 3인 경우는 오른쪽, 4인 경우는 왼쪽을 의미한다.
        //두 상어가 같은 크기를 갖는 경우는 없고, 하나의 칸에 둘 이상의 상어가 있는 경우는 없다.
        val (r, c, s, d, z) = br.readLine().split(" ").map { it.toInt() }
        board[r][c] = Shark(s, d - 1, z)
    }

    while (fisherIndex <= C) {
        //낚시왕과 가장 가까운 상어를 잡는다 ( y == 1 에 가장 가까운)
        for (i in 1..R) {
            if (board[i][fisherIndex] == null) continue
            answer += board[i][fisherIndex]!!.size
            board[i][fisherIndex] = null
            break
        }

        temp = Array(R + 1) { Array<Shark?>(C + 1) { null } }
        //상어가 이동한다.
        for (i in 1..R) {
            for (j in 1..C) {
                if (board[i][j] == null) continue
                moveShark(i, j)
            }
        }
        board = temp
        fisherIndex++
    }

    println(answer)
}

private fun moveShark(y: Int, x: Int) {
    val currentShark = board[y][x]!!
    var nY = y
    var nX = x

    //같은 상황이 되기 위한 거리 = 남은거리 * 2 + 뒤에 남은 거리 * 2
    var distanceSamePeriod = 0
    when (currentShark.direction) {
        0 -> distanceSamePeriod = (y - 1) * 2 + (R - y) * 2 //위
        1 -> distanceSamePeriod = (R - y) * 2 + (y - 1) * 2 //아래
        2 -> distanceSamePeriod = (C - x) * 2 + (x - 1) * 2 //오른
        3 -> distanceSamePeriod = (x - 1) * 2 + (C - x) * 2 //왼
    }
    var remainDistance = currentShark.speed % distanceSamePeriod //움직여야할 거리
    //칸을 넘는 경우 방향 전환
    while (remainDistance != 0) {
        when (currentShark.direction) {

            0 -> {
                val cY = nY
                nY += MY[currentShark.direction] * remainDistance
                if (nY < 1) {
                    remainDistance -= (cY - 1)
                    nY = 1
                    currentShark.direction = 1
                } else {
                    remainDistance -= abs(MY[currentShark.direction] * remainDistance)
                }
            }

            1 -> {
                val cY = nY
                nY += MY[currentShark.direction] * remainDistance
                if (nY > R) {
                    remainDistance -= (R - cY)
                    nY = R
                    currentShark.direction = 0
                } else {
                    remainDistance -= abs(MY[currentShark.direction] * remainDistance)
                }
            }

            2 -> {
                val cX = nX
                nX += MX[currentShark.direction] * remainDistance
                if (nX > C) {
                    remainDistance -= (C - cX)
                    nX = C
                    currentShark.direction = 3
                } else {
                    remainDistance -= abs(MX[currentShark.direction] * remainDistance)
                }
            }

            3 -> {
                val cX = nX
                nX += MX[currentShark.direction] * remainDistance
                if (nX < 1) {
                    remainDistance -= (cX - 1)
                    nX = 1
                    currentShark.direction = 2
                } else {
                    remainDistance -= abs(MX[currentShark.direction] * remainDistance)
                }
            }
        }
    }
    if (temp[nY][nX] == null) {
        temp[nY][nX] = currentShark
        return
    }

    if (temp[nY][nX] != null && (temp[nY][nX]!!.size < currentShark.size)) {
        //크기가 가장 큰 상어가 나머지 상어를 모두 잡아먹는다.
        temp[nY][nX] = currentShark
    }
}

data class Shark(
    val speed: Int,
    var direction: Int,
    val size: Int,
)