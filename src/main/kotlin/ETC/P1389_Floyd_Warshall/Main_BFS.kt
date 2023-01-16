package ETC.P1389_Floyd_Warshall

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.Queue
import java.util.StringTokenizer

private var M = 0 // 유저 수 (2 ≤ N ≤ 100)
private var N = 0 // 친구 관계 수 (1 ≤ M ≤ 5,000)
private lateinit var graph :List<MutableList<Int>>
private val answerList = mutableListOf<Person>()
private val Q :Queue<Person> = LinkedList()
private var count = 0

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1389/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    M = st.nextToken().toInt()
    N = st.nextToken().toInt()

    //인접 리스트 방식 (양방향)
    graph = List(M+1){ mutableListOf() }
    for(i in 1..N){
        val (a,b) = br.readLine().split(" ").map { it.toInt() }
        if (graph[a].contains(b)) continue //중복된 친구값 들어옴 방지
        graph[a].add(b)
        graph[b].add(a)
    }

    for (num in 1..M){
        for (target in 1..M){
            if(num == target) continue
            Q.add(Person(num,0))
            bfs(target)
        }
        answerList.add(Person(num, count))
        count = 0
    }

    answerList.sortBy { it.kevinCount }
    println(answerList.first().num)
}

private fun bfs(target :Int){
    while (Q.isNotEmpty()){
        val person = Q.poll()

        if (person.num == target){
            count += person.kevinCount
            Q.clear()
            return
        }

        for (i in graph[person.num]){
            Q.add(Person(i, person.kevinCount+1))
        }
    }
}

data class Person(
    val num :Int,
    val kevinCount :Int
)