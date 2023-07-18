package ETC.P15685_implement

import java.io.FileInputStream

private var N = 0 // 드래곤 커브 개수(1 ≤ N ≤ 20)
private val isCurve = Array(101) { BooleanArray(101) } //드래곤 커브 체크
private val MY = arrayOf(0, -1, 0, 1)//우,하,좌,상
private val MX = arrayOf(1, 0, -1, 0)//우,하,좌,상
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P15685_implement/input"))
    val br = System.`in`.bufferedReader()
    N = br.readLine().toInt()
    repeat(N) {
        // x와 y는 드래곤 커브의 시작 점, d는 시작 방향, g는 세대
        //0: x좌표가 증가하는 방향 (→)
        //1: y좌표가 감소하는 방향 (↑)
        //2: x좌표가 감소하는 방향 (←)
        //3: y좌표가 증가하는 방향 (↓)
        //(0 ≤ x, y ≤ 100, 0 ≤ d ≤ 3, 0 ≤ g ≤ 10)
        //입력으로 주어지는 드래곤 커브는 격자 밖으로 벗어나지 않는다.
        //드래곤 커브는 서로 겹칠 수 있다.
        val (x, y, d, g) = br.readLine().split(" ").map { it.toInt() }
        drawCurve(x, y, d, g)
    }

    countSquare()
}

private fun drawCurve(x: Int, y: Int, d: Int, g: Int) {
    isCurve[y][x] = true
    val directionHistory = mutableListOf<Int>(d)
    var currentY = y + MY[d]
    var currentX = x + MX[d]
    isCurve[currentY][currentX] = true
    var currentG = 0
    while (currentG < g) {
        //지금까지 이어온 선의 방향을 하나씩 시계 반대 방향으로 90도 회전하고 붙이면됨(== 방향 인덱스 +1)
        val temp = mutableListOf<Int>()
        for (i in directionHistory.lastIndex downTo 0) {
            val newDirection =
                if (directionHistory[i] + 1 > 3) directionHistory[i] + 1 - 4 else directionHistory[i] + 1
            currentY += MY[newDirection]
            currentX += MX[newDirection]
            isCurve[currentY][currentX] = true
            temp.add(newDirection)
        }
        directionHistory.addAll(temp)
        currentG++
    }
}

private fun countSquare() {
    var count = 0
    for (i in 0 until 100) {
        for (j in 0 until 100) {
            if (!isCurve[i][j]) continue
            if (isCurve[i][j] && isCurve[i][j + 1] &&
                isCurve[i + 1][j + 1] && isCurve[i + 1][j]
            ) {
                count++
            }
        }
    }
    println(count)
}