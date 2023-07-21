package ETC.P13458

import java.io.FileInputStream
import java.util.*
import kotlin.math.ceil

private var N = 0 //시험장의 개수 (1 ≤ N ≤ 1,000,000)
private lateinit var people: IntArray //people[i] = i 시험장에 있는 응시자 수 (1 ≤ people[i] ≤ 1,000,000)
private var B = 0 //총감독관이 한 시험장에서 감시할 수 있는 응시자의 수 (1 ≤ B ≤ 1,000,000)
private var C = 0 //부감독관이 한 시험장에서 감시할 수 있는 응시자의 수 (1 ≤ C ≤ 1,000,000)
private var answer = 0L
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P13458/input"))
    val br = System.`in`.bufferedReader()
    N = br.readLine().toInt()
    people = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    val st = StringTokenizer(br.readLine())
    B = st.nextToken().toInt()
    C = st.nextToken().toInt()

    for (i in 0 until N) {
        people[i] -= B
        answer++
        if (people[i] <= 0) continue
        answer += ceil(people[i].toDouble() / C).toInt()
    }
    println(answer)
}