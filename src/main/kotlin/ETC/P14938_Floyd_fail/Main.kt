package ETC.P14938_Floyd_fail

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0 //지역의 개수 (1 ≤ n ≤ 100)
private var M = 0 //수색범위 (1 ≤ m ≤ 15)
private var R = 0 //길의 개수 (1 ≤ r ≤ 100)
private const val INF = Int.MAX_VALUE / 2
private lateinit var itemMap :List<Int>
private lateinit var weight :List<MutableList<Int>>

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P14938_Floyd_fail/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    br.readLine().split(" ").apply {
        N = this[0].toInt()
        M = this[1].toInt()
        R = this[2].toInt()
    }
    itemMap = listOf(0) + br.readLine().split(" ").map { it.toInt() }
    weight = List(N+1){ MutableList(N+1){ INF } }
    repeat(N) { weight[it+1][it+1] = 0} //자신과 연결된 weight 점은 0

    repeat(R){
        val (a,b,c) = br.readLine().split(" ").map { it.toInt() }
        weight[a][b] = c
        weight[b][a] = c
    }

    floyd()
    getMaxItemCount()
}

private fun floyd() {
    for (k in 1..N){
        for (i in 1..N){
            for (j in 1..N){
                val cost = weight[i][k] + weight[k][j]
                if (cost < weight[i][j]) weight[i][j] = cost
            }
        }
    }
}

private fun getMaxItemCount(){
    var max = 0
    for (i in 1..N){
        var sum = 0
        for (j in 1..N){
            if (weight[i][j] <= M){
                sum += itemMap[j]
            }
        }
        if (max < sum) max = sum
    }
    println(max)
}

//각 지역은 일정한 길이 l (1 ≤ l ≤ 15)의 길로 다른 지역과 연결되어 있고
//이 길은 양방향 통행이 가능하다.
//예은이는 낙하한 지역을 중심으로 거리가 수색 범위 m (1 ≤ m ≤ 15) 이내의
//모든 지역의 아이템을 습득 가능하다

//예은이가 얻을 수 있는 아이템의 최대 개수를 알려주자.