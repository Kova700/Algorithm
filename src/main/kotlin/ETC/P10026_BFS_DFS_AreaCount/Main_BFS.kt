package ETC.P10026_BFS_DFS_AreaCount

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.Queue

private val MY = arrayOf(0,0,1,-1) //좌 ,우 ,위 ,아래
private val MX = arrayOf(-1,1,0,0) //좌 ,우 ,위 ,아래

private var N = 0
private lateinit var map :List<MutableList<Char>>
private lateinit var isVisited :List<MutableList<Boolean>>
private var count = 0
private var answerList = mutableListOf<Int>()
private val Q :Queue<Point> = LinkedList()

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P10026_BFS_DFS_AreaCount/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    map = List(N){br.readLine().toCharArray().toMutableList()}
    isVisited = List(N){ MutableList(N){false} }

    repeat(2){
        for (i in map.indices){
            for (j in map[i].indices){
                if (isVisited[i][j] == false){
                    count++
                    Q.add(Point(i,j))
                    bfs()
                }
            }
        }
        answerList.add(count)
        isVisited = List(N){ MutableList(N){false} }
        count = 0
    }

    println(answerList.joinToString(" "))
}

private fun bfs(){
    while (Q.isNotEmpty()){
        val point = Q.poll()
        for (i in 0 until 4){
            val nextY = point.y + MY[i]
            val nextX = point.x + MX[i]

            if (isInMap(nextY, nextX) && (isVisited[nextY][nextX] == false) && (map[point.y][point.x] == map[nextY][nextX])){
                isVisited[nextY][nextX] = true
                Q.add(Point(nextY,nextX))
            }
        }
        if (map[point.y][point.x] == 'G') map[point.y][point.x] = 'R' //G가 보이면 R로 수정
    }
}

private fun isInMap(y :Int, x :Int) :Boolean =
    (y in 0 until N) && (x in 0 until N)

data class Point(
    val y :Int,
    val x :Int
)