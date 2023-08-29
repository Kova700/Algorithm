package ETC.P19237_implement_MoveInSameTime

import java.io.FileInputStream
import java.util.*

private var N = 0 //격자의 크기(2 ≤ N ≤ 20)
private var M = 0 //상어 개수(2 ≤ M ≤ N^2)
private var K = 0 //상어 냄새가 사라지는 이동 횟수(1 ≤ K ≤ 1,000)
private val MY = arrayOf(-1, 1, 0, 0) //상하좌우
private val MX = arrayOf(0, 0, -1, 1) //상하좌우
private lateinit var smellBoard: Array<IntArray> //채취 남은량 기억
private lateinit var sharkBoard: Array<IntArray> //누구의 채취인지 기억
private lateinit var directionPriority: Array<Array<IntArray>> //directionPriority[상어num][현재방향] = 우선순위방향배열
private lateinit var sharks: Array<Shark?> //현재 상어의 방향 기억
private var time = 0
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P19237_implement_MoveInSameTime/input"))
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()
    K = st.nextToken().toInt()
    sharks = Array(M + 1) { null }
    sharkBoard = Array(N) { IntArray(N) }

    //0은 빈칸이고, 0이 아닌 수 x는 x번 상어가 들어있는 칸을 의미한다.
    for (i in 0 until N) {
        st = StringTokenizer(br.readLine())
        for (j in 0 until N) {
            val value = st.nextToken().toInt()
            sharkBoard[i][j] = value
            if (value != 0) {
                sharks[value] = Shark(i, j, value, null)
            }
        }
    }
    smellBoard = Array(N) { IntArray(N) }
    makeSmell()
    directionPriority = Array(M + 1) { Array(4) { IntArray(4) } }
    //그 다음 줄에는 각 상어의 방향이 차례대로 주어진다.
    // 1, 2, 3, 4는 각각 위, 아래, 왼쪽, 오른쪽을 의미한다.
    st = StringTokenizer(br.readLine())
    for (num in 1..M) {
        sharks[num].apply {
            this?.direction = st.nextToken().toInt() - 1
        }
    }

    for (sharkNum in 1..M) {
        for (cDirection in 0 until 4) {
            directionPriority[sharkNum][cDirection] =
                br.readLine().split(" ").map { it.toInt() - 1 }.toIntArray()
            }
    }

    while (true) {
        moveShark()
        deleteSmell()
        makeSmell()
        time++
        if (time > 1000) {
            time = -1
            break
        }
        if (isOnlyFirstSharkAlive()) break
    }

    println(time)
}

private fun isOnlyFirstSharkAlive(): Boolean {
    for (num in 2..M) {
        if (sharks[num] != null) return false
    }
    return true
}

private fun moveShark() {

    loop@ for (num in M downTo 1) {
        if (sharks[num] == null) continue
        val cShark = sharks[num]!!

        //먼저 인접한 칸 중 아무 냄새가 없는 칸의 방향으로 잡는다.
        for (k in 0 until 4) {
            val newDirection = directionPriority[cShark.num][cShark.direction!!][k]
            val nextY = cShark.y + MY[newDirection]
            val nextX = cShark.x + MX[newDirection]
            if (!isInMap(nextY, nextX) || smellBoard[nextY][nextX] != 0) continue

            val otherSharkNum = sharkBoard[nextY][nextX]
            sharks[otherSharkNum] = null

            sharkBoard[nextY][nextX] = cShark.num
            sharks[cShark.num].apply {
                this?.y = nextY
                this?.x = nextX
                this?.direction = newDirection
            }
            continue@loop
        }

        //그런 칸이 없으면 자신의 냄새가 있는 칸의 방향으로 잡는다.
        for (k in 0 until 4) {
            val newDirection = directionPriority[cShark.num][cShark.direction!!][k]
            val nextY = cShark.y + MY[newDirection]
            val nextX = cShark.x + MX[newDirection]
            if (!isInMap(nextY, nextX) || sharkBoard[nextY][nextX] != cShark.num) continue

            sharks[cShark.num].apply {
                this?.y = nextY
                this?.x = nextX
                this?.direction = newDirection
            }
            continue@loop
        }
    }
}

private fun makeSmell() {
    for (num in 1..M) {
        if (sharks[num] == null) continue
        val cShark = sharks[num]!!
        sharkBoard[cShark.y][cShark.x] = num
        smellBoard[cShark.y][cShark.x] = K
    }
}

private fun deleteSmell() {
    for (i in 0 until N) {
        for (j in 0 until N) {
            if (smellBoard[i][j] == 0) continue
            smellBoard[i][j]--
            if (smellBoard[i][j] == 0) {
                sharkBoard[i][j] = 0
            }
        }
    }
}

private fun isInMap(y: Int, x: Int) =
    (y in 0 until N) && (x in 0 until N)

private data class Shark(
    var y: Int,
    var x: Int,
    val num: Int,
    var direction: Int?
)