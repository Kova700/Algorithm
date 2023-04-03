package ETC.P1138

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0 //10보다 작거나 같은 자연수
private lateinit var answerArray :IntArray
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1138/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    answerArray = IntArray(N + 1){ N }
    br.readLine().split(" ").map { it.toInt() }
        .forEachIndexed { index, bigIntCountInLeft -> 
            getAnswer(index +1, bigIntCountInLeft) 
        }
    println(answerArray.drop(1).joinToString(" "))
}

private fun getAnswer(num :Int, bigIntCountInLeft :Int) {
    var count = 0
    for (i in 1..N){
        if (answerArray[i] > num) {
            count++
        }
        if (count > bigIntCountInLeft){
            if(answerArray[i] != N) continue
            answerArray[i] = num
            return
        }
    }
}