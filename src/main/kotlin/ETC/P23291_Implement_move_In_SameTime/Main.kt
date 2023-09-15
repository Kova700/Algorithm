package ETC.P23291_Implement_move_In_SameTime

import java.io.FileInputStream
import java.util.*
import kotlin.math.abs

private var N = 0 //어항의 개수 (4 ≤ N ≤ 100) N은 4의 배수
private var K = 0 //어항 물고기 수 차이(목표값) (0 ≤ K ≤ 10)
private lateinit var fishbowl: Array<IntArray> //fishbowl[x축][y축]
private val MY = arrayOf(1, -1, 0, 0)//상하좌우
private val MX = arrayOf(0, 0, -1, 1)//상하좌우
private var count = 0
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P23291_Implement_move_In_SameTime/input"))
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    K = st.nextToken().toInt()

    st = StringTokenizer(br.readLine())
    fishbowl = Array(N) { IntArray(N) { -1 } }

    for (i in 0 until N) {
        //1 ≤ 각 어항에 들어있는 물고기의 수 ≤ 10,000
        fishbowl[i][0] = st.nextToken().toInt()
    }

    while (true) {
        if (isAnswer()) break

        //물고기의 수가 가장 적은 어항에 물고기를 한 마리 넣는다.
        var min = Int.MAX_VALUE
        for (i in 0 until N) {
            min = minOf(min, fishbowl[i][0])
        }

        //그러한 어항이 여러개라면 물고기의 수가 최소인 어항 모두에 한 마리씩 넣는다.
        for (i in 0 until N) {
            if (fishbowl[i][0] != min) continue
            fishbowl[i][0]++
        }

        //가장 왼쪽에 있는 어항을 그 어항의 오른쪽에 있는 어항의 위에 올려 놓는다.
        fishbowl[1][1] = fishbowl[0][0]
        fishbowl[0][0] = -1

        rotation90Degree()
        exchangeFish()
        flattenFishBowl()

        rotation180Degree()
        exchangeFish()
        flattenFishBowl()
        count++

    }
    println(count)
}

private fun isAnswer(): Boolean {
    var max = Int.MIN_VALUE
    var min = Int.MAX_VALUE
    for (i in 0 until N) {
        max = maxOf(max, fishbowl[i][0])
        min = minOf(min, fishbowl[i][0])
    }
    return max - min <= K
}

private fun rotation90Degree() {
    var startXIndex = 1 //어항이 시작되는 인덱스
    var height = 2 //공중부양될 칸의 높이
    var width = 1 // 공중부양될 칸의 너비
    while (true) {
        height = fishbowl[startXIndex].count { it != -1 } //공중부양될 칸의 높이

        //공중 부양해서 오른쪽으로 눕혔을때 가로 길이가 바닥의 가로길이보다 크다면 적용하지 않고 종료
        if (height > (N - 1) - (startXIndex + width - 1)) break

        //startXIndex부터 startXIndex + (width - 1)까지 공중부양
        var heightIndex = 1
        val newStartXIndex = startXIndex + width
        //공중 부양한 블록 중 가장 오른쪽 부터 쌓기
        for (i in startXIndex + (width - 1) downTo startXIndex) {
            var itemIndex = 0
            //새로운 시작점부터 위에 어항 쌓기
            for (j in 0 until height) {
                fishbowl[newStartXIndex + j][heightIndex] = fishbowl[i][itemIndex]
                fishbowl[i][itemIndex] = -1
                itemIndex++
            }
            heightIndex++
        }

        startXIndex = newStartXIndex
        width = height
    }
}

private fun rotation180Degree() {
    for (i in 0 until N / 2) {
        fishbowl[(N - 1) - i][1] = fishbowl[i][0]
        fishbowl[i][0] = -1
    }

    var rightFloor = 2
    var leftFloor = 1
    val offsetRange = 0 until (N - N / 2) / 2
    for (i in 0 until 2) {
        for (j in offsetRange) {
            fishbowl[(N - 1) - j][rightFloor] = fishbowl[N / 2 + j][leftFloor]
            fishbowl[N / 2 + j][leftFloor] = -1
        }
        rightFloor++
        leftFloor--
    }
}

private fun exchangeFish() {
    val changes = mutableListOf<Change>()
    val Q: Queue<Point> = LinkedList()
    val isVisited = Array(N) { BooleanArray(N) }
    isVisited[N - 1][0] = true
    Q.add(Point(N - 1, 0))

    while (Q.isNotEmpty()) {
        val cPoint = Q.poll()

        for (direction in 0 until 4) {
            val nextX = cPoint.x + MX[direction]
            val nextY = cPoint.y + MY[direction]
            if (!isInMap(nextX, nextY) || fishbowl[nextX][nextY] == -1) continue

            val nPoint = Point(nextX, nextY)
            if (!isVisited[nextX][nextY]){
                isVisited[nextX][nextY] = true
                Q.add(nPoint)
            }

            val cFishCount = fishbowl[cPoint.x][cPoint.y]
            val nFishCount = fishbowl[nextX][nextY]
            val d = abs(cFishCount - nFishCount) / 5
            if (d == 0) continue

            //상 좌 일때만 추가
            if (direction == 0 || direction == 2){
                when {
                    cFishCount > nFishCount -> {
                        changes.add(Change(cPoint, nPoint, d))
                    }

                    cFishCount < nFishCount -> {
                        changes.add(Change(nPoint, cPoint, d))
                    }
                }
            }
        }
    }
    for (change in changes) {
        fishbowl[change.from.x][change.from.y] -= change.value
        fishbowl[change.to.x][change.to.y] += change.value
    }
}

private fun flattenFishBowl() {
    val newFishbowl = Array(N) { IntArray(N) { -1 } }
    var index = 0
    for (i in 0 until N) {
        for (j in 0 until N) {
            if (fishbowl[i][j] == -1) break

            newFishbowl[index][0] = fishbowl[i][j]
            index++
        }
    }
    fishbowl = newFishbowl
}

private fun isInMap(x: Int, y: Int) =
    (y in 0 until N) && (x in 0 until N)

private data class Point(
    val x: Int,
    val y: Int
)

private data class Change(
    val from: Point,
    val to: Point,
    val value: Int
)