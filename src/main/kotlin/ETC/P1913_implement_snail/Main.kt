package ETC.P1913_implement_snail

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.pow

private val MY = arrayOf(-1,0,1,0)//아래,우,위,좌
private val MX = arrayOf(0,1,0,-1)//아래,우,위,좌

private var N = 0 //홀수인 자연수 N(3 ≤ N ≤ 999)
private var target = 0 //N^2 이하의 자연수
private lateinit var map : Array<IntArray>
private lateinit var targetIndex :String

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1913_implement_snail/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    map = Array(N){ IntArray(N){ 0 } }
    target = br.readLine().toInt()

    makeSnail()
    for (i in map.indices){
        println(map[i].joinToString(" "))
    }
    println(targetIndex) //좌표는 1부터 시작
}
private fun makeSnail(){
    var cachedStraightCount = 1 //2번 직진하면 직진 칸 수 +1 증가
    var straightCount = 1 //2번 직진하면 직진 칸 수 +1 증가
    var y = N/2
    var x = N/2
    var value = 1 //칸 옮길 때 마다 증가,
    var direction = 0 //straightCount가 0이 될 때마다 방향 전환
    var directionChangeCount = 0
    map[y][x] = value
    targetIndex = "${y+1} ${x+1}"

    while (true){
        y += MY[direction % 4]
        x += MX[direction % 4]

        map[y][x] = ++value
        if (value == target) targetIndex = "${y+1} ${x+1}"
        straightCount-- // 칸 이동 시 감소

        if (value == N.toDouble().pow(2.0).toInt()) break

        if (straightCount == 0){
            direction++ ////straightCount가 0이 될 때마다 방향 전환
            directionChangeCount++
            straightCount = cachedStraightCount
        }
        if (directionChangeCount == 2){
            straightCount++
            cachedStraightCount = straightCount
            directionChangeCount = 0
        }
    }
}