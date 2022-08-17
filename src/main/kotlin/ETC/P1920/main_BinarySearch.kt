package ETC.P1920

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1920/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val N = br.readLine().toInt() //1~ 10만
    val inputs = br.readLine().split(" ").map {it.toInt()}.toMutableList()
    val M = br.readLine().toInt() //1~10만
    val checkList = br.readLine().split(" ").mapIndexed { index, s -> Pair(index,s.toInt()) }.toMutableList()
    val answer = Array(M){0}

    inputs.sort() //NlogN
    checkList.sortBy { it.second } //NlogN

    checkList.forEach {//logN
        val returnIndex = inputs.binarySearch(it.second)
        if (returnIndex >= 0) answer[it.first] = 1
    }
    answer.forEach { println(it) }
}