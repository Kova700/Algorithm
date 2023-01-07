package ETC.P2606_BFS_DFS

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0 //컴퓨터 수
private var M = 0 //관계 수
private lateinit var graph : List<MutableList<Int>>
private lateinit var isVisited : MutableList<Boolean>

private var count = 0

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2606_BFS_DFS/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    M = br.readLine().toInt()
    graph = List(N+1){ MutableList(N+1){0} }
    isVisited = MutableList(N+1){ false }

    //인접행렬 방식
    for (i in 0 until M){
        val (a,b) = br.readLine().split(' ').map { it.toInt() }
        graph[a][b] = 1
        graph[b][a] = 1
    }

    dfs(1)

    println(count)
}

private fun dfs(now :Int){
    //체크인
    isVisited[now] = true
    //목적지인가? -> 없음

    //연결된 곳 순회
    for (i in graph[now].indices){
        //갈 수 있는가? -> 방문하지 않았던 곳 + 연결된 곳
        if ((graph[now][i] == 1) && (isVisited[i] == false)){
            //간다.
            count++
            dfs(i)
        }
    }
    //체크아웃 -> 한 분기가 연결된 곳을 다 찾아냄으로 굳이 할 필요 없음
}