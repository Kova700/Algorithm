package ETC.P2108

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.round

private var N = 0 //수의 개수 (1 ≤ N ≤ 500,000) N은 홀수
private lateinit var dataArray : IntArray
private val numCountArray = IntArray(8002){0}
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2108/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    dataArray = IntArray(N){0}
    repeat(N){
        //입력되는 정수의 ★절댓값★은 4,000을 넘지 않는다.
        val inputedNum = br.readLine().toInt()
        dataArray[it] = inputedNum
        numCountArray[inputedNum + 4001] += 1
    }
    val sortedDataList = dataArray.sorted()
    println(round(((dataArray.sum().toDouble() / dataArray.size) * 10) / 10).toInt())

    println(sortedDataList[dataArray.size/2])

    val thirdAnswerList = numCountArray.mapIndexed { index, count -> Pair(index,count) }.sortedBy { it.second }
    if (thirdAnswerList.last().second == thirdAnswerList[thirdAnswerList.lastIndex-1].second){
        println(thirdAnswerList[getSecondSmallValueIndex(thirdAnswerList)].first - 4001)
    }else println(thirdAnswerList.last().first - 4001)

    println(sortedDataList.last() - sortedDataList.first())
}
private fun getSecondSmallValueIndex(thirdAnswerList :List<Pair<Int,Int>>) :Int{
    var index = thirdAnswerList.lastIndex
    while (true){
        if(thirdAnswerList[index].second != thirdAnswerList.last().second) break
        index--
    }
    return index+2
}