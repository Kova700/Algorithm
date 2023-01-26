package ETC.P10473_dijkstra_hell

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.PriorityQueue
import kotlin.math.min

private const val HUMAN_SPEED = 5
private lateinit var startNode :Node
private lateinit var targetNode :Node
private lateinit var timeList :MutableList<Double>
private val nodeList = mutableListOf<Node>()
private val PQ = PriorityQueue<Node>()
private var N = 0 //대포의 개수 (0이상 100이하)
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P10473_dijkstra_hell/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    br.readLine().split(" ").map { it.toDouble() }.also {
        startNode = Node(0,it[1], it[0], 0.0)
    }
    br.readLine().split(" ").map { it.toDouble() }.also {
        targetNode = Node(1,it[1], it[0], getTime(getDistance(startNode.y, startNode.x, it[1], it[0]), HUMAN_SPEED))
    }

    N = br.readLine().toInt()
    timeList = MutableList(N+2){Double.MAX_VALUE}
    timeList[0] = 0.0
    nodeList.add(startNode)
    nodeList.add(targetNode)
    repeat(N){
        val (x,y) = br.readLine().split(" ").map { it.toDouble() }
        nodeList.add(Node(2+it,y, x, getTime(getDistance(startNode.y, startNode.x, y, x), HUMAN_SPEED)))
    }

    PQ.add(startNode)
    dijkstra()
    println(timeList[1]) //1번 인덱스가 target인덱스
}

private fun dijkstra(){
    while (PQ.isNotEmpty()){
        val node = PQ.poll()
        if (timeList[node.num] < node.time) continue
        //연결된 노드 순회
        for (nextNode in nodeList){
            if (node.num == nextNode.num) continue
            //cost = 현재 노드의 쌓아온 시간 + 케이스별로 들어가는 추가시간
            val cost = node.time + getPlusTime(node,nextNode)
            if (cost < timeList[nextNode.num]){
                timeList[nextNode.num] = cost
                nextNode.time = cost
                PQ.add(nextNode)
            }
        }

    }
}
//어차피 최소시간을 구해야하는거라 걸어도 가보고 대포도 타봐야함
private fun getPlusTime(startNode :Node, fromNode :Node) :Double{
    val distance = getDistance(startNode, fromNode)

    //걸어간다. (걸어가는게 더 빠를 수 있으니까)
    var returnTime = getTime(distance, HUMAN_SPEED)

    //대포를 탄다. (첫 노드와 타겟노드는 대포를 못탄다.) (대포가 없으니까)
    if (startNode.num !in 0..1){
        //대포 사이의 거리가 50이상인 경우 (50만큼 날라라고 남은 거리 걸어감)
        if (distance >= 50){
            returnTime = min(returnTime,2 + getTime((distance-50), HUMAN_SPEED))
        //대포 사이의 거리가 50보다 작은경우 (대포타고 다시 오버된 거리만큼 걸어옴)
        }else{
            returnTime = min(returnTime,2 + getTime((50-distance), HUMAN_SPEED))
        }
    }
    return returnTime
}

private fun getTime(distance: Double, speed :Int) = distance / speed

private fun getDistance(n1 :Node, n2 :Node) :Double{
    return Math.sqrt( Math.pow(n2.x - n1.x, 2.0) + Math.pow(n2.y - n1.y, 2.0) )
}

private fun getDistance(
    y2 :Double, x2 :Double,
    y1 :Double, x1 :Double
): Double {
    return Math.sqrt( Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0) )
}

data class Node(
    val num :Int,
    val y :Double,
    val x :Double,
    var time :Double
) :Comparable<Node> {
    override fun compareTo(other: Node): Int = when{
        ((this.time - other.time) > 0.0) -> 1
        ((this.time - other.time) == 0.0) -> 0
        else -> -1
    }
}