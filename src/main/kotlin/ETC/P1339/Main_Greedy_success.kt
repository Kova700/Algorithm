package ETC.P1339

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

//그리디
//알파벳당 각 자릿수의 합(영향력)을 구하면
//값이 가장 큰 알파벳부터 큰 수를 배정하면됨
//(각 자리수의 합이 클 수록 그 알파벳이 좌지우지하는 수가 커짐으로)

private var N = 0
private val check = Array(26){0}
private val translator = HashMap<Int,Int>()
fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1339/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    //단어 수학 문제는 N개의 단어로 이루어짐 (1~10)
    N = br.readLine().toInt()
    val inputs = Array(N){br.readLine()}

    //알파벳당 자릿 수 파악
    for (input in inputs) {
        val length = input.length.toDouble()
        for ((index , c) in input.withIndex()) {
            check[c - 'A'] += Math.pow(10.0,length -index -1).toInt()
        }
    }
    var num = 9
    val priorityList = check.mapIndexed { index, i -> Pair(index,i) }
        .sortedByDescending{ it.second }.filter { it.second != 0 }
    priorityList.forEach{ it.second = num--}
    priorityList.forEach { translator[it.first] = it.second }

    val valueList = ArrayList<Int>()
    for (input in inputs) {
        val tempList = ArrayList<Int>()
        input.forEach { c ->
            tempList.add(translator[c-'A']!!)
        }
        valueList.add(tempList.joinToString("").toInt())
    }
    println(valueList.sum())
}

data class Pair(
    var first :Int,
    var second :Int
)