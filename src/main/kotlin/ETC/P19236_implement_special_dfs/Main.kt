package ETC.P19236_implement_special_dfs

import java.io.FileInputStream
import java.util.*

//이번 문제에서는 평소와 달리 x축 y축 바뀜 감안하고 풀어야함
private val MX = arrayOf(-1, -1, 0, 1, 1, 1, 0, -1)//하/ 좌하/ 좌/ 좌상/ 상/ 우상/ 우/ 우하
private val MY = arrayOf(0, -1, -1, -1, 0, 1, 1, 1)//하/ 좌하/ 좌/ 좌상/ 상/ 우상/ 우/ 우하
private var board = Array(4) { Array<Point?>(4) { null } }
private var ateNumCount = 0
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P19236_implement_special_dfs/input"))
    val br = System.`in`.bufferedReader()
    val numPositions = Array<Int?>(17 + 1) { null } //각 번호별로 현재 위치기억

    for (i in 0 until 4) {
        var st = StringTokenizer(br.readLine())

        for (j in 0 until 4) {
            var num = st.nextToken().toInt() //16보다 작거나 같은 자연수
            val direction = st.nextToken().toInt() - 1//8보다 작거나 같은 자연수
            if (i == 0 && j == 0) {
                ateNumCount += num
                numPositions[num] = null
                numPositions[17] = 0
                board[i][j] = Point(direction, 17)
                continue
            }
            board[i][j] = Point(direction, num)
            numPositions[num] = getPointNumFromPosition(i, j)
        }
    }

    dfs(ateNumCount, Array(4) { i -> board[i].copyOf() }, numPositions)
    println(ateNumCount)
}

private fun dfs(ateSum: Int, tempBoard: Array<Array<Point?>>, numPositions: Array<Int?>) {
    ateNumCount = maxOf(ateNumCount, ateSum)

    //물고기 위치 옮기기
    for (num in 1..16) {
        numPositions[num] ?: continue
        val cX = numPositions[num]!! / 4
        val cY = numPositions[num]!! % 4
        val cPoint = tempBoard[cX][cY]!!

        for (i in 0 until 8) {
            val nX = cX + MX[(cPoint.direction + i) % 8]
            val nY = cY + MY[(cPoint.direction + i) % 8]
            if (!isInMap(nX, nY) || tempBoard[nX][nY]?.num == 17) continue

            if (tempBoard[nX][nY] == null) {
                tempBoard[nX][nY] = cPoint.copy(direction = (cPoint.direction + i) % 8)
                numPositions[num] = getPointNumFromPosition(nX, nY)
                tempBoard[cX][cY] = null
                break
            }

            //자리 바꾸기
            val temp = tempBoard[nX][nY]!!
            tempBoard[nX][nY] = cPoint.copy(direction = (cPoint.direction + i) % 8)
            numPositions[num] = getPointNumFromPosition(nX, nY)
            tempBoard[cX][cY] = temp
            numPositions[temp.num] = getPointNumFromPosition(cX, cY)
            break
        }
    }

    //상어 옮기기
    val cX = numPositions[17]!! / 4
    val cY = numPositions[17]!! % 4
    val cPoint = tempBoard[cX][cY]!!

    for (i in 1 until 4) {
        val nX = cX + MX[cPoint.direction] * i
        val nY = cY + MY[cPoint.direction] * i
        //상어가 이동할 칸이 없다면 종료
        if (!isInMap(nX, nY) || tempBoard[nX][nY] == null) continue

        val targetFish = tempBoard[nX][nY]!!

        tempBoard[cX][cY] = null
        numPositions[targetFish.num] = null
        tempBoard[nX][nY] = Point(targetFish.direction, 17)
        numPositions[17] = getPointNumFromPosition(nX, nY)

        //현재 배열 복사해서 DFS에게 전달
        dfs(ateSum + targetFish.num, Array(4) { z -> tempBoard[z].copyOf() }, numPositions.copyOf())

        numPositions[17] = getPointNumFromPosition(cX, cY)
        tempBoard[nX][nY] = targetFish
        numPositions[targetFish.num] = getPointNumFromPosition(nX, nY)
    }
}

private fun isInMap(x: Int, y: Int) = (x in 0 until 4) && (y in 0 until 4)

private fun getPointNumFromPosition(x: Int, y: Int) = 4 * x + y

private data class Point(
    val direction: Int,
    val num: Int //상어(17), 물고기(번호)
)