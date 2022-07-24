package DAY02.P1072

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

fun main() {
    System.setIn(FileInputStream("src/main/kotlin/DAY02/P1072/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (X,Y) = br.readLine().split(" ").map { it.toLong() }

    //시간 제한 2초 ( 선형탐색 하면 바로 초과 ) ==> 이분탐색 하자.
    //X : 게임 횟수 (1~10억), Y : 이긴 게임 횟수 (0~X)
    //Z : 승률(소수점 버림)
    //앞으로 모든 게임은 이긴다 가정 (== X, Y같이 증가)
    // "최소" 몇번을 더 해야 Z가 변하는가
    var answer :Long = Long.MAX_VALUE
    val currentRate :Long = (Y*100) / X
    var findAnswer = false

    //승률이 변화는 곳의 최저치를 찾자
    var highPointer :Long = 1_000_000_000 ; var lowPointer :Long = 0
    while (true){
        val mid :Long = (highPointer + lowPointer) / 2
        val tempRate :Long = (Y+mid)*100 / (X+mid)

        //승률이 그대로인 경우
        if(tempRate == currentRate){
            lowPointer = mid + 1
        //승률이 변한경우
        }else {
            findAnswer = true
            answer = mid
            highPointer = mid -1
        }
        //탈출 조건
        if (highPointer < lowPointer) break
    }
    //승률이 절대 변하지 않는다면 -1을 출력
    if(findAnswer == false) println(-1)
    else println(answer)
}