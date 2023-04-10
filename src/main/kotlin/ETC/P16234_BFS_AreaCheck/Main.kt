package ETC.P16234_BFS_AreaCheck

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.math.abs

private var N = 0 //(1 ≤ N ≤ 50) 가로,세로 길이
private var L = 0 //1 ≤ L ≤ R ≤ 100) 인구차이가 L 이상
private var R = 0 //1 ≤ L ≤ R ≤ 100) R 이하라면 국경선 열림
private val MY = arrayOf(0, -1, 0, 1)//우하좌상
private val MX = arrayOf(1, 0, -1, 0)//우하좌상
private lateinit var peopleCountMap: Array<IntArray>
private var foundUnion = false
private var day = 0
private lateinit var unionNumList: Array<IntArray>
private var unionPeopleCountList = mutableListOf<Int>()

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P16234_BFS_AreaCheck/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    br.readLine().split(" ").map { it.toInt() }.apply {
        N = this[0]
        L = this[1]
        R = this[2]
    }
    peopleCountMap = Array(N) { IntArray(N) }
    unionNumList = Array(N) { IntArray(N) }
    repeat(N) { i ->
        peopleCountMap[i] = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }

    while (true) {
        checkUnionArea()
        if (!foundUnion) break
        movePeople()
        unionNumList = Array(N) { IntArray(N) }
        unionPeopleCountList.clear()
        day++
    }

    println(day)
}

private fun checkUnionArea() {
    foundUnion = false
    val isVisited = Array(N) { BooleanArray(N) { false } }
    val Q: Queue<Point> = LinkedList()
    var unionNum = 0

    for (y in 0 until N) {
        for (x in 0 until N) {
            if (isVisited[y][x]) continue

            isVisited[y][x] = true
            Q.add(Point(y, x))
            var unionCountryCount = 1
            var unionPeopleCount = peopleCountMap[y][x]
            unionNumList[y][x] = unionNum

            while (Q.isNotEmpty()) {
                val point = Q.poll()
                for (i in 0 until 4) {
                    val checkY = point.y + MY[i]
                    val checkX = point.x + MX[i]
                    //맵 내부 + 국경이 열려있는 국가
                    if (isInMap(checkY, checkX) && !isVisited[checkY][checkX] &&
                        abs(peopleCountMap[point.y][point.x] - peopleCountMap[checkY][checkX]) in L..R
                    ) {
                        foundUnion = true
                        isVisited[checkY][checkX] = true
                        Q.add(Point(checkY, checkX))
                        unionCountryCount++
                        unionPeopleCount += peopleCountMap[checkY][checkX]
                        unionNumList[checkY][checkX] = unionNum
                    }
                }
            }

            unionNum++
            unionPeopleCountList.add(unionPeopleCount / unionCountryCount)
        }
    }
}

private fun movePeople() {
    for (y in 0 until N) {
        for (x in 0 until N) {
            peopleCountMap[y][x] = unionPeopleCountList[unionNumList[y][x]]
        }
    }
}

private fun isInMap(y: Int, x: Int) =
    (y in 0 until N) && (x in 0 until N)

private data class Point(
    val y: Int,
    val x: Int
)