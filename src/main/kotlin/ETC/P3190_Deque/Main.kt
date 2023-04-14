package ETC.P3190_Deque

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

private var N = 0 //보드 가로,세로 (2 ≤ N ≤ 100)
private var K = 0 //사과의 개수 (0 ≤ K ≤ 100)
private var L = 0 //뱀의 방향 변환 횟수 (1 ≤ L ≤ 100)
private lateinit var board: Array<IntArray>
private lateinit var moveQueue: Queue<List<String>>
private lateinit var snakeDeque: ArrayDeque<Point>
private var time = 1
private var snakeDirection = 0 // 0 1 2 3 //우하좌상
private var snakeHeadY = 0
private var snakeHeadX = 0
private val MY = arrayOf(0, 1, 0, -1)//우하좌상
private val MX = arrayOf(1, 0, -1, 0)//우하좌상

//빈공간(0), 뱀(1) , 사과(2)
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P3190_Deque/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    K = br.readLine().toInt()
    board = Array(N) { IntArray(N) { 0 } }
    board[0][0] = 1

    repeat(K) {
        br.readLine().split(" ").map { it.toInt() }.apply {
            board[this[0] - 1][this[1] - 1] = 2
        }
    }
    L = br.readLine().toInt()
    moveQueue = LinkedList()
    repeat(L) {
        moveQueue.add(br.readLine().split(" "))
    }
    snakeDeque = ArrayDeque<Point>()
    snakeDeque.addFirst(Point(0, 0))

    while (true) {
        snakeHeadY += MY[snakeDirection]
        snakeHeadX += MX[snakeDirection]

        if (!isInMap(snakeHeadY, snakeHeadX) || board[snakeHeadY][snakeHeadX] == 1) {
            break
        }

        when(board[snakeHeadY][snakeHeadX]){
            2 ->{
                snakeDeque.addFirst(Point(snakeHeadY, snakeHeadX))
                board[snakeHeadY][snakeHeadX] = 1
            }
            0 ->{
                snakeDeque.addFirst(Point(snakeHeadY, snakeHeadX))
                board[snakeHeadY][snakeHeadX] = 1
                val lastTail = snakeDeque.pollLast()
                board[lastTail.y][lastTail.x] = 0
            }
        }

        //방향 전환
        if (moveQueue.isNotEmpty() && time == moveQueue.peek().first().toInt()) {
            moveQueue.poll().apply {
                changeSnakeDirection(this[1])
            }
        }

        time++
    }

    println(time)
}

private fun isInMap(y: Int, x: Int) =
    (y in 0 until N) && (x in 0 until N)

private fun changeSnakeDirection(direction: String) {
    when (direction) {
        "D" -> snakeDirection = if (snakeDirection == 3) 0 else snakeDirection + 1
        "L" -> snakeDirection = if (snakeDirection == 0) 3 else snakeDirection - 1
    }
}

private data class Point(
    val y: Int,
    val x: Int
)