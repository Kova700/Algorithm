package ETC.P4485_dijkstra

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.PriorityQueue

private val MY = arrayOf(0,0,1,-1)//좌,우,위,아래
private val MX = arrayOf(-1,1,0,0)//좌,우,위,아래

private val PQ = PriorityQueue<Node>()
private lateinit var map :List<List<Int>>
private lateinit var distanceList :MutableList<Int>

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P4485_dijkstra/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))

    var count = 1
    while (true){
        val N = br.readLine().toInt() //  (2 ≤ N ≤ 125)
        if (N == 0) break
        map = List(N){br.readLine().split(" ").map { it.toInt() }}
        distanceList = MutableList(N * N){ Int.MAX_VALUE }

        distanceList[0] = 0
        PQ.add(Node(0,map[0][0]))
        dijkstra(N)

        println("Problem $count: ${distanceList.last()}")
        count++
    }

}

private fun dijkstra(N :Int){
    while (PQ.isNotEmpty()){
        val (curNum , curdist) = PQ.poll()
        if ((distanceList[curNum] < curdist) && curNum !=0 ) continue //이미 갱신된 기록상 최단거리가 아닌 곳은 패스

        val curY = curNum / N
        val curX = curNum % N
        //인접 노드 순회
        for (i in 0 until 4){
            val nextY = curY + MY[i]
            val nextX = curX + MX[i]
            //갈 수 있는가? -> 맵 안인가? , 방문하지 않은 곳인가?(현재
            if (isInMap(nextY,nextX,N)){
                val cost = curdist + map[nextY][nextX] // 다음 위치 비용
                val nextNodeNum = getPointNum(nextY,nextX,N)
                if (cost < distanceList[nextNodeNum]){
                    distanceList[nextNodeNum] = cost
                    PQ.add(Node(nextNodeNum,cost))
                }
            }
        }
    }
}
private fun isInMap(y :Int, x :Int, N :Int) :Boolean =
    (y in 0..N-1) && (x in 0..N-1)

private fun getPointNum(y :Int, x :Int, N :Int) :Int = y*N + x

data class Node(
    val pointNum :Int, // (pointNum/N , pointNum%N) == map에서의 좌표
    val distance :Int
) : Comparable<Node>{
    override fun compareTo(other: Node): Int = this.distance - other.distance // 오름차순 (값이 클 수록 뒤로 간다 생각하면됨)
}
