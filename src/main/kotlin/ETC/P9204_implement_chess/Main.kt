package ETC.P9204

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

private var N = 0
//true = 흑색, false = 백색
private val chessBoardColor = Array(8 + 1) { BooleanArray(8 + 1) { false } }
private val bw = BufferedWriter(OutputStreamWriter(System.out))
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P9204/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()

    var k = 1
    for (i in 1 until 9) {
        for (j in k until 9 step 2) {
            chessBoardColor[i][j] = true
        }
        k = if (k == 1) 2 else 1
    }

    repeat(N) {
        br.readLine().split(" ").apply {
            getAnswer(
                Point(this[0].toChessInt(), this[1].toInt()),
                Point(this[2].toChessInt(), this[3].toInt())
            )
        }
    }
    bw.flush()
    bw.close()
}

private fun getAnswer(p1: Point, p2: Point) {
    if (chessBoardColor[p1.y][p1.x] != chessBoardColor[p2.y][p2.x]) {
        bw.write("Impossible\n")
        return
    }
    if (p1 == p2) {
        bw.write("0 ${p1.y.toChessString()} ${p1.x}\n")
        return
    }

    //각 Point의 대각선(X)에 들어있는 점들 중 공통된 점 한 개를 찾아야함
    val p1List = getDiagonalList(p1)
    //같은 대각선에 있는경우는 바로 연결
    if (p1List.contains(p2)){
        bw.write("1 ${p1.y.toChessString()} ${p1.x} ${p2.y.toChessString()} ${p2.x}\n")
        return
    }
    val p2List = getDiagonalList(p2)
    val target = p1List.first { p2List.contains(it) }
    bw.write("2 ${p1.y.toChessString()} ${p1.x} ${target.y.toChessString()} ${target.x} ${p2.y.toChessString()} ${p2.x}\n")
}

private fun getDiagonalList(p: Point): List<Point> {
    return getUpDiagonalList(p) + getDownDiagonalList(p)
}

//증가하는 대각선
private fun getUpDiagonalList(p: Point): List<Point> {
    val diagonalList = mutableListOf<Point>()
    for (i in 1..8){
        val p1 = Point(p.y +1*i, p.x +1*i)
        val p2 = Point(p.y -1*i, p.x -1*i)
        if(isInMap(p1)) diagonalList.add(p1)
        if(isInMap(p2)) diagonalList.add(p2)
    }
    return diagonalList
}

//감소하는 대각선
private fun getDownDiagonalList(p: Point): List<Point> {
    val diagonalList = mutableListOf<Point>()
    for (i in 1..8){
        val p1 = Point(p.y -1*i, p.x +1*i)
        val p2 = Point(p.y +1*i, p.x -1*i)
        if(isInMap(p1)) diagonalList.add(p1)
        if(isInMap(p2)) diagonalList.add(p2)
    }
    return diagonalList
}

private fun isInMap(p: Point) =
    (p.y in 1..8) && (p.x in 1..8)

//ABCDEFGH
//12345678
private fun String.toChessInt(): Int {
    return when (this) {
        "A" -> 1; "B" -> 2
        "C" -> 3; "D" -> 4
        "E" -> 5; "F" -> 6
        "G" -> 7; "H" -> 8
        else -> throw java.lang.Exception()
    }
}

private fun Int.toChessString(): String {
    return when (this) {
        1 -> "A"; 2 -> "B"
        3 -> "C"; 4 -> "D"
        5 -> "E"; 6 -> "F"
        7 -> "G"; 8 -> "H"
        else -> throw java.lang.Exception()
    }
}

data class Point(
    val y: Int,
    val x: Int
)