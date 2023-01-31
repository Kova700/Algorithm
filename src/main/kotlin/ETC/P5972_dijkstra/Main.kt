package ETC.P5972_dijkstra

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.PriorityQueue
import java.util.StringTokenizer

private var N = 0 //헛간의 개수 (1 <= N <= 50,000)
private var M = 0 //소들의 길 (1 <= M <= 50,000)
// 각길에 있는 소의 수 C(0 <= C_i <= 1,000)
private lateinit var graph :List<MutableList<Node>>
private lateinit var minCostList :MutableList<Int>
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P5972_dijkstra/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()
    minCostList = MutableList(N+1){ Int.MAX_VALUE }
    graph = List(N+1){ mutableListOf() }
    repeat(M){
        val (a,b,c) = br.readLine().split(" ").map { it.toInt() }
        graph[a].add(Node(b,c))
        graph[b].add(Node(a,c))
    }
    minCostList[0] = 0
    minCostList[1] = 0
    dijkstra(Node(1,0))

    println(minCostList.last())
}

private fun dijkstra(startNode :Node){
    val PQ = PriorityQueue<Node>()
    PQ.add(startNode)
    while (PQ.isNotEmpty()){
        val node = PQ.poll()
        if (minCostList[node.num] < node.cost) continue
        for (nextNode in graph[node.num]){
            val nextCost = node.cost + nextNode.cost
            if (nextCost < minCostList[nextNode.num]){
                minCostList[nextNode.num] = nextCost
                PQ.add(Node(nextNode.num,nextCost))
            }
        }
    }
}

data class Node(
    val num :Int,
    val cost :Int
):Comparable<Node> {
    override fun compareTo(other: Node): Int
        = this.cost - other.cost // 오름차순
}