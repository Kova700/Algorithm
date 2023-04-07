package ETC.P21608_PQ

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.math.pow

private val MY = arrayOf(0, -1, 0, 1)//우하좌상
private val MX = arrayOf(1, 0, -1, 0)//우하좌상
private var N = 0 //교실의 가로,세로 길이 (3 ≤ N ≤ 20)
private lateinit var seats: Array<IntArray>
private lateinit var dataList: Array<IntArray>

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P21608_PQ/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    seats = Array(N) { IntArray(N) }
    dataList = Array(N * N) { intArrayOf() }

    //좌석에 조건에 맞게 앉히기
    repeat(N * N) { i ->
        dataList[i] = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }

    for (intArray in dataList) {
        sitChild(intArray)
    }

    dataList.sortBy { it[0] }
    println(getSatisfactionPoint())
}

private fun getSatisfactionPoint(): Int {
    var sPoint = 0
    for (y in 0 until N) {
        for (x in 0 until N) {

            var count = 0
            for (i in 0 until 4) {
                val checkY = y + MY[i]
                val checkX = x + MX[i]
                if (isInMap(checkY, checkX)) {
                    if (seats[checkY][checkX] in dataList[seats[y][x] - 1].drop(1)) {
                        count++
                    }
                }
            }
            sPoint += 10.0.pow(count - 1).toInt()
        }
    }
    return sPoint
}

private fun sitChild(data: IntArray) {
    val child = data.first()
    val likeNumList = data.drop(1)
    //조건에 맞는 위치 탐색
    val fitPoint = findSitPoint(likeNumList)
    //그 위치에 배치
    seats[fitPoint.y][fitPoint.x] = child
}

private fun findSitPoint(likeNumList: List<Int>): Point {
    val PQ = PriorityQueue<Point>()

    for (y in 0 until N) {
        for (x in 0 until N) {
            if (seats[y][x] != 0) continue

            var likeCount = 0
            var emptyCount = 0
            for (i in 0 until 4) {
                val checkY = y + MY[i]
                val checkX = x + MX[i]
                if (isInMap(checkY, checkX)) {
                    if (seats[checkY][checkX] == 0) emptyCount++
                    if (seats[checkY][checkX] in likeNumList) likeCount++
                }
            }
            PQ.add(Point(y, x, likeCount, emptyCount))
        }
    }
    return PQ.poll()
}

private fun isInMap(y: Int, x: Int) =
    (y in 0 until N) && (x in 0 until N)

private data class Point(
    val y: Int,
    val x: Int,
    val likeCount: Int = 0,
    val emptyCount: Int = 0,
) : Comparable<Point> {
    override fun compareTo(other: Point): Int =
        compareValuesBy(this, other, { -it.likeCount }, { -it.emptyCount }, { it.y }, { it.x })
}
//compareValuesBy가 더 깔끔하지만 메모리는 더 잡아먹음 (더 맘에 드는 거 선택하는게 좋을 듯)
//{
//        return if ((other.likeCount - likeCount) != 0) other.likeCount - likeCount
//        else {
//            if ((other.emptyCount - emptyCount) != 0) other.emptyCount - emptyCount
//            else {
//                if ((y - other.y) != 0) y - other.y
//                else {
//                    x - other.x
//                }
//            }
//        }
//    }