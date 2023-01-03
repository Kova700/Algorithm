package ETC.P1339

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.HashMap

private var N = 0
private lateinit var inputs : Array<String>
private lateinit var charSetList :List<Char> //문자 후보군
private lateinit var isVisited : Array<Boolean>
private var tempList = LinkedList<Char>()
private var translator = HashMap<Char,Int>()
private var answer = 0
fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1339/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    inputs = Array(N){br.readLine()}
    //9! = 362880 = 모든 숫자의 경우의 수 (대략 x 100번 연산시 시간초과)
    charSetList = inputs.map { it.toSet() }.flatMap { it }.toSet().toList()
    isVisited = Array(charSetList.size){false}

    for (i in 0..charSetList.lastIndex){
        permutation(i)
    }
    println(answer)
}

private fun permutation(
    currentIndex :Int,
    count :Int = 1
) {
    //체크인
    isVisited[currentIndex] = true
    tempList.add(charSetList[currentIndex])
    //목적지 인가?
    if (count == charSetList.size){
        println("tempList : $tempList")
        setTranslator(tempList)
        translate()
        isVisited[currentIndex] = false
        tempList.removeLast()
        return
    }
    //연결된 곳 순회
    for (i in 0..charSetList.lastIndex){
        //갈 수 있는가?
        if (isVisited[i] == false){
            //간다.
            permutation(i,count+1)
        }
    }
    //체크아웃
    isVisited[currentIndex] = false
    tempList.removeLast()
}

private fun setTranslator(charList : List<Char>) {
    var num = 9
    for (c in charList) {
        translator[c] = num--
    }
}

private fun translate(){
    //answer 갱신
    val translatedList = ArrayList<Int>()
    for (input in inputs) {
        translatedList.add(input.map { c -> translator[c]!! }.joinToString("").toInt())
    }
    val temp = translatedList.sum()
    if (answer < temp) answer = temp
}