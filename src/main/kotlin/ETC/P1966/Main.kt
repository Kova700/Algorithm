package ETC.P1966

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

private var caseCount = 0
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1966/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))

    caseCount = br.readLine().toInt()
    repeat(caseCount){
        var count = 0
        val Q : Queue<Point> = LinkedList()
        val PQ = PriorityQueue<Point>()

        val (N, M) = br.readLine().split(" ").map{ it.toInt() }
        val weightList = br.readLine().split(" ").mapIndexed{ index, s -> Point(index, s.toInt()) }
        Q.addAll(weightList)
        PQ.addAll(weightList)

        while (true){
            val point = Q.poll()
            val topValue = PQ.peek()
            if (point.weight == topValue.weight){
                PQ.remove()
                count++
                if (point.position == M){
                    println(count)
                    break
                }
            }else Q.add(point)
        }
    }
}
data class Point(
    val position :Int,
    val weight :Int
) :Comparable<Point> {
    override fun compareTo(other: Point): Int =
        other.weight -this.weight //내림 차순
}