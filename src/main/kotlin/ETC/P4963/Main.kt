package ETC.P4963

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.Queue

private val MX = arrayOf(0,0,-1,1,-1,-1,1,1) //상,하,좌,우,좌상,좌하,우상,우하
private val MY = arrayOf(1,-1,0,0,1,-1,1,-1) //상,하,좌,우,좌상,좌하,우상,우하
private lateinit var isVisited : Array<BooleanArray>
private val answerList = mutableListOf<Int>()
private val Q :Queue<Point> = LinkedList()
private var count = 0

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P4963/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))

    while (true){
        val line = br.readLine()
        if (line == "0 0") break

        val (w,h) = line.split(" ").map{it.toInt()} //(1<= w,h <=50)
        val map = List(h){ br.readLine().split(" ").map { it.toInt() } }
        isVisited = Array(h){ BooleanArray(w){false} }
        for (r in map.indices) {
            for (c in map[r].indices){
                if ((map[r][c] == 1) && (isVisited[r][c] == false)){
                    isVisited[r][c] = true
                    count++
                    Q.add(Point(r,c))
                    bfs(map)
                }
            }
        }
        answerList.add( count )
        count = 0
    }

    //각 테스트 케이스에 대해서, 섬의 개수를 출력한다.
    answerList.forEach { println(it) }
}
private fun bfs(map :List<List<Int>>){
    while (Q.isNotEmpty()){
        val point = Q.poll()
        for (i in 0 until 8){
            val nextY = point.y + MY[i]
            val nextX = point.x + MX[i]
            if ( isInMap(nextY,nextX,map) && (map[nextY][nextX] == 1) && (isVisited[nextY][nextX] == false)){
                isVisited[nextY][nextX] = true
                Q.add(Point(nextY,nextX))
            }
        }
    }
}

private fun isInMap(y :Int, x :Int, map :List<List<Int>>) :Boolean{
    return (y in map.indices) && (x in map[0].indices)
}

data class Point(
    val y :Int,
    val x :Int
)