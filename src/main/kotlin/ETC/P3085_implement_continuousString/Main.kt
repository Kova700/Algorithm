package ETC.P3085_implement_continuousString

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.max

private val MY = arrayOf(1,-1,0,0) //상하좌우
private val MX = arrayOf(0,0,-1,1) //상하좌우
private val candyList = arrayOf('C','P','Z','Y')

private var N = 0 //보드의 크기 (3 ≤ N ≤ 50)
private lateinit var board :Array<CharArray>
private var answer = 0

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P3085_implement_continuousString/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    board = Array(N){ charArrayOf() }

    repeat(N){
        board[it] = br.readLine().toCharArray()
    }

    for (y in 0 until N){
        for (x in 0 until N){
            for (k in 0 until 4){
                val nextY = y + MY[k]
                val nextX = x + MX[k]
                if (!isInBoard(nextY,nextX)) continue
                if (board[y][x] == board[nextY][nextX]) continue
                
                //사탕 교환
                val temp = board[y][x]
                board[y][x] = board[nextY][nextX]
                board[nextY][nextX] = temp

                //바뀐 보드판을 토대로 최대한 먹을 수 있는 개수 구함
                eatCandy()

                //원상 복구
                board[nextY][nextX] = board[y][x]
                board[y][x] = temp
            }
        }
    }
    println(answer)
}

private fun eatCandy(){
    var maxEatCount = 0

    //각 색깔별로 사탕 먹어보기
    for (targetCandy in candyList){
        var count :Int

        //가로줄 검사
        for (y in 0 until N){
            count = 0
            for (x in 0 until N){
                if (board[y][x] != targetCandy) continue
                count++

                if (!isInBoard(y,x+1) || board[y][x+1] != targetCandy){
                    maxEatCount = max(maxEatCount, count)
                    count = 0
                    continue
                }
            }
        }

        //세로줄 검사
        for (x in 0 until N){
            count = 0
            for (y in 0 until N){
                if (board[y][x] != targetCandy) continue
                count++

                if (!isInBoard(y+1,x) || board[y+1][x] != targetCandy){
                    maxEatCount = max(maxEatCount, count)
                    count = 0
                    continue
                }
            }
        }

    }
    answer = max(maxEatCount, answer)
}

private fun isInBoard(y :Int, x :Int) =
    (y in 0 until N) && (x in 0 until N)