package DAY02.P2805

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

fun main() {
    System.setIn(FileInputStream("src/main/kotlin/DAY02/P2805/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N,M) = br.readLine().split(" ").map { it.toLong() }
    val list = br.readLine().split(" ").map { it.toLong() }

    var answer :Long = Long.MIN_VALUE
    var lowPointer :Long = 0; var highPointer :Long =list.maxOf{it}
    while (true){
        var sum :Long = 0
        var mid :Long = (lowPointer + highPointer)/2

        if(highPointer<lowPointer)break

        //나무 잘라서 sum 계산
        list.forEach{
            if(it>mid) sum += it-mid
        }

        //sum이 M보다 크다면
        if (sum > M){
            answer = if (mid > answer) mid else answer
            lowPointer = mid +1
        //sum이 M과 같다면
        }else if (sum == M){
            answer = mid
            break
        //sum이 M보다 작다면
        }else{
            highPointer = mid-1
        }
    }

    println(answer)
}