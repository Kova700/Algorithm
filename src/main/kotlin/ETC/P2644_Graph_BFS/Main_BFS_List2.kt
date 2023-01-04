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
private lateinit var graph : List<MutableList<Int>>
private lateinit var isVisited : MutableList<Boolean>
private var count = 0

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
}

//노드 객체로 Count를 세는게 아닌 같은 레벨의 탐색이 종료되었다면 count를 올려주는 방식
//강제로 한 레벨의 큐가 다 비워질때 까지 기다림
private fun bfs(target :Int){
    val Q = ArrayDeque<Int>()
    Q.add(P1)
    isVisited[P1] = true

    while (Q.isNotEmpty()){
        for(i in 1..Q.size){
            //큐에서 꺼냄
            val node = Q.removeFirst()
            //목적지 인가?
            if (node == target){
                println(count)
                return
            }
            //연결된 곳을 순회
            for (i in graph[node]) {
                //갈 수 있는가? -> 방문한 적이 없는 곳
                if(!isVisited[i]){
                    //체크인 (간다.)
                    isVisited[i] = true
                    //큐에 넣음
                    Q.add(i)
                }
            }
        }
        count++ // bfs 레벨파악을 위해 q의 사이즈만큼 반복하여 종료될 때 마다 count
    }
    println(-1)
}
