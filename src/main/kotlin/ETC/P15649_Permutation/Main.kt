package ETC.P15649_Permutation

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.StringTokenizer

// (1 ≤ M ≤ N ≤ 8)
//nPm의 case구하기
private var N = 0 
private var M = 0
private val answerList = ArrayList<String>()
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P15649_permutation/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()

    for (i in 1..N){
        permutation(i, listOf(i))
    }

    answerList.forEach { println(it) }
}

private fun permutation(
    x :Int,
    list :List<Int>
){
    //탈출 -> listSize == M일때
    if (list.size == M){
        answerList.add(list.joinToString(" "))
        return
    }

    for (i in 1..N){
        if ((x == i) || list.contains(i)) continue
        permutation(i,list + i)
    }
}