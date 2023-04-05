package ETC.P2636_BFS_Implement

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

private var R = 0 //세로 (양의 정수, 최대 100)
private var C = 0 //가로 (양의 정수, 최대 100)
private var time = 0
private var lastCheeseCount = 0
private var cheeseCountList = mutableListOf<Int>()
private val MY = arrayOf(0, -1, 0, 1) //우하좌상
private val MX = arrayOf(1, 0, -1, 0) //우하좌상
private lateinit var cheeseMap: Array<Array<String>>

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2636_BFS_Implement/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    br.readLine().split(" ").map { it.toInt() }.apply {
        R = this[0]
        C = this[1]
    }
    cheeseMap = Array(R) { arrayOf() }
    repeat(R) {
        cheeseMap[it] = br.readLine().split(" ").toTypedArray()
    }
    while (true) {
        checkOutSpace()
        meltCheese()
        if (lastCheeseCount == 0) break
        time++
        cheeseCountList.add(lastCheeseCount)
        lastCheeseCount = 0
    }
    println(time)
    println(cheeseCountList.last())
}

//안쪽 공간과 바깥쪽 공간을 구분해야함
private fun checkOutSpace() {
    val Q: Queue<Point> = LinkedList()
    val isVisited = Array(R) { BooleanArray(C) { false } }
    Q.add(Point(0, 0))
    while (Q.isNotEmpty()) {
        val point = Q.poll()
        cheeseMap[point.y][point.x] = "2"

        for (i in 0 until 4) {
            val checkRow = point.y + MY[i]
            val checkCol = point.x + MX[i]
            if (isInMap(checkRow, checkCol) &&
                (cheeseMap[checkRow][checkCol] != "1") &&
                !isVisited[checkRow][checkCol]
            ) {
                isVisited[checkRow][checkCol] = true
                Q.add(Point(checkRow, checkCol))
            }
        }
    }
}

private fun meltCheese() {
    for (r in 0 until R) {
        for (c in 0 until C) {
            if (cheeseMap[r][c] != "1") continue

            lastCheeseCount++
            //치즈 칸 주변 탐색
            for (i in 0 until 4) {
                val checkRow = r + MY[i]
                val checkCol = c + MX[i]
                //외각을 맞댄 치즈칸이라면 녹임
                if (isInMap(checkRow, checkCol) && cheeseMap[checkRow][checkCol] == "2") {
                    cheeseMap[r][c] = "0"
                    break
                }
            }
        }
    }
}

private fun isInMap(y: Int, x: Int): Boolean =
    (y in 0 until R) && (x in 0 until C)

private data class Point(
    val y: Int,
    val x: Int
)