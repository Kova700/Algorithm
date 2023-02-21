package ETC.P16918

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private val MY = arrayOf(1,-1,0,0)//상하좌우
private val MX = arrayOf(0,0,-1,1)//상하좌우
//(1 ≤ R, C, N ≤ 200)
private var R = 0
private var C = 0
private var N = 0
private lateinit var map : Array<String>
private lateinit var subMap : Array<String>
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P16918/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    br.readLine().split(" ").map{it.toInt()}.also {
        R = it[0]
        C = it[1]
        N = getN(it[2])
    }
    map = Array(R){""}
    subMap = Array(R){""}

    repeat(R){
        val line = br.readLine()
        map[it] = line
        subMap[it] = line
    }
    var time = 1
    while (true){
        if (time == N) break
        makeBomb()//깔고
        time++
        if (time == N) break
        explode() //터트리고(이전에 깐거만)
        time++
        if (time == N) break
    }
    map.forEach {
        println(it)
    }
}
private fun getN(i :Int) :Int{
    if (i == 1) return  1
    if (i%4 == 0) return 4
    if (i%4 == 1) return 5
    return i%4
}

private fun explode(){
    for(i in 0 until R){
        for (j in 0 until C){
            if(subMap[i][j] == 'O'){ //펑!
                map[i] = map[i].toCharArray().apply { this[j] = '.' }.joinToString("")
                for (k in 0 until 4){
                    val nextY = i + MY[k]
                    val nextX = j + MX[k]
                    if (isInMap(nextY,nextX)){
                        map[nextY] = map[nextY].toCharArray().apply { this[nextX] = '.' }.joinToString("")
                    }
                }
            }
        }
    }
    subMap = map.copyOf()
}

private fun makeBomb(){
    for (i in map.indices){
        map[i] = map[i].toCharArray().map {'O'}.joinToString("")
    }
}

private fun isInMap(y :Int, x :Int) =
    (y in 0 until R) && (x in 0 until C)