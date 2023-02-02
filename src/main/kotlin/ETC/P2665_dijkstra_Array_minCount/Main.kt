package ETC.P2665_dijkstra_Array_minCount

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

private val MY = arrayOf(1,-1,0,0) //상하좌우
private val MX = arrayOf(0,0,-1,1) //상하좌우

private var N = 0 //(1 ≤ N ≤ 50)
private lateinit var minCostList :MutableList<Int>
private lateinit var map : List<MutableList<Int>>
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2665_dijkstra_Array_minCount/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    minCostList = MutableList(N*N){ Int.MAX_VALUE }
    minCostList[0] = 0
    //흰방 0 , 검은방 1로 재 구성
    map = List(N){br.readLine().toCharArray().map{if (it == '0') 1 else 0}.toMutableList()}
    dijkstra(Node(0,0,0))

    println(minCostList.last())
}

private fun dijkstra(start :Node){
    val PQ = PriorityQueue<Node>()
    PQ.add(start)
    while(PQ.isNotEmpty()){
        val node = PQ.poll()
        if (minCostList[node.num] < node.cost) continue
        for (i in 0 until 4){
            val nextY = node.y + MY[i]
            val nextX = node.x + MX[i]
            if (isInMap(nextY,nextX)){
                val cost = node.cost + map[nextY][nextX]
                if (cost < minCostList[getNodeNum(nextY,nextX)]){
                    PQ.add(Node(nextY,nextX,cost))
                    minCostList[getNodeNum(nextY,nextX)] = cost
                }
            }
        }
    }
}

private fun isInMap(y :Int, x :Int)
    = (y in 0 until N) && (x in 0 until N)

private fun getNodeNum(y :Int, x :Int)
    = N*y + x

data class Node(
    val y :Int,
    val x :Int,
    val cost :Int,
    val num :Int = N*y + x
): Comparable<Node> {
    override fun compareTo(other: Node): Int
        = this.cost - other.cost
}