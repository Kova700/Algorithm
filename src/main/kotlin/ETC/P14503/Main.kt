package ETC.P14503

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.StringTokenizer

private var N = 0
private var M = 0
private lateinit var map :List<List<Int>>
private lateinit var isVisited :MutableList<MutableList<Boolean>>
const val NORTH = 0 //북
const val EAST = 1 //동
const val SOUTH = 2 //남
const val WEST = 3 //서
const val NOWAY = -1
private var count = 0

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P14503/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()
    val robotLocation = br.readLine().split(' ').map { it.toInt() }
    map = List(N){br.readLine().split(' ').map { it.toInt() }}
    isVisited = MutableList(N){ MutableList(M){ false } }

    autoClean(robotLocation[0], robotLocation[1], robotLocation[2])
    println(count)
}

private fun autoClean(
    row :Int,
    col :Int,
    nowDirection :Int
) {

    //1.체크인 (청소)
    if (isVisited[row][col] == false){
        count++
        isVisited[row][col] = true
    }

    val nextDirection = getNextDirection(row, col, nowDirection)
    //2.목적지 인가? -> 네 방향 모두 청소가 이미 되었거나 , 벽이고, 후진도 못함 (= 갈 곳이 없다.)
    if (nextDirection == NOWAY) {
        //바라보는 방향을 유지한 채로 한 칸 후진할 수 있다면 한 칸 후진
        val backPoint = getBackPoint(row, col, nowDirection)
        if (map[backPoint.row][backPoint.col] == 0) {
            autoClean(backPoint.row, backPoint.col, nowDirection)
        }
        //바라보는 방향의 뒤쪽 칸이 벽이라 후진할 수 없다면 작동을 멈춘다.
        return
    }
    //3.연결된 곳 순회 -> 왼쪽 중 갈 수 있는 곳
    val nextPoint = getNextPoint(row, col ,nextDirection)
        //4. 갈 수 있는가? -> ㅇㅇ
            //5. 간다.
    autoClean(nextPoint.row, nextPoint.col, nextDirection)
    //6. 체크 아웃 -> 굳이 하지 않아도 됨
}

private fun getNextDirection(
    row :Int,
    col :Int,
    nowDirection :Int
) :Int {
    var checkCount = 0
    var checkingDirection = if(nowDirection -1 < 0) 3 else nowDirection -1
    while (checkCount != 4){
        when(checkingDirection){
            NORTH -> if(isExistNorthPoint(row, col)) return NORTH
            EAST -> if(isExistEastPoint(row, col)) return EAST
            SOUTH -> if(isExistSouthPoint(row, col)) return SOUTH
            WEST -> if(isExistWestPoint(row, col)) return WEST
        }
        checkingDirection = if (checkingDirection -1 < 0) 3 else checkingDirection -1
        checkCount++
    }
    return NOWAY
}

private fun getBackPoint(
    row :Int,
    col :Int,
    nowDirection :Int
): Point{
    return when(nowDirection){
        NORTH -> { Point(row + 1, col) }
        EAST -> { Point(row, col - 1) }
        SOUTH -> { Point(row - 1, col) }
        WEST -> { Point(row, col + 1) }
        else -> throw Exception("wrong direction")
    }
}

private fun getNextPoint(
    row :Int,
    col :Int,
    nextDirection :Int
): Point{
    return when(nextDirection){
        NORTH -> { Point(row - 1, col) }
        EAST -> { Point(row, col + 1) }
        SOUTH -> { Point(row + 1, col) }
        WEST -> { Point(row, col - 1) }
        else -> throw Exception("wrong direction")
    }
}

private fun isExistNorthPoint(row :Int, col :Int):Boolean{
    return (!isVisited[row - 1][col]) && (map[row - 1][col] == 0)
}

private fun isExistEastPoint(row :Int, col :Int):Boolean{
    return (!isVisited[row][col + 1]) && (map[row][col + 1] == 0)
}

private fun isExistSouthPoint(row :Int, col :Int):Boolean{
    return (!isVisited[row + 1][col]) && (map[row + 1][col] == 0)
}

private fun isExistWestPoint(row :Int, col :Int):Boolean{
    return (!isVisited[row][col -1]) && (map[row][col -1] == 0)
}

data class Point(
    val row :Int,
    val col :Int
)