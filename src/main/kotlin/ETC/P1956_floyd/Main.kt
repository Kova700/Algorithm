package ETC.P1956_floyd

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.min

/*배운점 :
그래프를 그릴 때
Array<IntArray> 와
List<MutableList<Int>>
시간차이가 1초가까이 났다
==> 노드 , 간선의 개수가 많을 수록 시간차이가 더욱 커지는 것같으니
앞으로 List 보단 Array를 활용하자.
* */

private var V = 0 // 마을 개수 (2 ≤ V ≤ 400)
private var E = 0 // 도로 개수 (0 ≤ E ≤ V(V-1))
private const val INF = Int.MAX_VALUE / 4
private lateinit var minCostList :Array<IntArray>

//최소 길이 사이클 찾기
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1956_floyd/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    br.readLine().split(" ").map { it.toInt() }.apply {
        V = this[0]
        E = this[1]
    }
    minCostList = Array(V+1){ IntArray(V+1){ INF } }

    repeat(E){
        //거리는 10000이하
        val (a,b,c) = br.readLine().split(" ").map{it.toInt()}
        minCostList[a][b] = c
    }

    floyd()

    var answer = INF
    for(i in 1..V){
        answer = min(minCostList[i][i], answer)
    }
    if (answer == INF) answer = -1
    println(answer)
}


private fun floyd(){
    for (k in 1..V){
        for (i in 1..V){
            for (j in 1..V){
                val cost = minCostList[i][k] + minCostList[k][j]
                if (cost < minCostList[i][j]) {
                    minCostList[i][j] = cost
                }
            }
        }
    }
}