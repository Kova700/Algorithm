package ETC.P1063

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private lateinit var kingPoint :Point
private lateinit var stonePoint :Point
private var N = 0 // N은 50보다 작거나 같은 자연수

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1063/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    br.readLine().split(" ").apply {
        //첫째 줄에 킹의 위치, 돌의 위치, 움직이는 횟수 N이 주어진다.
        kingPoint = Point(this[0][0].toChessInt(), this[0][1].digitToInt())
        stonePoint = Point(this[1][0].toChessInt(), this[1][1].digitToInt())
        N = this[2].toInt()
    }
    repeat(N){
        //둘째 줄부터 N개의 줄에는 킹이 어떻게 움직여야 하는지 주어진다.
        move(br.readLine())
    }
    kingPoint.printChessPoint()
    stonePoint.printChessPoint()
}

//체스판은 x좌표가 우선적으로 사용됨
data class Point(
    val x :Int,
    val y :Int
){
    fun printChessPoint() = println("${this.x.toChessString()}${this.y}")
}
private fun Char.toChessInt(): Int {
    return when (this) {
        'A' -> 1; 'B' -> 2
        'C' -> 3; 'D' -> 4
        'E' -> 5; 'F' -> 6
        'G' -> 7; 'H' -> 8
        else -> throw java.lang.Exception("Invalid string")
    }
}
private fun Int.toChessString(): String {
    return when (this) {
        1 -> "A"; 2 -> "B"
        3 -> "C"; 4 -> "D"
        5 -> "E"; 6 -> "F"
        7 -> "G"; 8 -> "H"
        else -> throw java.lang.Exception("Invalid Int")
    }
}

private fun isInMap(y :Int, x :Int) =
    (y in 1..8) && (x in 1..8)

private fun move(s :String) {
    var dX = 0
    var dY = 0
    var stoneFlag = false
    when(s){
        "R" -> { dX = 1 }
        "L" -> { dX = -1 }
        "B" -> { dY = -1 }
        "T" -> { dY = 1 }
        "RT" -> { dX = 1; dY = 1 }
        "LT" -> { dX = -1; dY = 1 }
        "RB" -> { dX = 1; dY = -1 }
        "LB" -> { dX = -1; dY = -1 }
        else -> throw java.lang.Exception("Invalid string")
    }
    //옮긴 킹과 돌이 같은 좌표에 있으면 킹이 움직인 방향으로 돌도 옮기기
    if ((kingPoint.y + dY == stonePoint.y) &&
        (kingPoint.x + dX == stonePoint.x)){
        stoneFlag = true
    }
    //킹이나 돌이 맵 밖이면 다시 되돌리기(이번 턴 무시)
    if (!isInMap(kingPoint.y + dY, kingPoint.x + dX)) return
    if (stoneFlag && !isInMap(stonePoint.y + dY, stonePoint.x + dX)) return

    kingPoint = kingPoint.copy(y = kingPoint.y + dY, x = kingPoint.x + dX)
    if (stoneFlag){
        stonePoint = stonePoint.copy(y = stonePoint.y + dY, x = stonePoint.x + dX)
    }

}