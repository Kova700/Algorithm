package ETC.P12865_2DArray_DP_Hard

import java.io.FileInputStream
import java.util.*

private var N = 0 //물품의 수 (1 ≤ N ≤ 100)
private var K = 0 //버틸 수 있는 무게 (1 ≤ K ≤ 100,000)
private lateinit var dp: Array<IntArray>
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P12865_2DArray_DP_Hard/input"))
    val br = System.`in`.bufferedReader()

    var st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    K = st.nextToken().toInt()
    dp = Array(N + 1) { IntArray(K + 1) }

    repeat(N) {
        //물건의 무게 (1 ≤ W ≤ 100,000)
        //물건의 가치 (0 ≤ V ≤ 1,000)
        val (W, V) = br.readLine().split(" ").map { i -> i.toInt() }

        //dp[i][j] == i번 째 물품을 담을까 고민하고(담거나, 담지않거나), j무게가 되었을 때, 최대 가치
        // i번째 물품의 무게가 j무게보다 높다면 이전에 담았던 무게의 가치와 같음 (현재 물품을 담지 못하니까)
        // i번째 물품의 무게가 j무게보다 낮다면
        // [현재 가치(V) + 이전 물품들로 쌓았던 j-W무게의 최대가치] 와 [이전 물품들로 쌓았던 j무게의 가치] 중 더 높은 가치를 사용
        for (j in 1..K) {
            if (W > j) {
                dp[it + 1][j] = dp[it][j]
                continue
            }
            dp[it + 1][j] = maxOf(V + dp[it][j - W], dp[it][j])
        }
    }
    println(dp[N][K])
}