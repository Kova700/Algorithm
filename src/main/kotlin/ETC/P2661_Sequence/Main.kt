package ETC.P2661_Sequence

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var N = 0 //1 이상 80 이하
private val numList = mutableListOf<Int>()
private var answer = List(80){9}.joinToString("") //문제상 최대 값
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2661_Sequence/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()

    dfs(1)
    println(answer)
}

private fun dfs(num :Int){
    //체크인
    numList.add(num)
    //목적지인가?
    if (numList.size >= N) {
        if (answer > numList.joinToString("")){ //아스키 코드 비교
            answer = numList.joinToString("")
        }
        return
    }
    //연결된 곳 순회
    for (nextNum in 1..3){
        //갈 수 있는가?
        if (nextNum == num) continue
        if (!isBadSequence(nextNum)){
            //간다.
            dfs(nextNum)
        }
    }
    //체크아웃
    numList.removeLast()
}
private fun isBadSequence(nextNum :Int) :Boolean{
    //나쁜 수열 체크
    val checkedString = (numList + nextNum).reversed().joinToString("")
    for(i in 0..checkedString.length/2){
        val frontString = checkedString.slice(0..i)
        val front2String = checkedString.slice(i+1..checkedString.lastIndex).take(frontString.length)
        if(frontString == front2String) return true
    }
    return false
}