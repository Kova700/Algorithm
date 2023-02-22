package ETC.P16926

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private val MY = arrayOf(1,0,-1,0)//상우하좌
private val MX = arrayOf(0,1,0,-1)//상우하좌

//2 ≤ N, M ≤ 300
//min(N, M) mod 2 = 0 --> 1칸짜리 레일은 없단말
private var N = 0
private var M = 0
private var R = 0 //1 ≤ R ≤ 1,000
private lateinit var map :Array<IntArray>
private lateinit var isVisited :Array<BooleanArray>
private val memoryList = mutableListOf<MutableList<Int>>()
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P16926/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    br.readLine().split(" ").map{ it.toInt()}.apply {
        N = this[0]
        M = this[1]
        R = this[2]
    }
    map = Array(N){ intArrayOf() }
    isVisited = Array(N){ BooleanArray(M){false} }

    repeat(N){ row ->
        //배열의 원소는 10^8이하
        map[row] = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }

    var y = 0
    var x = 0
    var i = 0
    var temp = mutableListOf<Int>()
    //각 돌아가는 레일(?)별로 배열에 따로 저장
    while (true){
        if(!isVisited[y][x]){ temp.add(map[y][x])}
        isVisited[y][x] = true

        val nextY = y + MY[i]
        val nextX = x + MX[i]
        if(!isInMap(nextY, nextX) || isVisited[nextY][nextX]){
            if(i == 3){
                y++
                x = y
                i = 0
                if (isVisited[y][y]) {
                    memoryList.add(temp.toMutableList())
                    break
                }
                memoryList.add(temp)
                temp = mutableListOf<Int>()
                continue
            }
            i++
        }
        y += MY[i]
        x += MX[i]
    }

    for (j in memoryList.indices){
        val r = R % memoryList[j].size
        val temp1 = memoryList[j].slice(memoryList[j].lastIndex-r+1..memoryList[j].lastIndex)
        val answerLine = memoryList[j].dropLast(r)
        memoryList[j] = (temp1 + answerLine).toMutableList()
    }

    y = 0
    x = 0
    i = 0
    for (j in memoryList.indices){
        while (true){
            isVisited[y][x] = false
            map[y][x] = memoryList[j].removeFirstOrNull() ?: break

            val nextY = y + MY[i]
            val nextX = x + MX[i]
            if(!isInMap(nextY, nextX) || !isVisited[nextY][nextX]){
                if(i == 3){
                    y++
                    x = y
                    i = 0
                    if (!isVisited[y][y]) break
                    continue
                }
                i++
            }
            y += MY[i]
            x += MX[i]
        }
    }

    map.forEach {
        println(it.joinToString(" "))
    }
    //본인이 속한 외곽에 속한 칸의 개수를 구하고, c라고 칭하자
    //R %c만큼 위치를 이동시켜주면 될듯
}

private fun isInMap(y :Int, x :Int) =
    (y in 0 until N) && (x in 0 until M)