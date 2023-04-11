package ETC.P5430_String

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var T = 0 //테스트 케이스 개수 (최대 100)
private var canReadLeft = true
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P5430_String/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    T = br.readLine().toInt()

    for(i in 0 until T) {
        canReadLeft = true
        val p = br.readLine() //p의 길이는 1보다 크거나 같고, 100,000보다 작거나 같다.
        val n = br.readLine().toInt() //(0 ≤ n ≤ 100,000)
        val dataString = br.readLine() //내부 정수는 1이상 100이하

        if (n < p.count { it == 'D' }) {
            println("error")
            continue
        }
        if (dataString.length == 2){
            println(dataString)
            continue
        }
        val dataList = dataString.slice(1 until dataString.lastIndex)
            .split(",").map { it.toInt() }
        println(getAnswer(p, dataList))
    }
}

private fun getAnswer(p: String, dataList: List<Int>): String {
    var leftIndex = 0
    var rightIndex = dataList.lastIndex
    for (c in p) {
        when (c) {
            'R' -> {
                if (dataList.isEmpty()) return "error"

                canReadLeft = !canReadLeft
            }
            'D' -> {
                if (dataList.isEmpty()) return "error"

                if (canReadLeft) leftIndex++
                else rightIndex--
            }
        }
    }
    return if (canReadLeft){
        '[' + dataList.slice(leftIndex..rightIndex).joinToString(",") + ']'
    }else{
        '[' + dataList.slice(leftIndex..rightIndex).reversed().joinToString(",") + ']'
    }
}