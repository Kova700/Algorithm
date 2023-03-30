package ETC.P10973_pre_permutation

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0 //(1 ≤ N ≤ 10,000)
private lateinit var input :IntArray
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P10973_pre_permutation/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    input = br.readLine().split(" ").map { it.toInt() }.toIntArray()

    var findAnswer = false
    for (i in input.lastIndex downTo 1){
        //왼쪽 수가 큰 수 = 자리를 바꿀 수 있는 포인트
        if (input[i-1] > input[i]) {
            findAnswer = true
            //왼쪽 수의 오른쪽 수들 중에서 왼쪽 수 보다 작은 값 중
            //가장 큰 값과 왼쪽 수의 위치를 바꿈
            var maxIndex = i
            var max = input[i]
            for (j in i until N){
                if ((max < input[j]) && (input[j] < input[i-1])) {
                    max = input[j]
                    maxIndex = j
                }
            }
            val temp = input[i-1]
            input[i-1] = input[maxIndex]
            input[maxIndex] = temp

            //왼쪽 수의 오른쪽 순열을 뒤집음
            val reverseList = input.slice(i until N).reversed()
            for (k in i until N){
                input[k] = reverseList[k-i]
            }
            println(input.joinToString(" "))
            break
        }
    }
    if (findAnswer == false) {
        println(-1)
    }
}