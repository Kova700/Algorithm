package ETC.P1238

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

private var N = 0 // (1 ≤ N ≤ 1,000) //마을, 학생의 수
private var M = 0 // (1 ≤ M ≤ 10,000) // 도로의 개수
private var X = 0 // (1 ≤ X ≤ N) // 모이기로한 마을
//각 도로간 걸리는 시간 T (1 ≤ T ≤ 100)
private lateinit var map : List<MutableList<Node>>
private lateinit var answerList :MutableList<Int>
private lateinit var minimumTimeList :MutableList<Int>
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1238/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()
    X= st.nextToken().toInt()
    map = List(N+1){ mutableListOf() }
    minimumTimeList = MutableList(N+1){ Int.MAX_VALUE }
    answerList = MutableList(N+1){0}

    repeat(M){
        val(a,b,c) = br.readLine().split(" ").map{ it.toInt() }
        map[a].add(Node(b,c))
    }

    //각 지점에서 X로 가는 최단거리
    for (i in 1 .. N){
        if (i == X) continue // X에서 X까지의 최단거리는 0임으로
        dijkstra(i)
        answerList[i] += minimumTimeList[X]
        minimumTimeList = MutableList(N+1){ Int.MAX_VALUE }
    }

    //X에서 각 지점으로 돌아가는 최단거리
    dijkstra(X)
    for (i in 1 ..N){
        if (i == X) continue
        answerList[i] += minimumTimeList[i]
    }

    println(answerList.maxOf { it })
}

private fun dijkstra(start :Int){
    val PQ = PriorityQueue<Node>()
    PQ.add(Node(start,0))
    while(PQ.isNotEmpty()){
        val node = PQ.poll()
        if (minimumTimeList[node.num] < node.time) continue
        for (nextNode in map[node.num]){
            val cost = node.time + nextNode.time
            if (cost < minimumTimeList[nextNode.num]){
                minimumTimeList[nextNode.num] = cost
                PQ.add(Node(nextNode.num,cost))
            }
        }
    }
}

data class Node(
    val num: Int,
    val time :Int
) :Comparable<Node> {
    override fun compareTo(other: Node): Int =
        this.time - other.time
}