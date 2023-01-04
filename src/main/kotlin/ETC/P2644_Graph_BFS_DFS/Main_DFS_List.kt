package ETC.P2644_Graph_BFS_DFS

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

private var N = 0 //사람 수
private var M = 0 //관계 수
private var P1 = 0 //사람 1
private var P2 = 0 //사람 2
private lateinit var graph : List<MutableList<Int>>
private lateinit var isVisited : MutableList<Boolean>
private var answer = -1

private fun main(){
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
    dfs(P1,P2,0)

    println(answer)
}

private fun dfs(
    num :Int,
    target :Int,
    count :Int
){
    //체크인
    isVisited[num] = true
    //목적지인가?
    if (num == target) {
        answer = count
        return
    }
    //연결된 곳 울 순회
    for (i in graph[num]){
        //갈 수 있는가?
        if (!isVisited[i]){
            //간다.
            dfs(i,target,count+1)
        }
    }
    //체크아웃 (여기선 굳이 안해도 됨)
    // 배열 형식의 지도가 아니라 
    // 트리 구조의 그래프라서 한 분기가 밟은 땅을
    // 다른 분기가 밟을 수 가 없기 때문
    // 만약 배열 형식의 지도를 준다면 
    // 각 분기가 밟을 수 있는 땅이 중복됨으로 꼭 체크아웃을 해줘야함
    isVisited[num] = false
}