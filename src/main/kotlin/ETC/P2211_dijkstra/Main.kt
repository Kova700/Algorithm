package ETC.P2211_dijkstra

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

private var N = 0 //컴퓨터 개수 (1 ≤ N ≤ 1,000)
private var M = 0 //회선의 정보 개수
private val PQ = PriorityQueue<Node>()
private lateinit var distanceList : MutableList<Int>
private lateinit var graph :List<MutableList<Node>>
private lateinit var connectedList : MutableList<Int>
private val answerList = mutableListOf<String>()
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2211_dijkstra/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()
    distanceList = MutableList(N+1){Int.MAX_VALUE} //0번 인덱스 사용안함
    connectedList = MutableList(N+1){0} //0번 인덱스 사용안함
    graph = List(N+1){ mutableListOf() } //0번 인덱스 사용안함
    
    repeat(M){
        val (a,b,c) = br.readLine().split(" ").map { it.toInt() }
        graph[a].add(Node(b,c))
        graph[b].add(Node(a,c))
    }

    distanceList[0] = 0
    distanceList[1] = 0
    PQ.add(Node(1,0))
    dijkstra()

    for (i in connectedList.indices) {
        if (connectedList[i] == 0) continue
        answerList.add("$i ${connectedList[i]}")
    }
    println(answerList.size)
    answerList.forEach { println(it) }
}
private fun dijkstra(){
    while (PQ.isNotEmpty()){
        val node = PQ.poll()
        //최단거리가 아닌 루트면 continue
        if (distanceList[node.num] < node.time) continue
        //연결된 간선 순회
        for (nextNode in graph[node.num]){
            val cost = node.time + nextNode.time
            if (cost < distanceList[nextNode.num]){
                distanceList[nextNode.num] = cost
                connectedList[nextNode.num] = node.num
                PQ.add(Node(nextNode.num,cost))
            }
        }
    }
}

data class Node(
    val num :Int,
    val time :Int, //(1 ≤ time ≤ 10)
) :Comparable<Node> {
    override fun compareTo(other: Node): Int = this.time - other.time
}