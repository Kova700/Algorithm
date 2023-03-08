package ETC.P1783

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

//느낀점 :
// 1. 그리디는 메모장이 필수다.
// 2. 그리디는 왠만하면 모든 경우의수를 다 눈으로 계산해봐야한다.
// 3. dfs로 풀면 될것같은 느낌이 들었을때, input값이 너무 크다 싶으면 그리디를 생각해보자.

private var answer = 1
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1783/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    //N과 M은 2,000,000,000보다 작거나 같은 자연수 (Int 범위) ==> DFS는 못쓴다.
    val (N, M) = br.readLine().split(" ").map { it.toInt() }

    answer = when(N){
        1 -> 1
        2 -> minOf(4,(M+1) / 2)
        else -> {
            if (M < 7) minOf(4,M)
            else M - 7 + 5
        }
    }.also { println(it) }
}