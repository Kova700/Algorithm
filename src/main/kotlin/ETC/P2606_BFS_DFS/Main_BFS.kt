package ETC.P2606_BFS_DFS

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.Queue

private var N = 0 //컴퓨터 수
private var M = 0 //관계 수
private lateinit var graph : List<MutableList<Int>>
private lateinit var isVisited : MutableList<Boolean>
private val Q :Queue<Int> = LinkedList()

private var count = 0

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2606_BFS_DFS/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    M = br.readLine().toInt()
    graph = List(N+1){ mutableListOf<Int>() }
    isVisited = MutableList(N+1){ false }

    //인접 리스트 방식
    for (i in 0 until M){
        val (a,b) = br.readLine().split(' ').map { it.toInt() }
        graph[a].add(b)
        graph[b].add(a)
    }

    isVisited[1] = true
    graph[1].forEach {
        Q.add(it)
        isVisited[it] = true
        count++
    }

    bfs()
    println(count)
}

private fun bfs(){
    while (Q.isNotEmpty()){
        //큐에서 꺼낸다.
        val point = Q.poll()
        //목적지 인가? -> 없음
        //연결된 곳 순회
        for (i in graph[point]) {
            //갈 수 있는가? -> 방문한적이 없는 곳
            if(isVisited[i] == false){
                //간다.(체크인)
                isVisited[i] = true
                count++
                Q.add(i)
            }
        }
    }

}