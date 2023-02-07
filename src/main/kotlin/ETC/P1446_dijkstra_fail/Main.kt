package ETC.P1446_dijkstra_fail

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.math.min

private var N = 0 //지금길의 개수 (12 이하인 양의 정수)
private var D = 0 //고속도로의 길이 (10,000보다 작거나 같은 자연수)
private lateinit var minCostList : MutableList<Int>
private val PQ = PriorityQueue<Route>()

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1446_dijkstra_fail/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    D = st.nextToken().toInt()
    minCostList = MutableList(D+1){ it }

    repeat(N){
        //모든 위치와 길이는 10,000보다 작거나 같은 음이 아닌 정수 , (a < b)
        val (a,b,c) = br.readLine().split(" ").map { it.toInt() }
        PQ.add(Route(a,b,c))
    }
    dijkstra()
    println(minCostList[D])
}

private fun dijkstra(){
    while (PQ.isNotEmpty()){
        val route = PQ.poll()
        if (route.end > D) continue

        if (route.nomalCost <= route.cost) continue
        val cost = minCostList[route.start] + route.cost
        if (cost < minCostList[route.end]){
            for (i in route.end..D){
                minCostList[i] = min(cost + i - route.end, minCostList[i])
            }
        }
    }
}

data class Route(
    val start :Int,
    val end :Int,
    val cost :Int,
): Comparable<Route> {
    val nomalCost:Int = end - start

    override fun compareTo(other: Route): Int =
        this.start - other.start
}