package ETC.P2178

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

private val MY = arrayOf(0,1,0,-1) //좌 ,우 ,위 ,아래
private val MX = arrayOf(1,0,-1,0) //좌 ,우 ,위 ,아래
private var N = 0
private var M = 0
private lateinit var map : List<MutableList<Int>>
private lateinit var isVisited :List<MutableList<Boolean>>
private val Q :Queue<Point> = LinkedList()

private var answer = 0
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2178/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()
    map = List(N){ br.readLine().map { it.digitToInt() }.toMutableList() }
    isVisited = List(N){MutableList(M){false}}

    Q.add(Point(0,0,1))
    isVisited[0][0] = true
    bfs()
    println(answer)
}

private fun bfs(){
    while (Q.isNotEmpty()){
        //큐에서 꺼낸다.
        val point = Q.poll()
        //목적지 인가?
        if ((point.y == N-1) && (point.x == M-1)){
            answer = point.count
            return
        }
        //연결된 곳 순회
        for (i in 0 until 4){
            val nextY = point.y + MY[i]
            val nextX = point.x + MX[i]
            //갈 수 있는가? -> 맵 내부 + 값이 1 + 가보지 않은 곳
            if (isInMap(nextY,nextX) && (map[nextY][nextX] == 1) && (isVisited[nextY][nextX] == false)){
                //체크인
                isVisited[nextY][nextX] = true
                Q.add(Point(nextY,nextX, point.count+1))
            }
        }
    }
}

private fun isInMap(
    y :Int,
    x :Int
) :Boolean{
    return (y in 0 until N) && (x in 0 until M)
}

data class Point(
    val y :Int,
    val x :Int,
    val count :Int
)