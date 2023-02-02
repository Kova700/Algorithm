package ETC.P1719_Floyd_Dijkstra

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.PriorityQueue
import java.util.StringTokenizer

private var N = 0 //집하장의 개수 (200이하의 자연수)
private var M = 0 //집하장간 경로의 개수 (10000이하의 자연수)
//경로간 소요시간은 1000이하의 자연수
private val bw = BufferedWriter(OutputStreamWriter(System.out))
private lateinit var graph : List<MutableList<Node>>
private lateinit var minCostList :MutableList<Int>

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1719_Floyd_Dijkstra/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()
    graph = List(N+1){ mutableListOf() }

    repeat(M){
        val (a,b,c) = br.readLine().split(" ").map { it.toInt() }
        graph[a].add(Node(b,c))
        graph[b].add(Node(a,c))
    }

    for (i in 1..N){
        bw.write(dijkstra(Node(i,0)).joinToString(" ") + "\n")
    }

    bw.flush()
    bw.close()
}
private fun dijkstra(startNode :Node) :MutableList<String>{
    val PQ = PriorityQueue<Node>()
    PQ.add(startNode)
    minCostList = MutableList(N+1){ Int.MAX_VALUE }
    val minRouteList = MutableList(N+1){""}
    minRouteList[startNode.num] = "-"

    while (PQ.isNotEmpty()){
        val node = PQ.poll()
        if (minCostList[node.num] < node.cost) continue
        for (nextNode in graph[node.num]){
            val nextCost = node.cost + nextNode.cost
            if (nextCost < minCostList[nextNode.num]){
                val firstNum = node.firstNum ?: nextNode.num
                if (nextNode.num != startNode.num) minRouteList[nextNode.num] = firstNum.toString()
                minCostList[nextNode.num] = nextCost
                PQ.add(Node(nextNode.num, nextCost, firstNum))
                minCostList
            }
        }
    }
    return minRouteList.drop(1).toMutableList() // 0번 인덱스 버림
}

data class Node(
    val num :Int,
    val cost :Int,
    val firstNum :Int? = null
) :Comparable<Node> {
    override fun compareTo(other: Node): Int
        = this.cost - other.cost //오름차순
}