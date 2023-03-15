package ETC.P8911

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private val MY = arrayOf(1,0,-1,0) //북동남서
private val MX = arrayOf(0,1,0,-1)

private var T = 0
// 0 :북, 1 :동, 2 :남, 3 :서
private var direction = 0
private var nowPoint = Point(0,0)
private var pointList = mutableListOf<Point>( nowPoint )
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P8911/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    T = br.readLine().toInt()

    repeat(T){
        br.readLine().toCharArray().forEach { readChar(it) }
        println(getArea())
        initPoint()
    }
}
private fun initPoint(){
    nowPoint = Point(0,0)
    direction = 0
    pointList = mutableListOf<Point>( nowPoint )
}
private fun getArea() :Int{
    //사각형 넓이 = (지나온 좌표중 (가장 x가 큰값 - 가장 x가 작은값) * (가장 y가 큰값 - 가장 y가 작은값)
    var maxX = Int.MIN_VALUE ; var minX = Int.MAX_VALUE
    var maxY = Int.MIN_VALUE ; var minY = Int.MAX_VALUE
    for (point in pointList) {
        if (point.x > maxX) maxX = point.x
        if (point.x < minX) minX = point.x
        if (point.y > maxY) maxY = point.y
        if (point.y < minY) minY = point.y
    }
    return (maxX - minX) * (maxY - minY)
}
private fun goFront(){
    nowPoint = Point(nowPoint.y + MY[direction], nowPoint.x + MX[direction])
    pointList.add(nowPoint)
}
private fun goBack(){
    nowPoint = Point(nowPoint.y - MY[direction], nowPoint.x - MX[direction])
    pointList.add(nowPoint)
}

private fun readChar(c :Char) {
    when(c){
        'F' -> goFront()
        'B' -> goBack()
        'L' -> direction = if(direction-1 < 0) direction-1 +4 else direction-1
        'R' -> direction = if(direction+1 > 3) direction+1 -4 else direction+1
    }
}
private data class Point(
    val y :Int,
    val x :Int,
)