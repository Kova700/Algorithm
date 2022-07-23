package DAY02.P1806

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

fun main(){
    System.setIn(FileInputStream("src/main/kotlin/DAY02/P1806/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N,S) = br.readLine().split(" ").map { it.toInt() }
    val list = br.readLine().split(" ").map { it.toInt() }

    var answer = Int.MAX_VALUE
    var lowPointer = 0; var highPointer = 0; var sum = list[0]
    //부분합중 그 합이 S 이상 되는 것 중, 가장 짧은 것의 길이를 구해라
    while (true){
        //합이 S보다 작을 때
        if(sum < S){
            highPointer++
            if(highPointer == list.size) break
            sum += list[highPointer]
        //합이 S보다 크거나 같을 때
        }else{
            val length = highPointer - lowPointer +1
            sum -= list[lowPointer++]
            if(length < answer) answer = length
        }
    }
    if(answer == Int.MAX_VALUE) println(0)
    else println(answer)
}
