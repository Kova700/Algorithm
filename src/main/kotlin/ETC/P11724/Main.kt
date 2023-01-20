package ETC.P11724

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

private var N = 0 //(1 ≤ N ≤ 1,000, 0 ≤ M ≤ N×(N-1)/2)
private var M = 0
private lateinit var graph : List<MutableList<Int>>
private val Q :Queue<Int> = LinkedList()

private var count = 0
private lateinit var isVisited :BooleanArray
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P11724/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()

    isVisited = BooleanArray(N+1){false}
    //인접 행렬 방식
    graph = List(N+1){ MutableList(N+1){0} }
    repeat(M){
        val (a,b) = br.readLine().split(" ").map { it.toInt() }
        graph[a][b] = 1
        graph[b][a] = 1
    }

    for (r in graph.indices) {
        if (r == 0) continue
        for (c in graph[r].indices){
            if (graph[r][c] == 1){
                isVisited[r] = true
                graph[r][c] = 0
                Q.add(c)
                count++
                bfs()
            }
        }
    }

    //count + 관계가 연결되지 않은 노드의 개수
    val notConnectedNodeCount = isVisited.filter { it == false }.count() - 1 // -1 = 0번째 노드
    println(count + notConnectedNodeCount)
}

private fun bfs(){
    while (Q.isNotEmpty()){
        val num = Q.poll()
        for (i in graph[num].indices){
            if (graph[num][i] == 1){
                graph[num][i] = 0
                isVisited[num] = true
                Q.add(i)
            }
        }
    }
}