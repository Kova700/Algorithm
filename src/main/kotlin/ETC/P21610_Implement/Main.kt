package ETC.P21610_Implement

import java.io.FileInputStream
import java.util.*
import kotlin.math.abs

private var N = 0 //격자의 크기 (2 ≤ N ≤ 50)
private var M = 0 //구름 이동 명령 횟수 (1 ≤ M ≤ 100)
private lateinit var waterBoard: Array<IntArray> //바구니에 저장된 물의 양 (0 ≤ waterBoard[r][c] ≤ 100)
private lateinit var isCloud: Array<BooleanArray> //구름 여부 저장
private val MY = arrayOf(0, -1, -1, -1, 0, 1, 1, 1)//좌,좌하,하,우하,우,우상,상,좌상
private val MX = arrayOf(-1, -1, 0, 1, 1, 1, 0, -1)//좌,좌하,하,우하,우,우상,상,좌상
private val diagonalMY = arrayOf(-1, 1, -1, 1) //우하, 우상 , 좌하 , 좌상
private val diagonalMX = arrayOf(1, 1, -1, -1) //우하, 우상 , 좌하 , 좌상
private fun main() {
    //시간 복잡도 : M * move(4 * N^2) =  100 * 4 * 2500 = 백만(충분)
    System.setIn(FileInputStream("src/main/kotlin/ETC/P21610_Implement/input"))
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()
    waterBoard = Array(N + 1) { IntArray(N + 1) }
    isCloud = Array(N + 1) { BooleanArray(N + 1) }
    isCloud[N][1] = true
    isCloud[N][2] = true
    isCloud[N - 1][1] = true
    isCloud[N - 1][2] = true

    for (i in 1..N) {
        st = StringTokenizer(br.readLine())
        for (j in 1..N) {
            waterBoard[i][j] = st.nextToken().toInt()
        }
    }

    repeat(M) {
        //(1 ≤ d ≤ 8), (1 ≤ s ≤ 50)
        val (d, s) = br.readLine().split(" ").map { it.toInt() }
        move(d - 1, s)
    }

    var sum = 0
    for (i in 1..N) {
        for (j in 1..N) {
            sum += waterBoard[i][j]
        }
    }
    println(sum)
}

private fun move(d: Int, s: Int) {
    val Q: Queue<Cloud> = LinkedList()

    //구름 담기
    for (i in 1..N) {
        for (j in 1..N) {
            if (!isCloud[i][j]) continue

            isCloud[i][j] = false
            Q.add(Cloud(i, j))
        }
    }

    //구름 움직이기
    while (Q.isNotEmpty()) {
        val cCloud = Q.poll()
        var nextY = cCloud.y + (MY[d] * s) % N
        when {
            nextY > N -> nextY = if (nextY % N == 0) 1 else nextY % N
            nextY < 1 -> nextY = N - (abs(nextY) % N)
        }
        var nextX = cCloud.x + (MX[d] * s) % N
        when {
            nextX > N -> nextX = if (nextX % N == 0) 1 else nextX % N
            nextX < 1 -> nextX = N - (abs(nextX) % N)
        }
        isCloud[nextY][nextX] = true
        waterBoard[nextY][nextX]++
    }

    //2에서 물이 증가한 칸 (r, c)에 물복사버그 마법을 시전한다.
    for (i in 1..N) {
        for (j in 1..N) {
            if (!isCloud[i][j]) continue

            //경계를 넘어가는 칸은 대각선 방향으로 거리가 1인 칸이 아니다.
            //우하, 우상 , 좌하 , 좌상
            var count = 0
            for (direction in 0 until 4) {
                val nextY = i + diagonalMY[direction]
                val nextX = j + diagonalMX[direction]
                if (!isInMap(nextY, nextX) || waterBoard[nextY][nextX] == 0) continue
                count++
            }
            waterBoard[i][j] += count
        }
    }

    val tempIsCloud = Array(N + 1) { BooleanArray(N + 1) }
    //바구니에 저장된 물의 양이 2 이상인 모든 칸에 구름이 생기고, 물의 양이 2 줄어든다.
    for (i in 1..N) {
        for (j in 1..N) {
            if (waterBoard[i][j] < 2 || isCloud[i][j]) continue

            // 이때 구름이 생기는 칸은 3에서 구름이 사라진 칸이 아니어야 한다.
            waterBoard[i][j] -= 2
            tempIsCloud[i][j] = true
        }
    }

    isCloud = tempIsCloud
}

private fun isInMap(y: Int, x: Int) =
    (y in 1..N) && (x in 1..N)

private data class Cloud(
    val y: Int,
    val x: Int
)