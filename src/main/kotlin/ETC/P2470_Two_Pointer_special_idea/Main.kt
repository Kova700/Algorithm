package ETC.P2470_Two_Pointer_special_idea

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.abs

private var N = 0 //2 이상 100,000 이하
private var minNongDo = Int.MAX_VALUE
private var answer1 = 0
private var answer2 = 0
private lateinit var dataList: List<Int>
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2470_Two_Pointer_special_idea/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    dataList = br.readLine().split(" ").map { it.toInt() }.sorted() // O(N * logN) //최대 : 1천6백만

    //dfs 시간복잡도 O(V+E)
    // => V = 10만 , E = 10만 * (10만-1) = 10만^2 ==> 시간초과
    getAnswer()
    println("$answer1 $answer2")
}

private fun getAnswer() {
    var start = 0
    var end = dataList.lastIndex
    while (start < end) {
        val nongDo = dataList[start] + dataList[end]
        if (minNongDo > abs(nongDo)) {
            minNongDo = abs(nongDo)
            answer1 = dataList[start]
            answer2 = dataList[end]
        }
        if (nongDo > 0) end-- else start++
    }
}