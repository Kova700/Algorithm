package DAY03.P11279

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Comparator
import java.util.PriorityQueue

fun main() {
    System.setIn(FileInputStream("src/main/kotlin/DAY03/P11279/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val N = br.readLine().toInt()
    val inputs = Array(N){br.readLine().toInt()}
    val heap = PriorityQueue<Int>(Comparator.reverseOrder())

    //입력에서 0이 주어진 횟수만큼 답을 출력
    for(input in inputs){
        if (input == 0){
            //답 출력
            if (heap.size == 0){
            //배열이 비어있는데 출력하라고 한다면 0을 출력력
                println(0)
            }else{
                println(heap.poll())
            }
       }else{
            //우선순위 큐에 집어넣음
           heap.add(input)
        }
    }
}