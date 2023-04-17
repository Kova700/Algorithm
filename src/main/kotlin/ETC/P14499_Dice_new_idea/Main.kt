package ETC.P14499_Dice_new_idea

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0 //세로 크기 N (1 ≤ N, M ≤ 20)
private var M = 0 //가로 크기 M
private var x = 0 //주사위를 놓은 곳의 row좌표 (0 ≤ x ≤ N-1)
private var y = 0 //주사위를 놓은 곳의 col좌표(0 ≤ y ≤ M-1)
private var K = 0 //명령의 개수 (1 ≤ K ≤ 1,000)
private lateinit var board: Array<IntArray>
private var diceNum = Array(6) { 0 }
private val MX = arrayOf(0, 0, -1, 1) //동서북남
private val MY = arrayOf(1, -1, 0, 0) //동서북남

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P14499_Dice_new_idea/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    br.readLine().split(" ").map { it.toInt() }.apply {
        N = this[0]
        M = this[1]
        x = this[2]
        y = this[3]
        K = this[4]
    }
    board = Array(N) { br.readLine().split(" ").map { it.toInt() }.toIntArray() }

    br.readLine().split(" ").map { it.toInt() }
        .forEach { moveDice(it) }
}

private fun moveDice(direction: Int) {
    val newX = x + MX[direction - 1]
    val newY = y + MY[direction - 1]
    if (!isInMap(newX, newY)) return

    //1 : 위
    //2 : 북
    //3 : 동
    //4 : 서
    //5 : 남
    //6 : 바닥
    //방향에 따라 굴렸을때 숫자를 옮겨야 하는 위치(주어진 전개도 번호)가 다름으로
    //굴리는 방향에 따라 주사위에 적혀있는 숫자를 옮기는 로직 분기
    val newDiceNum = diceNum.copyOf()
    when (direction) {
        1 -> { //동
            //위(1) -> 동(3) , 서(4) -> 위(1) , 바닥(6) -> 서(4)  , 동(3) -> 바닥(6)
            newDiceNum[0] = diceNum[2]
            newDiceNum[3] = diceNum[0]
            newDiceNum[5] = diceNum[3]
            newDiceNum[2] = diceNum[5]
        }
        2 -> { //서
            //위(1) -> 서(4) , 서(4) -> 바닥(6) , 바닥(6) -> 동(3)  , 동(3) -> 위(1)
            newDiceNum[0] = diceNum[3]
            newDiceNum[3] = diceNum[5]
            newDiceNum[5] = diceNum[2]
            newDiceNum[2] = diceNum[0]
        }
        3 -> { //북
            //위(1) -> 북(2) , 북(2) -> 바닥(6) , 바닥(6) -> 남(5)  , 남(5) -> 위(1)
            newDiceNum[0] = diceNum[1]
            newDiceNum[1] = diceNum[5]
            newDiceNum[5] = diceNum[4]
            newDiceNum[4] = diceNum[0]
        }
        4 -> { //남
            //위(1) -> 남(5) , 남(5) -> 바닥(6) , 바닥(6) -> 북(2)  , 북(2) -> 위(1)
            newDiceNum[0] = diceNum[4]
            newDiceNum[4] = diceNum[5]
            newDiceNum[5] = diceNum[1]
            newDiceNum[1] = diceNum[0]
        }
    }
    diceNum = newDiceNum
    x = newX
    y = newY

    if (board[x][y] == 0) {
        board[x][y] = diceNum[5]
    } else {
        diceNum[5] = board[x][y]
        board[x][y] = 0
    }
    println(diceNum[0])
}

private fun isInMap(x: Int, y: Int) =
    (x in 0 until N) && (y in 0 until M)