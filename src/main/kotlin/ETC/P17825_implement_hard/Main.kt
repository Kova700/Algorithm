package ETC.P17825_implement_hard

import java.io.FileInputStream

private val road0 = arrayOf(2, 4, 6, 8, 10) //시작 빨강
private val road1 = arrayOf(10, 12, 14, 16, 18, 20) //road1 : 10 빨강
private val road2 = arrayOf(10, 13, 16, 19, 25) //road2 : 10 파랑
private val road3 = arrayOf(20, 22, 24, 26, 28, 30) //road3 : 20 빨강
private val road4 = arrayOf(20, 22, 24, 25) //road4 : 20 파랑
private val road5 = arrayOf(30, 32, 34, 36, 38, 40) //road5 : 30 빨강
private val road6 = arrayOf(30, 28, 27, 26, 25) //road6 : 30 파랑
private val road7 = arrayOf(25, 30, 35, 40) //25 빨강
private val endRoad = arrayOf(40)

private val roads = arrayOf(
    road0, road1, road2,
    road3, road4, road5,
    road6, road7, endRoad
)
private lateinit var diceResults: IntArray
private var answer = Int.MIN_VALUE
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P17825_implement_hard/input"))
    val br = System.`in`.bufferedReader()
    //주사위에서 나올 수 10개가 순서대로 주어진다.
    diceResults = br.readLine().split(" ").map { it.toInt() }.toIntArray()

    dfs(0, 0, Point(-2, 0), Point(-2, 0), Point(-2, 0), Point(-2, 0))

    println(answer)
}

//각 말의 현재 위치
private fun dfs(
    diceIndex: Int,
    pointSum: Int,
    a1: Point,
    a2: Point,
    a3: Point,
    a4: Point
) {
    if (diceIndex >= 10) {
        answer = maxOf(answer, pointSum)
        return
    }
    //말이 이동을 마치는 칸에 다른 말이 있으면 그 말은 고를 수 없다. 단, 이동을 마치는 칸이 도착 칸이면 고를 수 있다.
    //말이 마지막칸에 도착하면 그 말은 더 이상 이동하지 않는다.
    if (a1.roadNum != -1) {
        val nextA1 = getNextPoint(a1, diceResults[diceIndex])
        if (isNotSamePoint(nextA1, a2, a3, a4)) {
            var point = 0
            if (nextA1.roadNum != -1) {
                point = roads[nextA1.roadNum][nextA1.roadIndex]
            }
            dfs(diceIndex + 1, pointSum + point, nextA1, a2, a3, a4)
        }
    }
    if (a2.roadNum != -1) {
        val nextA2 = getNextPoint(a2, diceResults[diceIndex])
        if (isNotSamePoint(a1, nextA2, a3, a4)) {
            var point = 0
            if (nextA2.roadNum != -1) {
                point = roads[nextA2.roadNum][nextA2.roadIndex]
            }
            dfs(diceIndex + 1, pointSum + point, a1, nextA2, a3, a4)
        }
    }
    if (a3.roadNum != -1) {
        val nextA3 = getNextPoint(a3, diceResults[diceIndex])
        if (isNotSamePoint(a1, a2, nextA3, a4)) {
            var point = 0
            if (nextA3.roadNum != -1) {
                point = roads[nextA3.roadNum][nextA3.roadIndex]
            }
            dfs(diceIndex + 1, pointSum + point, a1, a2, nextA3, a4)
        }
    }
    if (a4.roadNum != -1) {
        val nextA4 = getNextPoint(a4, diceResults[diceIndex])
        if (isNotSamePoint(a1, a2, a3, nextA4)) {
            var point = 0
            if (nextA4.roadNum != -1) {
                point = roads[nextA4.roadNum][nextA4.roadIndex]
            }
            dfs(diceIndex + 1, pointSum + point, a1, a2, a3, nextA4)
        }
    }

}

private fun isNotSamePoint(a1: Point, a2: Point, a3: Point, a4: Point): Boolean {
    val unfinishedMals = arrayOf(a1, a2, a3, a4).filter { it.roadNum != -1 && it.roadNum != -2 }
    return unfinishedMals.size == unfinishedMals.map { "${it.roadNum} ${it.roadIndex}" }.toSet().size
}

private fun getNextPoint(cPoint: Point, mX: Int): Point {
    var cRoadNum = cPoint.roadNum

    var currentIndex = cPoint.roadIndex
    var nextIndex = currentIndex + mX
    if (cRoadNum == -2) {
        cRoadNum = 0
        nextIndex--
    }
    while (true) {
        when {
            nextIndex > roads[cRoadNum].lastIndex -> {
                nextIndex -= roads[cRoadNum].lastIndex
                cRoadNum = getNextRoadNum(cRoadNum)
                if (cRoadNum == -1) break
            }

            nextIndex == roads[cRoadNum].lastIndex -> {
                nextIndex = 0
                cRoadNum = getBlueRoadNum(cRoadNum)
                break
            }

            else -> break
        }
    }
    return Point(cRoadNum, nextIndex)
}

//0 : 시작 빨강
//1 : 10 빨강
//2 : 10 파랑
//3 : 20 빨강
//4 : 20 파랑
//5 : 30 빨강
//6 : 30 파랑
//7 : 25 빨강
//8 : 마지막 길
//-1 : 종료
//-2 : 시작(움직이기 전)
private fun getNextRoadNum(cRoadNum: Int): Int {
    return when (cRoadNum) {
        0 -> 1  //시작 빨강 -> 10 빨강
        1 -> 3  //10 빨강 -> 20 빨강
        2 -> 7  //10 파란 -> 25 빨강
        3 -> 5  //20 빨강 -> 30 빨강
        4 -> 7  //20 파랑 -> 25 빨강
        5 -> 8  //30 빨강 -> 마지막길
        6 -> 7  //30 파랑 -> 25 빨강
        7 -> 8  //25 빨강 -> 마지막길
        8 -> -1 //마지막길 -> 종료
        else -> throw Exception("NextRoadNum Exception $cRoadNum")
    }
}

private fun getBlueRoadNum(cRoadNum: Int): Int {
    return when (cRoadNum) {
        0 -> 2  //시작 빨강 -> 10 파랑
        1 -> 4  //10 빨강 -> 20 파랑
        3 -> 6  //20 빨강 -> 30 파랑
        2 -> 7  //10 파랑 -> 25 빨강
        4 -> 7  //20 파랑 -> 25 빨강
        6 -> 7  //30 파랑 -> 25 빨강
        5 -> 8  //30 빨강 -> 마지막길
        7 -> 8  //25 빨강 -> 마지막길
        else -> throw Exception("BlueRoadNum Exception $cRoadNum")
    }
}

private data class Point(
    val roadNum: Int,
    val roadIndex: Int
)