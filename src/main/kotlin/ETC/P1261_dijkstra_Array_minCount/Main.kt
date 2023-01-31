package ETC.P1261_dijkstra_Array_minCount

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.System
import java.util.*

private val MY = arrayOf(1,-1,0,0)//상하좌우
private val MX = arrayOf(0,0,-1,1)//상하좌우

private var M = 0 //가로 (1 ≤ N, M ≤ 100)
private var N = 0 //세로
private lateinit var map :List<MutableList<Int>>
private lateinit var distanceList :MutableList<Int>
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1261_dijkstra_Array_minCount/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    M = st.nextToken().toInt()
    N = st.nextToken().toInt()
    map = List(N){br.readLine().toCharArray().map { it.digitToInt() }.toMutableList() }
    distanceList = MutableList(N*M){Int.MAX_VALUE}
    distanceList[0] = 0
    dijkstra()

    println(distanceList.last())
}
private fun dijkstra(){
    val PQ = PriorityQueue<Node>()
    PQ.add(Node(0,0,0))
    while (PQ.isNotEmpty()){
        val node = PQ.poll()
        if (distanceList[node.num] < node.count) continue

        for (i in 0 until 4){
            val nextY = node.y + MY[i]
            val nextX = node.x + MX[i]
            if (isInMap(nextY,nextX)){
                val cost = if (map[nextY][nextX] == 0) node.count else node.count + 1
                if (cost < distanceList[getNodeNum(nextY,nextX)]){
                    distanceList[getNodeNum(nextY,nextX)] = cost
                    PQ.add(Node(nextY,nextX,cost))
                }
            }
        }
    }
}
private fun getNodeNum(y :Int, x:Int) :Int = y * M + x

private fun isInMap(y :Int, x:Int)
    = (y in 0 until N) && (x in 0 until M)

data class Node(
    val y :Int,
    val x :Int,
    val count :Int,
    val num :Int = y * M + x
): Comparable<Node> {
    override fun compareTo(other: Node): Int
        = this.count - other.count // 오름차순
}