package ETC.P11051_DP_Pascals_Triangle

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.math.BigInteger

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P11051/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    // (1 ≤ N ≤ 1,000) (1 ≤ K ≤ N)
    val (N, K) = br.readLine().split(" ").map { it.toInt() }

    val top: BigInteger =
        (N - K + 1..N)
            .fold(BigInteger.ONE) { total: BigInteger, num: Int ->
                total.multiply(BigInteger("$num"))
            }
    val bottom: BigInteger = (1..K).fold(BigInteger.ONE) { total: BigInteger, num: Int ->
        total.multiply(BigInteger("$num"))
    }
    println((top / bottom).mod(BigInteger("${10_007}")))
}