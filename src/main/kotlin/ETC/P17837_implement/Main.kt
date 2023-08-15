package ETC.P17837_implement

import java.io.FileInputStream
import java.util.*

private val MY = arrayOf(0, 0, -1, 1)//우좌하상
private val MX = arrayOf(1, -1, 0, 0)//우좌하상
private var N = 0 //체스판의 크기 (4 ≤ N ≤ 12)
private var K = 0 //말의 개수 (4 ≤ K ≤ 10)
private lateinit var colorBoard: Array<IntArray> //보드 색상 저장
private lateinit var malBoard: Array<Array<MutableList<Int>>> //같은 위치에 있는 말 번호 저장
private lateinit var mals: Array<Mal?> //말 객체 저장
private var count = 1
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P17837_implement/input"))
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    K = st.nextToken().toInt()

    colorBoard = Array(N + 1) { IntArray(N + 1) }
    malBoard = Array(N + 1) { Array(N + 1) { mutableListOf() } }
    mals = Array(K + 1) { null }
    for (i in 1..N) {
        //0은 흰색, 1은 빨간색, 2는 파란색
        st = StringTokenizer(br.readLine())
        for (j in 1..N) {
            colorBoard[i][j] = st.nextToken().toInt()
        }
    }

    for (i in 1..K) {
        // 이동 방향은 4보다 작거나 같은 자연수이고 1부터 순서대로 →, ←, ↑, ↓의 의미를 갖는다.
        val (row, col, direction) = br.readLine().split(" ").map { it.toInt() }
        malBoard[row][col].add(i)
        mals[i] = Mal(i, direction - 1, row, col)
    }

    move()
    println(count)
}

private fun move() {
    while (true) {
        if (count > 1000) {
            count = -1
            return
        }

        for (i in 1..K) {
            if (mals[i] == null) continue

            val cMal = mals[i]!!
            val nextY = cMal.y + MY[cMal.direction]
            val nextX = cMal.x + MX[cMal.direction]

            if (!isInMap(nextY, nextX)) {
                val finishFlag = blue(cMal)
                if (finishFlag) return
                continue
            }
            when (colorBoard[nextY][nextX]) {
                0 -> {
                    val finishFlag = white(cMal, nextY, nextX)
                    if (finishFlag) return
                }

                1 -> {
                    val finishFlag = red(cMal, nextY, nextX)
                    if (finishFlag) return
                }

                2 -> {
                    val finishFlag = blue(cMal)
                    if (finishFlag) return
                }
            }
        }
        count++
    }
}

private fun white(cMal: Mal, nextY: Int, nextX: Int): Boolean {
    val cY = cMal.y
    val cX = cMal.x

    //다 옮기는게 아니라 현재 말 위에것들만 옮겨야함
    var targetIndex = 0
    for (i in 0..malBoard[cY][cX].lastIndex) {
        if (cMal.num != malBoard[cY][cX][i]) continue
        targetIndex = i
        break
    }

    for (k in targetIndex..malBoard[cY][cX].lastIndex) {
        val malNum = malBoard[cY][cX][k]

        mals[malNum].apply {
            this?.y = nextY
            this?.x = nextX
        }
        malBoard[nextY][nextX].add(malNum)
    }
    malBoard[cY][cX] = malBoard[cY][cX].slice(0 until targetIndex).toMutableList()
    if (malBoard[nextY][nextX].size >= 4) return true

    return false
}

private fun red(cMal: Mal, nextY: Int, nextX: Int): Boolean {
    val cY = cMal.y
    val cX = cMal.x

    //다 옮기는게 아니라 현재 말 위에것들만 옮겨야함
    var targetIndex = 0
    for (i in 0..malBoard[cY][cX].lastIndex) {
        if (cMal.num != malBoard[cY][cX][i]) continue
        targetIndex = i
        break
    }

    for (k in malBoard[cY][cX].lastIndex downTo targetIndex) {
        val malNum = malBoard[cY][cX][k]

        mals[malNum].apply {
            this?.y = nextY
            this?.x = nextX
        }
        malBoard[nextY][nextX].add(malNum)
    }
    malBoard[cY][cX] = malBoard[cY][cX].slice(0 until targetIndex).toMutableList()
    if (malBoard[nextY][nextX].size >= 4) return true
    return false
}

private fun blue(cMal: Mal): Boolean {
    val cDirection = cMal.direction
    val nextDirection =
        if (cDirection % 2 == 0) cDirection + 1
        else cDirection - 1

    mals[cMal.num].apply {
        this?.direction = nextDirection
    }
    val nextY2 = cMal.y + MY[nextDirection]
    val nextX2 = cMal.x + MX[nextDirection]
    if (!isInMap(nextY2, nextX2)) return false

    when (colorBoard[nextY2][nextX2]) {
        0 -> {
            val finishFlag = white(cMal, nextY2, nextX2)
            if (finishFlag) return true
        }

        1 -> {
            val finishFlag = red(cMal, nextY2, nextX2)
            if (finishFlag) return true
        }
    }
    return false
}

private fun isInMap(y: Int, x: Int) =
    (y in 1..N) && (x in 1..N)

private data class Mal(
    val num: Int,
    var direction: Int,
    var y: Int,
    var x: Int
)