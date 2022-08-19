package ETC.P9663

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
lateinit var stack :ArrayList<Int>

var N = 0
var count = 0
fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P9663/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt() //1~14
    stack = ArrayList<Int>()

    for (i in 0 until N){
        dfs(0,i)
    }

    println(count)
}

private fun dfs(
    row :Int,
    col :Int
){
    //체크인
    stack.add(col)
    //목적지인가? -> row = N -1
    if (row == N-1) {
        count++
        stack.removeLast() //체크아웃
        return
    }
    //연결된 곳 순회
    for (i in 0 until N){
        //갈 수 있는가? -> 다른 퀸의 대각 ,좌우, 상하로 부터 안전한가
        if (checkQueen(row +1,i)){
            //간다.
            dfs(row +1,i)
        }
    }
    //체크아웃
    stack.removeLast()
}

private fun checkQueen(
    row :Int,
    col :Int
):Boolean{
    for ((index , queenCol) in stack.withIndex()) {
        //상하
        if (queenCol == col) return false
        //대각(기울기 1, -1)
        if(Math.abs(queenCol - col) == Math.abs(index - row)) return false
    }
    return true
}