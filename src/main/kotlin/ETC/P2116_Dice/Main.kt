package ETC.P2116_Dice

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0 // 주사위의 개수 (10,000개 이하)
private const val DICE_SIZE = 6
private lateinit var diceList: Array<IntArray>
private val opSideIndexList = arrayOf(5, 3, 4, 1, 2, 0)
private var tempSum = 0
private var answer = 0

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2116_Dice/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    diceList = Array(N) { IntArray(DICE_SIZE) { 0 } }
    repeat(N) { i ->
        diceList[i] = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    }
    diceList[0].forEach { firstTop ->
        tempSum += getHorizontalMaxNum(0, firstTop)
        stackDice(1, firstTop)
        tempSum = 0
    }

    println(answer)
    //윗면, 아랫면 도킹을 한 후, 돌릴 수 있는 수 중 가장 큰 수 찾아서 더하기
}

private fun stackDice(floor: Int, bottomTop :Int){
    if (floor == N) {
        answer = maxOf(tempSum, answer)
        return
    }
    tempSum += getHorizontalMaxNum(floor, bottomTop)
    stackDice(floor+1, getTopNum(floor, bottomTop))
}

private fun getHorizontalMaxNum(floor: Int, bottomOrTopNum: Int) :Int{
    val targetNumIndex = diceList[floor].indexOfFirst { it == bottomOrTopNum }
    var max = Int.MIN_VALUE
    for (i in 0 until DICE_SIZE){
        if (i == targetNumIndex || i == opSideIndexList[targetNumIndex]) continue
        max = maxOf(diceList[floor][i], max)
    }
    return max
}

private fun getTopNum(floor: Int, bottomNum: Int) :Int{
    val bottomNumIndex = diceList[floor].indexOfFirst { it == bottomNum }
    return diceList[floor][opSideIndexList[bottomNumIndex]]
}