package ETC.P12100_BruteForce

import java.io.FileInputStream
import java.util.*

private var N = 0 //보드의 크기 (1 ≤ N ≤ 20)
private lateinit var board: Array<IntArray>
private lateinit var isCombined: Array<BooleanArray>
private lateinit var st: StringTokenizer
private val MY = arrayOf(1, -1, 0, 0)//상 하 좌 우
private val MX = arrayOf(0, 0, -1, 1)//상 하 좌 우
private var maxBlock = Int.MIN_VALUE
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P12100_BruteForce/input"))
    val br = System.`in`.bufferedReader()
    N = br.readLine().toInt()
    board = Array(N) { IntArray(N) }
    repeat(N) { i ->
        st = StringTokenizer(br.readLine())
        for (j in 0 until N) {
            //0은 빈 칸을 나타내며, 이외의 값은 모두 블록을 나타낸다.
            //2 <= board[i][j] <= 1024를 만족하는 2의 제곱꼴
            //블록은 적어도 하나 주어진다.
            board[i][j] = st.nextToken().toInt()
        }
    }
    dfs(board, 0)
    println(maxBlock)
}

private fun dfs(tempBoard: Array<IntArray>, count: Int) {
    if (count == 5) {
        for (i in 0 until N){
            for (j in 0 until N){
                maxBlock = maxOf(maxBlock, tempBoard[i][j])
            }
        }
        return
    }

    for (direction in 0 until 4) {
        val temp = Array(N) { intArrayOf() }
        isCombined = Array(N) { BooleanArray(N) }
        for (i in 0 until N) {
            temp[i] = tempBoard[i].copyOf()
        }

        when (direction) {
            0 -> { //상
                for (j in 0 until N) {
                    for (i in N - 2 downTo 0) {
                        if (temp[i][j] == 0) continue
                        moveBlock(i, j, temp, direction)
                    }
                }
                dfs(temp, count + 1)
            }
            1 -> { //하
                for (j in 0 until N) {
                    for (i in 1 until N) {
                        if (temp[i][j] == 0) continue
                        moveBlock(i, j, temp, direction)
                    }
                }
                dfs(temp, count + 1)
            }
            2 -> { //좌
                for (i in 0 until N) {
                    for (j in 1 until N) {
                        if (temp[i][j] == 0) continue
                        moveBlock(i, j, temp, direction)
                    }
                }
                dfs(temp, count + 1)
            }
            3 -> { //우
                for (i in 0 until N) {
                    for (j in N - 2 downTo 0) {
                        if (temp[i][j] == 0) continue
                        moveBlock(i, j, temp, direction)
                    }
                }
                dfs(temp, count + 1)
            }
        }

        //충돌해서 합쳐졌는지 확인할 수 있는 기록지가 필요함
        //해당 방향으로 옮기는 로직도 필요함
        //옮겼던걸 다른 방향으로도 해볼 때 되돌려야하는데 어떻게 되돌릴지도 생각
    }
}

private fun moveBlock(y: Int, x: Int, temp: Array<IntArray>, direction: Int) {
    var currentY = y
    var currentX = x
    var nextY = currentY + MY[direction]
    var nextX = currentX + MX[direction]
    while (true) {
        if (nextY !in 0 until N || nextX !in 0 until N) break

        //합쳐지는 경우
        if (temp[currentY][currentX] == temp[nextY][nextX] &&
            !isCombined[nextY][nextX]
        ) {
            temp[nextY][nextX] += temp[currentY][currentX]
            temp[currentY][currentX] = 0
            isCombined[nextY][nextX] = true
            break
        }

        //옮길 수 있는 경우
        if (temp[nextY][nextX] == 0) {
            temp[nextY][nextX] += temp[currentY][currentX]
            temp[currentY][currentX] = 0
            currentY = nextY
            currentX = nextX
            nextY += MY[direction]
            nextX += MX[direction]
            continue
        }

        //옮길수도 없고, 합칠수도 없는경우 종료
        break

        //한칸씩 앞으로 이동하면서
        //합칠 수 있는칸이면 합치고 넘어가고,
        //합칠 수 없으면 칸만옮기기
        //칸마저 이동할 수 없다면 while문 종료
    }
}
//한 번의 이동 =
//      다음칸이 맵 밖이거나, 이미 합쳐진 블록이거나, 합쳐지지 않는 블록이거나
//      를 만족할때까지 그 방향으로 이동
//      이동 순서는 이동하고자 하는 방향에서 가장 앞에 있는 블록우선 이동

//한 번의 이동에서 이미 합쳐진 블록은 또 합쳐질 수 없다.
//똑같은 수가 세 개가 있는 경우에는 이동하려고 하는 쪽의 칸이 먼저 합쳐진다.