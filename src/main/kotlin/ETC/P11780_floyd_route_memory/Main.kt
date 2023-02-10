package ETC.P11780_floyd_route_memory

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

private var N = 0 // (1 ≤ n ≤ 100)
private var M = 0 // (1 ≤ m ≤ 100,000)
private const val INF = Int.MAX_VALUE / 4

private lateinit var minCostList :List<MutableList<Int>>
private lateinit var minCostRouteList :List<MutableList<List<Int>>>
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P11780_floyd_route_memory/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    N = br.readLine().toInt()
    M = br.readLine().toInt()
    minCostList = List(N+1){ MutableList(N+1){ INF } }
    minCostRouteList = List(N+1){ MutableList(N+1){ listOf() } }

    repeat(N){ minCostList[it+1][it+1] = 0 }

    repeat(M){
        //비용은 100,000보다 작거나 같은 자연수
        val (a,b,c) = br.readLine().split(" ").map { it.toInt() }
        minCostList[a][b] = minOf(minCostList[a][b], c)
        minCostRouteList[a][b] = listOf(a,b)
    }

    floyd()

    minCostList.drop(1)
        .map { it.drop(1)
            .map { i -> if (i == INF) 0 else i } } //0번 인덱스 삭제 + 남은INF 0처리
        .forEach { bw.write("${it.joinToString(" ")}\n") }

    minCostRouteList.drop(1).forEach { line->
        line.drop(1).forEach { col ->
            bw.write("${col.size} ")
            bw.write("${col.joinToString(" ")}\n")
        }
    }

    bw.flush()
    bw.close()
}

private fun floyd(){
    for (k in 1..N){
        for (i in 1..N){
            for (j in 1..N){
                val cost = minCostList[i][k] + minCostList[k][j]
                if (cost < minCostList[i][j]) {
                    //지나왔던길 , 비용 갱신
                    minCostRouteList[i][j] = minCostRouteList[i][k].dropLast(1) + minCostRouteList[k][j]
                    minCostList[i][j] = cost
                }
            }
        }
    }
}