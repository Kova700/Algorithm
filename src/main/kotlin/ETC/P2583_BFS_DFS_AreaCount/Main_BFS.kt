package ETC.P2583_BFS_DFS_AreaCount

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.Queue
import java.util.StringTokenizer

private val MY = arrayOf(0,0,1,-1) //좌 ,우 ,위 ,아래
private val MX = arrayOf(-1,1,0,0) //좌 ,우 ,위 ,아래

//M, N, K는 모두 100 이하의 자연수
private var M = 0
private var N = 0
private var K = 0
private lateinit var map : List<MutableList<Boolean>>
private val Q :Queue<Point> = LinkedList()

private var areaCount = 0
private var squareCount = 0
private val squareCountList = mutableListOf<Int>()

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2583_BFS_DFS_AreaCount/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    M = st.nextToken().toInt()
    N = st.nextToken().toInt()
    K = st.nextToken().toInt()
    map = List(M){ MutableList(N){false} }

    val coloredAreaList = List(K){br.readLine().split(" ").map { it.toInt() }}
    
    //색칠된 영역 표시
    for (area in coloredAreaList) {
        //사각형의 중심을 좌표로 생각
        val xRange = area[0]until area[2]
        val yRange = area[1]until area[3]
        for(i in yRange){
            for (j in xRange){
                map[i][j] = true
            }
        }
    }

    for (i in map.indices){
        for (j in map[i].indices){
            if (map[i][j] == true) continue
            map[i][j] = true
            Q.add(Point(i,j))
            areaCount++
            squareCount++
            bfs()
        }
    }

    println(areaCount)
    println(squareCountList.sorted().joinToString(" "))
}

private fun bfs(){
    while (Q.isNotEmpty()){
        val point = Q.poll()
        for (i in 0 until 4) {
            val nextY = point.y + MY[i]
            val nextX = point.x + MX[i]
            if (isInMap(nextY, nextX) && (map[nextY][nextX] == false)){
                map[nextY][nextX] = true
                squareCount++
                Q.add(Point(nextY, nextX))
            }
        }
    }
    squareCountList.add(squareCount)
    squareCount = 0
}

private fun isInMap(y :Int, x :Int) :Boolean =
    (y in 0 until M) && (x in 0 until N)

data class Point(
    val y :Int,
    val x :Int
)