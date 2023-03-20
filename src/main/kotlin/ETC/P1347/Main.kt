package ETC.P1347

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private val MY = arrayOf(1,0,-1,0)//남,서,북,동
private val MX = arrayOf(0,-1,0,1)//남,서,북,동
private var nY = 50
private var nX = 50
private var N = 0 //내용의 길이 (0보다 크고, 50보다 작다)
private lateinit var moveData :CharArray
private val map = Array(101){ CharArray(101){'#'} }
private var direction = 0
private var minY = nY
private var minX = nX
private var maxY = nY
private var maxX = nX
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1347/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    moveData = br.readLine().toCharArray()

    startMove()

    for (r in minY..maxY){
        for (c in minX..maxX){
            print("${map[r][c]}")
        }
        println()
    }
}
private fun startMove(){
    map[nY][nX] = '.'
    for (c in moveData){
        readData(c)
    }
}
private fun readData(c : Char){
    when(c){
        'L' -> { direction = if(direction -1 < 0) 3 else direction -1 }
        'R' -> { direction = if(direction +1 > 3) 0 else direction +1 }
        'F' -> {
            nY += MY[direction]
            nX += MX[direction]
            map[nY][nX] = '.'
            minY = Math.min(nY, minY)
            minX = Math.min(nX, minX)
            maxY = Math.max(nY, maxY)
            maxX = Math.max(nX, maxX)
        }
    }
}