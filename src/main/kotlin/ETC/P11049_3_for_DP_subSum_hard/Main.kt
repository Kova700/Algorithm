package ETC.P11049_3_for_DP_subSum_hard

import java.io.FileInputStream

private var N = 0 //행렬의 개수 (1 ≤ N ≤ 500)
private lateinit var matrix: Array<List<Int>>
private lateinit var dp: Array<IntArray> //dp[i][j] = i부터 j번까지 행렬을 곱할 때, 최소 곱셈연산 수

//11066번과 매우 유사
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P11049_3_for_DP_subSum_hard/input"))
    val br = System.`in`.bufferedReader()
    N = br.readLine().toInt()
    matrix = Array(N) { listOf(2) }
    dp = Array(N) { IntArray(N) }
    repeat(N) { i ->
        //(1 ≤ r, c ≤ 500)
        //항상 순서대로 곱셈을 할 수 있는 크기만 입력으로 주어진다.
        matrix[i] = br.readLine().split(" ").map { it.toInt() }
        //(r1, c1) , (r2, c2) 행렬 곱을 하려면
        //곱해지는 행렬의 열의 개수와 곱할 행렬의 행의 개수가 동일해야함 (c1 == r2를 만족해야함)
        //곱해진 행렬의 크기 = r1 x c2
        //곱하는데 필요한 곱셈 연산의 수 = r1 x r2 x c2 (c1 == r2)
    }

    for (j in 1 until N) { //~까지(목적지)
        for (i in j - 1 downTo 0) { // ~부터(출발지)
            dp[i][j] = Int.MAX_VALUE
            //i 부터 j 사이값 중 기준점을 잡고, 연산 수가 가장 작은 값 찾기 (중간값)
            for (k in i until j) {
                val multiply = matrix[i][0] * matrix[k][1] * matrix[j][1]
                dp[i][j] = minOf(dp[i][j], dp[i][k] + dp[k + 1][j] + multiply)
            }
        }
    }

    println(dp[0][N - 1])

    //행렬의수 = 1일때,
    //곱할 행렬이 없음으로 0출력

    //행렬의수 = 2일때,
    //1번행렬 x 2번행렬
    //행렬의 크기 : matrix[1][0] * matrix[2][1]
    //곱셈 연산 수(dp[1][2]) : matrix[1][0] * matrix[1][1] * matrix[2][1]

    //행렬의수 = 3일때,
    //① 1번행렬 x (2번행렬 x 3번행렬)
    //  행렬의 크기 : matrix[1][0] X matrix[3][1]
    //  곱셈 연산 수 : matrix[1][0] x matrix[1][1] x matrix[3][1]
    //  누적 감안 == dp[1][1] + dp[2][3] + matrix[1][0] x matrix[1][1] x matrix[3][1]
    //② (1번행렬 x 2번행렬) x 3번행렬
    //  행렬의 크기 : matrix[1][0] x matrix[3][1]
    //  곱셈 연산 수 : matrix[1][0] x matrix[2][1] x matrix[3][1]
    //  누적 감안 ==> dp[1][2] + dp[3][3] + matrix[1][0] x matrix[2][1] x matrix[3][1]
    //①, ② 두 가지 중에서 곱셈 연산의 수가 더 적은 값

    //행렬의수 = 4일때,
    //① 1번행렬 x (2번행렬 x 3번행렬 x 4번행렬)
    //  행렬의 크기 : matrix[1][0] x matrix[4][1]
    //  곱셈 연산 수 : matrix[1][0] x matrix[1][1] x matrix[4][1]
    //  누적 감안 ==> dp[1][1] + dp[2][4] + matrix[1][0] x matrix[1][1] x matrix[4][1]
    //② (1번행렬 x 2번행렬) x (3번행렬 x 4번행렬)
    //  행렬의 크기 : matrix[1][0] x matrix[4][1]
    //  곱셈 연산 수 : matrix[1][0] x matrix[2][1] x matrix[4][1]
    //  누적 감안 ==> dp[1][2] + dp[3][4] + matrix[1][0] x matrix[2][1] x matrix[4][1]
    //③ (1번행렬 x 2번행렬 x 3번행렬) x 4번행렬
    //  행렬의 크기 : matrix[1][0] x matrix[4][1]
    //  곱셈 연산 수 : matrix[1][0] x matrix[3][1] x matrix[4][1]
    //  누적 감안 ==> dp[1][3] + dp[4][4] + matrix[1][0] x matrix[3][1] x matrix[4][1]
    //①, ②, ③ 세 가지 중에서 곱셈 연산의 수가 더 적은 값

    //...

    //==> i 와 j값 사이에 있는 중간값을 기점으로 양쪽의 행렬을 합친경우를 전부 확인해봐야함
}