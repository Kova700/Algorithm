package ETC.P20057_Implement

import java.io.FileInputStream
import java.util.*

private var N = 0 //격자의 크기 (3 ≤ N ≤ 499) N은 홀수
private val MY = arrayOf(0, 1, 0, -1)//좌,상,우,하
private val MX = arrayOf(-1, 0, 1, 0)//좌,상,우,하
private lateinit var board: Array<IntArray> //모래의 양 지도 0 ≤ board[r][c] ≤ 1,000
private var outSand = 0
private var alphaSand = 0
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P20057_Implement/input"))
    val br = System.`in`.bufferedReader()
    N = br.readLine().toInt()
    board = Array(N + 1) { IntArray(N + 1) }
    for (i in 1..N) {
        val st = StringTokenizer(br.readLine())
        for (j in 1..N) {
            board[i][j] = st.nextToken().toInt()
        }
    }
    tornado()
    println(outSand)
}

private fun tornado() {
    var cY = N / 2 + 1
    var cX = N / 2 + 1

    //매번 이동시마다 방향이 좌,상,우,하 순으로 변경됨
    //특정 거리만큼 2번 이동 후 거리가 1씩 증가함
    var distance = 1
    var direction = 0
    loop@ while (true) {

        for (i in 0 until 2) {
            for (j in 0 until distance) {
                cY += MY[direction % 4]
                cX += MX[direction % 4]
                spreadSand(cY, cX, direction % 4)
                if (cY == 1 && cX == 1) break@loop
            }
            direction++
        }
        distance++
    }

}

//좌상우하
//오른쪽으로 90도 -> direction + 1
//그냥 뒤로 (180도) -> direction + 2
//왼쪽으로 90도 -> direction + 3
private fun spreadSand(cY: Int, cX: Int, direction: Int) {
    alphaSand = board[cY][cX]

    //다른땅 모래 흩뿌리기
    val up7PY = cY + MY[(direction + 1) % 4]
    val up7PX = cX + MX[(direction + 1) % 4]
    calculateSpreadSand(cY, cX, up7PY, up7PX, 0.07F)

    val up2PY = cY + (MY[(direction + 1) % 4]) * 2
    val up2PX = cX + (MX[(direction + 1) % 4]) * 2
    calculateSpreadSand(cY, cX, up2PY, up2PX, 0.02F)

    val up1PY = cY + MY[(direction + 1) % 4] + MY[(direction + 2) % 4]
    val up1PX = cX + MX[(direction + 1) % 4] + MX[(direction + 2) % 4]
    calculateSpreadSand(cY, cX, up1PY, up1PX, 0.01F)

    val up10PY = cY + MY[(direction + 1) % 4] + MY[direction]
    val up10PX = cX + MX[(direction + 1) % 4] + MX[direction]
    calculateSpreadSand(cY, cX, up10PY, up10PX, 0.1F)

    val m5PY = cY + MY[direction] * 2
    val m5PX = cX + MX[direction] * 2
    calculateSpreadSand(cY, cX, m5PY, m5PX, 0.05F)

    val d10PY = cY + MY[(direction + 3) % 4] + MY[direction]
    val d10PX = cX + MX[(direction + 3) % 4] + MX[direction]
    calculateSpreadSand(cY, cX, d10PY, d10PX, 0.1F)

    val d7PY = cY + MY[(direction + 3) % 4]
    val d7PX = cX + MX[(direction + 3) % 4]
    calculateSpreadSand(cY, cX, d7PY, d7PX, 0.07F)

    val d2PY = cY + MY[(direction + 3) % 4] * 2
    val d2PX = cX + MX[(direction + 3) % 4] * 2
    calculateSpreadSand(cY, cX, d2PY, d2PX, 0.02F)

    val d1PY = cY + MY[(direction + 3) % 4] + MY[(direction + 2) % 4]
    val d1PX = cX + MX[(direction + 3) % 4] + MX[(direction + 2) % 4]
    calculateSpreadSand(cY, cX, d1PY, d1PX, 0.01F)

    //alphaSand를 계산해서 다음칸에 넣어주기
    if (isInMap(cY + MY[direction], cX + MX[direction])) {
        board[cY + MY[direction]][cX + MX[direction]] += alphaSand
    } else {
        outSand += alphaSand
    }

    board[cY][cX] = 0
}

private fun calculateSpreadSand(cY: Int, cX: Int, targetY: Int, targetX: Int, rate: Float) {
    if (isInMap(targetY, targetX)) {
        board[targetY][targetX] += (board[cY][cX] * rate).toInt()
    } else {
        outSand += (board[cY][cX] * rate).toInt()
    }
    alphaSand -= (board[cY][cX] * rate).toInt()

}

private fun isInMap(y: Int, x: Int) =
    (y in 1..N) && (x in 1..N)