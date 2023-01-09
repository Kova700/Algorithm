package ETC.P10451

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var T = 0 // 예제 수
private lateinit var graphList :MutableList<MutableList<Int>>
private lateinit var isChecked : MutableList<MutableList<Boolean>>
private lateinit var answerList : MutableList<Int>

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P10451/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    T = br.readLine().toInt()

    answerList = MutableList(T){0}
    graphList = mutableListOf()
    isChecked = mutableListOf()
    repeat(T){
        val N = br.readLine().toInt() //순열의 크기 N (2 ≤ N ≤ 1,000)
        isChecked.add(MutableList(N+1){false})
        val graph = MutableList(N+1){0}
        val dataList = mutableListOf(0).also {
            it.addAll(br.readLine().split(" ").map { it.toInt() })
        }
        //인접리스트 방식
        for (i in dataList.indices){
            graph[i] = dataList[i]
        }
        graphList.add(graph)
    }

    for ((row,graph) in graphList.withIndex()) {
        for (i in graph.indices){
            if (i == 0) continue
            dfs(row, graph[i], i)
        }
    }

    answerList.forEach {
        println(it)
    }
}

private fun dfs(
    row :Int, //graphList Index
    nowNum :Int,
    target :Int
){
    //체크인
    isChecked[row][nowNum] = true
    //목적지 인가?
    if (nowNum == target) {
        answerList[row]++
        return
    }
    //연결된 곳 순회
    val nextNum = graphList[row][nowNum] //순회인 경우 StackOverFlow 걸림
    //갈 수 있는가?
    if ((isChecked[row][nextNum] == false)){
        //간다.
        dfs(row,nextNum,target)
    }
    //체크아웃 -> 굳이 필요하지 않음
}