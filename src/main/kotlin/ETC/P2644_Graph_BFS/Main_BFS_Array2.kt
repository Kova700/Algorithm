package ETC.P2644_Graph_BFS

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayDeque

private var N = 0 //사람 수
private var M = 0 //관계 수
private var P1 = 0 //사람 1
private var P2 = 0 //사람 2
private lateinit var graph : Array<Array<Int>>
private lateinit var isVisited : Array<Boolean>
private var count = 0

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2644_Graph_BFS/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    val st = StringTokenizer(br.readLine())
    P1 = st.nextToken().toInt(); P2 = st.nextToken().toInt()
    M = br.readLine().toInt()
    isVisited = Array(N+1){false}

    graph = Array(N+1){ Array(N+1){0} }
    for (i in 0 until M){
        val (a,b) = br.readLine().split(' ').map { it.toInt() }
        graph[a][b] = 1
        graph[b][a] = 1
    }
    bfs(P2)
}

private fun bfs(target :Int){
    var node = target
    val Q = ArrayDeque<Int>()
    Q.add(P1)
    isVisited[P1] = true

    while (Q.isNotEmpty()){
        for(i in 1..Q.size){
            node = Q.removeFirst()

            if (node == target){
                return println(count)
            }

            for (i in graph[node].indices) {
                if((graph[node][i] == 1) && !isVisited[i]){
                    isVisited[i] = true
                    Q.add(i)
                }
            }
        }
        count++
    }
    println(-1)
}