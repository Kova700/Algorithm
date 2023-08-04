package ETC.P17779_Implement

import java.io.FileInputStream
import java.util.*

//(x,y) -> (y,x)로 변환해서 풀다가 오류 계속 터져서 열불났던 문제
//이번 문제에 관해서는 인덱스도 1부터 시작, x,y좌표계도 변환없이 풀어야 쉽게 풀린다.
//괜히 바꿔서 풀었다가 오류의 늪에서 빠져나오기가 쉽지 않다.
private var N = 0 //재현시의 크기 (5 ≤ N ≤ 20)
private lateinit var board: Array<IntArray> //구역의 인구수 (1 ≤ board[r][c] ≤ 100)
private var answer = Int.MAX_VALUE
private lateinit var st: StringTokenizer
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P17779_Implement/input"))
    val br = System.`in`.bufferedReader()
    N = br.readLine().toInt()
    board = Array(N + 1) { IntArray(N + 1) }

    for (i in 1..N) {
        st = StringTokenizer(br.readLine())
        for (j in 1..N) {
            board[i][j] = st.nextToken().toInt()
        }
    }

    for (x in 1..N) {
        for (y in 1..N) {
            for (d1 in 1 until N) {
                if (x + d1 > N || y - d1 < 1) break

                for (d2 in 1 until N) {
                    if (x + d2 > N || y + d2 > N) break
                    if (x + d1 + d2 > N || y - d1 + d2 < 1) break

                    val areaBoard = Array(N + 1) { IntArray(N + 1) { -1 } }
                    drawLine(x, y, d1, d2, areaBoard) //64,000,000 시간복잡도 만족함
                    divideArea(x, y, d1, d2, areaBoard)
                }
            }
        }
    }

    println(answer)
}

private fun drawLine(x: Int, y: Int, d1: Int, d2: Int, areaBoard: Array<IntArray>) {
    for (i in 0..d1) {
        areaBoard[x + i][y - i] = 5
        areaBoard[x + d2 + i][y + d2 - i] = 5
    }

    for (i in 0..d2) {
        areaBoard[x + i][y + i] = 5
        areaBoard[x + d1 + i][y - d1 + i] = 5
    }
}

private fun divideArea(x: Int, y: Int, d1: Int, d2: Int, areaBoard: Array<IntArray>) {
    //아무 조건에 해당되지 않는 구역 == 5번 구역
    val numCountArray = IntArray(6) //영역별 인구수 카운팅

    for (r in 1 until x + d1) {
        for (c in 1..y) {
            if (areaBoard[r][c] == 5) break
            areaBoard[r][c] = 1
            numCountArray[1] += board[r][c]
        }
    }

    for (r in 1..x + d2) {
        for (c in N downTo y + 1) {
            if (areaBoard[r][c] == 5) break
            areaBoard[r][c] = 2
            numCountArray[2] += board[r][c]
        }
    }

    for (r in x + d1..N) {
        for (c in 1 until y - d1 + d2) {
            if (areaBoard[r][c] == 5) break
            areaBoard[r][c] = 3
            numCountArray[3] += board[r][c]
        }
    }

    for (r in x + d2 + 1..N) {
        for (c in N downTo y - d1 + d2) {
            if (areaBoard[r][c] == 5) break
            areaBoard[r][c] = 4
            numCountArray[4] += board[r][c]
        }
    }


    for (r in 1..N) {
        for (c in 1..N) {
            if (areaBoard[r][c] == -1) {
                areaBoard[r][c] = 5
            }

            if (areaBoard[r][c] != 5) continue
            numCountArray[5] += board[r][c]
        }
    }

    //인구가 가장 많은 선거구와 가장 적은 선거구의 인구 차이 구하기
    var max = Int.MIN_VALUE
    var min = Int.MAX_VALUE
    for (i in 1..5) {
        max = maxOf(max, numCountArray[i])
        min = minOf(min, numCountArray[i])
    }
    //max값과 min값의 차이 구해서 answer와 비교 후 갱신
    answer = minOf(answer, max - min)
}