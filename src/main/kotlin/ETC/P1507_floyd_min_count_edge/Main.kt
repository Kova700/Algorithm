package ETC.P1507_floyd_min_count_edge

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0 //(1 ≤ N ≤ 20)
private lateinit var graph: Array<IntArray>
private lateinit var graph2: Array<IntArray>
private var answer = 0

/** #배운점
 * i -> j 간선의 비용과 i -> k -> j의 비용이 같아야 플로이드가 잘 적용된 최단거리이다.
 * 또한, 간선의 개수가 최소이길 원한다면, 최대한 재활용하는 방식의 i -> k -> j인 간선만
 * 남겨두고, i -> j 처럼 direct로 가는 간선은 지워주는 방향으로 구현하면 된다.
*/

//시간은 2500보다 작거나 같은 자연수이다.
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1507_floyd_min_count_edge/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()

    graph = Array(N) { br.readLine().split(" ").map { it.toInt() }.toIntArray() }
    graph2 = Array(N) { graph[it].copyOf() }

    floyd()

    if (answer != -1){
        for (i in 0 until N){
            for (j in 0 until i){
                answer += graph2[i][j]
            }
        }
    }

    println(answer)
}

private fun floyd() {
    for (k in 0 until N) {
        for (i in 0 until N) {
            for (j in 0 until N) {
                if (i == k || k == j) continue  // i -> k -> j 인경우에 k는 i와 j와 달라야 유효한 비교가 된다.

                //해당 지점을 거쳐가는 비용이 그냥 direct로 가는 비용과 같다면 
                //direct로 가는 간선 제거 (비용 0처리)
                val cost = graph[i][k] + graph[k][j] 
                if (graph[i][j] == cost) {
                    graph2[i][j] = 0
                    continue
                }
                //해당 지점을 거쳐가는 비용이 direct로 가는 비용보다 작다면
                //강호가 잘못된 최소 이동시간 표를 넘겨준 것임으로 -1 출력
                //(잘못된 플로이드 와샬 표가 입력값으로 주어진 경우)
                if (graph[i][j] > cost) {
                    answer = -1
                    return
                }
            }
        }
    }
}