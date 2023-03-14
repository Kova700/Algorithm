package ETC.P2477

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var K = 0 // 1m^2의 넓이에 자라는 참외의 개수 (1 ≤ K ≤ 20)
private var area = 0
private lateinit var directionList :MutableList<Int>
private lateinit var lengthList :MutableList<Int>

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2477/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))

    K = br.readLine().toInt()
    directionList = mutableListOf()
    lengthList = mutableListOf()
    repeat(6){
        //변의 방향에서 동쪽은 1, 서쪽은 2, 남쪽은 3, 북쪽은 4로 나타낸다.
        br.readLine().split(" ").map { it.toInt() }.apply {
            directionList.add(this[0])
            lengthList.add(this[1])
        }
    }
    val fieldType = getFieldType(directionList.sorted())
    val biggestWidthIndex = getBiggestWidthIndex()

    val bigSquare :Int
    val smallSquare :Int
    when(fieldType){
        1,3 -> { //ㄱ형 , 180도
            bigSquare = lengthList[biggestWidthIndex] * lengthList[getPreviousIndex(biggestWidthIndex,1)]
            smallSquare = lengthList[getNextIndex(biggestWidthIndex,2)] * lengthList[getNextIndex(biggestWidthIndex,3)]
            area = bigSquare - smallSquare
        }
        2,4 -> { //90도 , 270도
            bigSquare = lengthList[biggestWidthIndex] * lengthList[getNextIndex(biggestWidthIndex,1)]
            smallSquare = lengthList[getPreviousIndex(biggestWidthIndex,2)] * lengthList[getPreviousIndex(biggestWidthIndex,3)]
            area = bigSquare - smallSquare
        }
    }

    println(area * K)
}
private fun getNextIndex(now :Int, dx :Int) :Int =
    if (now +dx > 5) now +dx - 6 else now +dx

private fun getPreviousIndex(now :Int, dx :Int) :Int =
    if (now -dx < 0) now -dx + 6 else now -dx

//가장 긴 가로길이 인덱스 구하기
private fun getBiggestWidthIndex() :Int{
    var index = 0
    var biggestWidth = 0
    //방향이 1 혹은 2 여야함 (동쪽 혹은 서쪽)
    for (i in 0 until 6){
        if (directionList[i] !in 1..2) continue
        if (lengthList[i] > biggestWidth){
            biggestWidth = lengthList[i]
            index = i
        }
    }
    return index
}
private fun getFieldType(directionSet :List<Int>) :Int{
    val one =   listOf(1,1,2,3,3,4)
    val two =   listOf(1,1,2,3,4,4)
    val three = listOf(1,2,2,3,4,4)
    val four =  listOf(1,2,2,3,3,4)
    return when{
        (directionSet == one) ->  1
        (directionSet == two) ->  2
        (directionSet == three) ->  3
        (directionSet == four) ->  4
        else -> throw java.lang.Exception()
    }
}