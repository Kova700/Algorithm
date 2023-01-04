package ETC.P2644_Graph_BFS_DFS

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayDeque

private var N = 0 //사람 수
private var M = 0 //관계 수
private var P1 = 0 //사람 1
private var P2 = 0 //사람 2
private lateinit var graph : List<MutableList<Int>>
private lateinit var isVisited : MutableList<Boolean>
private var count = -1

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2644_Graph_BFS/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    val st = StringTokenizer(br.readLine())
    P1 = st.nextToken().toInt(); P2 = st.nextToken().toInt()
    M = br.readLine().toInt()
    isVisited = MutableList(N+1){ false }

    //인접리스트 방식
    graph = List(N+1){ mutableListOf<Int>() }
    for (i in 0 until M){
        val (a,b) = br.readLine().split(' ').map { it.toInt() }
        graph[a].add(b)
        graph[b].add(a)
    }
    bfs(P2)

    println(count)
}

private fun bfs(target :Int){
    val Q = ArrayDeque<Node>()
    Q.add(Node(P1,0))
    isVisited[P1] = true

    while (Q.isNotEmpty()){
        //큐에서 꺼냄
        val node = Q.removeFirst()
        //목적지 인가?
        if (node.num == target){
            count = node.count
            break
        }
        //연결된 곳을 순회
        for (i in graph[node.num]) {
            //갈 수 있는가? -> 방문한 적이 없는 곳
            if(!isVisited[i]){
                //체크인 (간다.)
                isVisited[i] = true
                //큐에 넣음
                Q.add(Node(i,node.count +1))
            }
        }

    }
}
data class Node(
    val num :Int,
    val count :Int
)
