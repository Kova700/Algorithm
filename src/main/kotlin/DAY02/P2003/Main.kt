package DAY02.P2003

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

fun main(){
    System.setIn(FileInputStream("src/main/kotlin/DAY02/P2003/input.txt"))

    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N,M) = br.readLine().split(" ").map { it.toInt() }
    val list = br.readLine().split(" ").map { it.toInt() }

    var lowPointer = 0
    var highPointer = 0
    var answer = 0
    while (true){
        var sum = 0
        for (i in lowPointer..highPointer){
            sum += list[i]
        }
        //합이 M보다 작다면
        if(sum < M){
            highPointer++
        //합이 M과 같다면
        }else if (sum == M){
            answer++
            lowPointer++
        //합이 M보다 크다면
        }else{
            lowPointer++
        }
        if(highPointer == list.size) break
    }

    println(answer)
}