package ETC

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.abs
import kotlin.math.min

private lateinit var cityMap: Array<IntArray>
private var N = 0 //(2 ≤ N ≤ 50) //도시의 가로,세로
private var M = 0 //(1 ≤ M ≤ 13) //폐업하지 않을 치킨집 개수
private var answer = Int.MAX_VALUE

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P15686_15686_BackTracking_BruteForce/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    br.readLine().split(" ").map { it.toInt() }.apply {
        N = this[0]
        M = this[1]
    }
    cityMap = Array(N) { intArrayOf() }
    repeat(N) { i ->
        //치킨집의 개수는 M보다 크거나 같고, 13보다 작거나 같다.
        cityMap[i] = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }

    for (y in 0 until N) {
        for (x in 0 until N) {
            if (cityMap[y][x] == 2) {
                dfs(y, x, 1)
            }
        }
    }

    println(answer)
}

private fun dfs(y: Int, x: Int, count: Int) {
    cityMap[y][x] = 3 //선택된 치킨집은 3이라고 가정

    if (count == M) {
        //골라놓은 치킨집들(3)만 치킨집이라고 가정하고,
        //모든 집에서 치킨거리 구하고
        //합을 구해서 answer 갱신
        var cityChickenDis = 0
        for (r in 0 until N) {
            for (c in 0 until N) {
                if (cityMap[r][c] == 1) {
                    cityChickenDis += getChickenDis(r, c)
                }
            }
        }
        answer = min(answer, cityChickenDis)
        cityMap[y][x] = 2
        return
    }

    for (r in y until N) {
        for (c in 0 until N) {
            if (cityMap[r][c] == 2) {
                dfs(r, c, count + 1)
            }
        }
    }
    cityMap[y][x] = 2
}

private fun getChickenDis(homeY: Int, homeX: Int): Int {
    var minDis = Int.MAX_VALUE
    for (y in 0 until N) {
        for (x in 0 until N) {
            if (cityMap[y][x] == 3) {
                minDis = min(minDis, abs(homeY - y) + abs(homeX - x))
            }
        }
    }
    return minDis
}