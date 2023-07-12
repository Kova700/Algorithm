package ETC.P17144_implement

import java.io.FileInputStream
import java.util.*

private var R = 0 //가로 (6 ≤ R ≤ 50)
private var C = 0 //세로 (6 ≤ C ≤ 50)
private var T = 0 //주어진 시간(1 ≤ T ≤ 1,000)
private val MY = arrayOf(1, -1, 0, 0) // 상하좌우
private val MX = arrayOf(0, 0, -1, 1) // 상하좌우
private lateinit var board: Array<IntArray>
private lateinit var temp: Array<IntArray> //먼지확산의 변화 기억
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P17144_implement/input"))
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())
    R = st.nextToken().toInt()
    C = st.nextToken().toInt()
    T = st.nextToken().toInt()

    board = Array(R) { IntArray(C) }
    temp = Array(R) { IntArray(C) }
    var cleanerY2 = 0

    //시간 비교 (적게 드는 순)
    //① IntArray 와 St조합 (애용하자)
    //② br.readLine().split(" ").map{it.toInt}.toIntArray()
    //③ MutableList(ArrayList)  (최대한 지양하자)
    repeat(R) { i ->
        // (-1 ≤ A(r,c) ≤ 1,000)
        st = StringTokenizer(br.readLine())
        for (j in 0 until C){
            board[i][j] = st.nextToken().toInt()
        }
        if (board[i][0] == -1) cleanerY2 = i
    }

    repeat(T) {
        //먼지 확산
        for (y in 0 until R) {
            for (x in 0 until C) {
                if (board[y][x] <= 0) continue
                val dustAmount = board[y][x] / 5
                for (k in 0 until 4) {
                    val nextY = MY[k] + y
                    val nextX = MX[k] + x
                    if (!isInMap(nextY, nextX) || board[nextY][nextX] == -1) continue
                    temp[nextY][nextX] += dustAmount
                    board[y][x] -= dustAmount
                }
            }
        }

        //변화 합치기
        for (y in 0 until R) {
            for (x in 0 until C) {
                if (board[y][x] == -1 || temp[y][x] == 0) continue
                board[y][x] += temp[y][x]
                temp[y][x] = 0
            }
        }
        //청소기 공기 순환
        cleanCycle(cleanerY2 - 1, 1, 1)
        cleanCycle(cleanerY2, 1, -1)
    }

    var sum = 0
    for (y in 0 until R) {
        for (x in 0 until C) {
            if (board[y][x] <= 0) continue
            sum += board[y][x]
        }
    }
    println(sum)
}

private fun isInMap(y: Int, x: Int) =
    (y in 0 until R) && (x in 0 until C)

private fun cleanCycle(y: Int, x: Int, type: Int) {
    var tempY = y
    var tempX = x
    //다음칸 값을 뽑고,
    //다음칸에 현재칸 값(이전에 뽑아둔 값)을 담고
    //... 반복
    var nowValue = board[tempY][tempX]
    var nextValue = 0
    var direction = 0
    val MY = arrayOf(0, -1, 0, 1)//우,하(상),좌,상(하)
    val MX = arrayOf(1, 0, -1, 0)//우,하(상),좌,상(하)
    board[tempY][tempX] = 0
    while (true) {
        tempY += if (direction == 1 || direction == 3) MY[direction] * type else MY[direction]
        tempX += MX[direction]
        if (!isInMap(tempY, tempX)) {
            tempY -= if (direction == 1 || direction == 3) MY[direction] * type else MY[direction]
            tempX -= MX[direction]
            direction++
            continue
        }
        if (board[tempY][tempX] == -1) break
        nextValue = board[tempY][tempX]
        board[tempY][tempX] = nowValue
        nowValue = nextValue
    }
}