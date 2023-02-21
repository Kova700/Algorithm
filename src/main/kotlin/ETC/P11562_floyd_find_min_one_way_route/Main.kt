package ETC.P11562_floyd_find_min_one_way_route

import java.io.*

private var N = 0 //건물의 수 (n ≤ 250)
private var M = 0 //길의 수 (m ≤ n * (n - 1) / 2 )
private var K = 0 //질문 수 (1 ≤ k ≤ 30,000)
private val bw = BufferedWriter(OutputStreamWriter(System.out))
private lateinit var graph: Array<IntArray>
private const val INF = Int.MAX_VALUE / 4

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P11562_floyd_find_min_one_way_route/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    br.readLine().split(" ").map { it.toInt() }.apply {
        N = this[0]
        M = this[1]
    }
    graph = Array(N + 1) { IntArray(N + 1) { INF } }
    repeat(N){ graph[it+1][it+1] = 0 }

    for(i in 1..M) {
        //(1 ≤ u ≤ n), (1 ≤ v ≤ n), (u != v), (b = 0 또는 1)
        val (u, v, b) = br.readLine().split(" ").map { it.toInt() }
        if(b == 0) {
            graph[u][v] = 0
            graph[v][u] = 1
            continue
        }
        graph[u][v] = 0
        graph[v][u] = 0
    }
    floyd()

    K = br.readLine().toInt()
    repeat(K) {
        //(1 ≤ s ≤ n) , (1 ≤ e ≤ n)
        val (s, e) = br.readLine().split(" ").map { it.toInt() }
        bw.write("${graph[s][e]}\n" )
    }

    bw.flush()
    bw.close()

}
private fun floyd(){
    for (k in 1..N){
        for (i in 1..N){
            for (j in 1..N){
                if (i == k || k == j || i == j) continue // 비교 값이 같기 떄문에 Pass(검사 필요성이 없음)
                val cost = graph[i][k] + graph[k][j]
                if (cost < graph[i][j]){
                    graph[i][j] = cost
                    continue
                }
            }
        }
    }
}