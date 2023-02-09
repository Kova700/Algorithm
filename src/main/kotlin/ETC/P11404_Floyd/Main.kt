package ETC.P11404_Floyd

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0 //도시의 개수 (2 ≤ n ≤ 100)
private var M = 0 //버스의 개수 (1 ≤ m ≤ 100,000)

private const val INF = Int.MAX_VALUE / 4
private lateinit var costList :List<MutableList<Int>>

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P11404_Floyd/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    M = br.readLine().toInt()
    costList = List(N+1){ MutableList(N+1){ INF } }
    repeat(N) { costList[it+1][it+1] = 0} //자기 자신 cost = 0

    repeat(M){
        // 시작 도시 a, 도착 도시 b, 비용 c (c는 100,000보다 작거나 같은 자연수)
        val (a,b,c) = br.readLine().split(" ").map { it.toInt() }
        costList[a][b] = minOf(costList[a][b], c)
    }

    floyd()

    val answerList = costList.drop(1)
        .map { it.drop(1)
            .map { i -> if (i == INF) 0 else i } } //0번 인덱스 삭제 + 남은INF 0처리

    answerList.forEach { println(it.joinToString(" ")) }

}

private fun floyd(){
    for (k in 1..N){
        for (i in 1..N){
            for (j in 1..N){
                val cost = costList[i][k] + costList[k][j]
                if (cost < costList[i][j]) costList[i][j] = cost
            }
        }
    }
}