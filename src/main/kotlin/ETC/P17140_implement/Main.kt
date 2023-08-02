package ETC.P17140_implement

import java.io.FileInputStream
import java.util.*

private var R = 0 // (1 ≤ R ≤ 100)
private var C = 0 // (1 ≤ C ≤ 100)
private var K = 0 // (1 ≤ K ≤ 100)
private lateinit var board: Array<IntArray>
private var time = 0
private var rMaxLastIndex = 3 //가로로 가장 긴 Index
private var cMaxLastIndex = 3 //세로로 가장 긴 Index
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P17140_implement/input"))
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())
    R = st.nextToken().toInt()
    C = st.nextToken().toInt()
    K = st.nextToken().toInt()
    board = Array(100 + 1) { IntArray(100 + 1) }
    for (i in 1..3) {
        st = StringTokenizer(br.readLine())
        for (j in 1..3) {
            board[i][j] = st.nextToken().toInt()
        }
    }

    while (true) {
        if (time > 100) {
            time = -1
            break
        }
        if (board[R][C] == K) break
        if (cMaxLastIndex >= rMaxLastIndex) rSort()
        else cSort()

        time++
    }

    println(time)
}

private fun rSort() {
    var newRMaxLastIndex = Int.MIN_VALUE
    var numCountArray: IntArray

    //한 행씩 검사
    for (i in 1..cMaxLastIndex) {
        numCountArray = IntArray(101)

        //숫자 개수 카운팅
        for (j in 1..rMaxLastIndex) {
            numCountArray[board[i][j]]++
        }

        val temp = mutableListOf<Pair<Int, Int>>()
        for (k in 1..100) {
            if (numCountArray[k] == 0) continue
            temp.add(Pair(k, numCountArray[k]))
        }
        var index = 1
        temp.sortedWith(compareBy({ it.second }, { it.first }))
            .forEach {
                if (index > 100) return@forEach
                board[i][index++] = it.first
                if (index > 100) return@forEach
                board[i][index++] = it.second
            }
        val newRLastIndex = index - 1
        for (m in newRLastIndex + 1..rMaxLastIndex) {
            board[i][m] = 0
        }

        if (newRMaxLastIndex < newRLastIndex) {
            newRMaxLastIndex = newRLastIndex
        }
    }
    rMaxLastIndex = newRMaxLastIndex
}

private fun cSort() {
    var newCMaxLastIndex = Int.MIN_VALUE
    var numCountArray: IntArray

    //한 열씩 검사
    for (j in 1..rMaxLastIndex) {
        numCountArray = IntArray(101)

        //숫자 개수 카운팅
        for (i in 1..cMaxLastIndex) {
            numCountArray[board[i][j]]++
        }

        val temp = mutableListOf<Pair<Int, Int>>()
        for (k in 1..100) {
            if (numCountArray[k] == 0) continue
            temp.add(Pair(k, numCountArray[k]))
        }
        var index = 1
        temp.sortedWith(compareBy({ it.second }, { it.first }))
            .forEach {
                if (index > 100) return@forEach
                board[index++][j] = it.first
                if (index > 100) return@forEach
                board[index++][j] = it.second
            }
        val newCLastIndex = index - 1
        for (m in newCLastIndex + 1..cMaxLastIndex) {
            board[m][j] = 0
        }
        newCMaxLastIndex = maxOf(newCMaxLastIndex, newCLastIndex)
    }
    cMaxLastIndex = newCMaxLastIndex
}