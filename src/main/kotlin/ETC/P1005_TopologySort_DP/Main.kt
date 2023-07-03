package ETC.P1005_TopologySort_DP

import java.io.FileInputStream
import java.util.*

private var T = 0 //테스트케이스의 개수
private var N = 0 //건물 개수 N (2 ≤ N ≤ 1000)
private var K = 0 //건물간의 건설순서 규칙의 총 개수 K (1 ≤ K ≤ 100,000)
private lateinit var times: IntArray //각 건물당 건설에 걸리는 시간 (0 ≤ times[i] ≤ 100,000인 정수)
private lateinit var dp: IntArray // 각 건물을 짓는 시간 최대값
private lateinit var connections: Array<MutableList<Int>>  //각 건물의 인접 리스트
private lateinit var pointCount: IntArray //각 건물 진입차수 (건물을 가리키는 수)

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1005_TopologySort_DP/input"))
    val br = System.`in`.bufferedReader()
    T = br.readLine().toInt()

    repeat(T) {
        var st = StringTokenizer(br.readLine())
        N = st.nextToken().toInt()
        K = st.nextToken().toInt()

        dp = IntArray(N)
        times = IntArray(N)
        connections = Array(N) { mutableListOf() }
        pointCount = IntArray(N)

        st = StringTokenizer(br.readLine())
        for (i in 0 until N) {
            times[i] = st.nextToken().toInt()
        }

        for (i in 0 until K) {
            //X를 지은 다음에 건물 Y를 짓는 것이 가능하다는 의미 (1 ≤ X, Y, W ≤ N)
            val (X, Y) = br.readLine().split(" ").map { it.toInt() }
            connections[X - 1].add(Y - 1)
            pointCount[Y - 1]++
        }
        //승리하기 위해 건설해야 할 건물의 번호 W
        val W = br.readLine().toInt()

        topologySort()
        println(dp[W - 1])
    }
}

private fun topologySort() {
    val Q: Queue<Int> = LinkedList()

    for (i in 0 until N) {
        if (pointCount[i] == 0) {
            dp[i] = times[i]
            Q.add(i)
        }
    }

    while (Q.isNotEmpty()) {
        val current = Q.poll()

        for (i in connections[current].indices) {
            val next = connections[current][i]
            dp[next] = maxOf(dp[next], dp[current] + times[next])
            pointCount[next]--
            if (pointCount[next] == 0) Q.add(next)
        }
    }
}