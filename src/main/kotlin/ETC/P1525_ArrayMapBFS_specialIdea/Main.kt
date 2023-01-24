package ETC.P1525_ArrayMapBFS_specialIdea

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.LinkedList
import java.util.Queue

private val MY = arrayOf(0,0,1,-1)//좌,우,위,아래
private val MX = arrayOf(-1,1,0,0)//좌,우,위,아래
private val Q :Queue<Node> =LinkedList()
private val isVisited = HashMap<String,Boolean>()
private var answer = -1
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1525_ArrayMapBFS_specialIdea/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))

    var map = ""
    repeat(3){
        map += br.readLine().replace(" ","")
    }

    isVisited[map] = true
    Q.add(Node(map,0))
    bfs()

    println(answer)
}
private fun bfs(){
    while (Q.isNotEmpty()){
        val node = Q.poll()
        if (node.map == "123456780") {
            answer = node.switchCount
            return
        }

        val zeroIndex = node.map.indexOf('0')
        val currentY = zeroIndex / 3
        val currentX = zeroIndex % 3

        for (i in 0 until 4){
            val nextY = currentY + MY[i]
            val nextX = currentX + MX[i]
            //맵 안인가? ,가본적이 없는 곳
            if(isInMap(nextY, nextX)){
                val nextIndex = 3*nextY + nextX
                val mapList = node.map.toCharArray()
                mapList[zeroIndex] = mapList[nextIndex]
                mapList[nextIndex] = '0'

                val nextString = mapList.joinToString("")
                if (isVisited[nextString] != true){
                    Q.add(Node(nextString,node.switchCount +1))
                    isVisited[nextString] = true
                }
            }
        }
    }
}

private fun isInMap(y :Int, x :Int) :Boolean =
    (y in 0 until 3) && (x in 0 until 3)

data class Node(
    val map :String,
    val switchCount :Int
)