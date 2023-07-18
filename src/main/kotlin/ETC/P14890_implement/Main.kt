package ETC.P14890_implement

import java.io.FileInputStream
import java.util.*
import kotlin.math.abs

private var N = 0 //지도 크기 (2 ≤ N ≤ 100)
private var L = 0 //경사로의 길이(1 ≤ L ≤ N)
private lateinit var board: Array<IntArray>
private lateinit var isBuilt: Array<Array<BooleanArray>> //경사로 설치 여부(가로,세로)
private var count = 0
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P14890_implement/input"))
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    L = st.nextToken().toInt()
    board = Array(N) { IntArray(N) }
    isBuilt = Array(N) { Array(N) { BooleanArray(2) } }

    for (i in 0 until N) {
        st = StringTokenizer(br.readLine())
        for (j in 0 until N) {
            //각 칸의 높이는 10보다 작거나 같은 자연수이다.
            board[i][j] = st.nextToken().toInt()
        }
    }
    checkRow()
    checkCol()

    println(count)
}

private fun checkRow() {
    loop2@ for (i in 0 until N) {
        var failIndex = 0
        loop@ for (j in 1 until N) {
            val currentFloor = board[i][j]
            if (currentFloor == board[i][j - 1]) continue

            val gap = abs(board[i][j - 1] - currentFloor)
            if (gap > 1) continue@loop2

            if (currentFloor < board[i][j - 1]) {
                for (k in 0 until L) {
                    if (!isInMap(i, j + k) || currentFloor != board[i][j + k] || isBuilt[i][j + k][0]) {
                        failIndex = j
                        break@loop
                    }
                }
                for (k in 0 until L) {
                    isBuilt[i][j + k][0] = true
                }
            } else if (currentFloor > board[i][j - 1]) {
                for (k in 0 until L) {
                    if (!isInMap(i, j - 1 - k) || board[i][j - 1] != board[i][j - 1 - k] || isBuilt[i][j - 1 - k][0]) {
                        failIndex = j
                        break@loop
                    }
                }
                for (k in 0 until L) {
                    isBuilt[i][j - 1 - k][0] = true
                }
            }

        }
        if (failIndex == 0) {
            count++
            continue
        }

        for (j in 0 until failIndex) {
            isBuilt[i][j][0] = false
        }
    }
}

private fun checkCol() {
    //세로방향 길 검사
    loop2@ for (j in 0 until N) {
        var failIndex = 0
        loop@ for (i in 1 until N) {
            val currentFloor = board[i][j]
            if (currentFloor == board[i - 1][j]) continue

            val gap = abs(board[i - 1][j] - currentFloor)
            if (gap > 1) continue@loop2

            if (currentFloor < board[i - 1][j]) {
                for (k in 0 until L) {
                    if (!isInMap(i + k, j) || currentFloor != board[i + k][j] || isBuilt[i + k][j][1]) {
                        failIndex = i
                        break@loop
                    }
                }
                for (k in 0 until L) {
                    isBuilt[i + k][j][1] = true
                }
            } else if (currentFloor > board[i - 1][j]) {
                for (k in 0 until L) {
                    if (!isInMap(i - 1 - k, j) || board[i - 1][j] != board[i - 1 - k][j] || isBuilt[i - 1 - k][j][1]) {
                        failIndex = i
                        break@loop
                    }
                }
                for (k in 0 until L) {
                    isBuilt[i - 1 - k][j][1] = true
                }
            }
        }
        if (failIndex == 0) {
            count++
            continue
        }

        for (i in 0 until failIndex) {
            isBuilt[i][j][1] = false
        }
    }
}

private fun isInMap(y: Int, x: Int) =
    (y in 0 until N) && (x in 0 until N)

//검사 공통사항
//현재 칸이 이전 칸보다 값이 작다면
//    차이가 1보다 크다면 이용불가
//    이미 경사로가 놓여있다면 이용불가
//    현재칸부터 L만큼 다음칸들을 확인해봐야함
//          중간에 만족하지 않는다면 해당길은 이용불가
//              검사를 시작하는 칸부터 L칸까지 높이가 같지 않은경우
//              검사를 시작하는 칸부터 L칸까지 길이가 나오지 않는경우
//현재 칸이 이전 칸 보다 값이 크다면
//      차이가 1보다 크다면 이용불가
//      이미 경사로가 놓여있다면 이용불가
//      현재칸부터 L-1만큼 이전칸들을 다시 확인해봐야함
//          중간에 만족하지 않는다면 해당길은 이용불가
//              검사를 시작하는 칸부터 L칸까지 높이가 같지 않은경우
//              검사를 시작하는 칸부터 L칸까지 길이가 나오지 않는경우