package ETC.P1339

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.LinkedList

private var N = 0
private val translator = HashMap<Char,Int>()
private var num = 9
fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1339/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    //단어 수학 문제는 N개의 단어로 이루어짐 (1~10)
    N = br.readLine().toInt()
    val inputs = Array(N){br.readLine()}
        .sortedByDescending { it.length }
        .toCollection(LinkedList<String>())
    val temp = ArrayList<String>()
    temp.addAll(inputs)

    //그리디
    //자릿 수가 같다면 어느 문자에게 높은 숫자를 줘야할까 고민할 필요 X
    //어느 수에게 줘도 합한값은 같기 때문,
    //고로 그냥 높은 자리기준으로 자리가 같은 애들 먼저 높은 숫자 할당
    
    /*
        10
        ABB
        BB
        BB
        BB
        BB
        BB
        BB
        BB
        BB
        BB
        값을 구하지 못해서 일단 그리디 접근 실패
    */

    while (inputs.size != 0){ //한 사이클당 인덱스 예외 확인
        //자릿 수가 같은 수가 없다면
        if (isNotExistSameLengthItem(inputs)){
            //자릿 수가 같은 수가 나올때 까지 숫자 할당
            val cutLength = getNextItemLength(inputs) -1
            val longestItem = inputs.removeFirst() //하나씩 없앰
            //뒤에 아이템이 남아있을 때
            if (cutLength != 0){
                //자른 부분 기록
                val recordString = longestItem.substring(0 until cutLength)
                recordTranslator(recordString)
                //남은 부분 다시 집어넣기 (잘 잘라졌고 잘 들어가지는지 확인)
                //뽑은 아이템의 길이가 1이 아니라면
                if(longestItem.length != 1){
                    inputs.add(longestItem.substring(cutLength))
                    inputs.sortByDescending { it.length }
                }
                continue
            }
            //뒤에 더이상 아이템이 없을때 (본인이 마지막일 때)
            recordTranslator(longestItem)

            continue
        }
        //자릿 수가 같은 수가 있다면
        //한 글자씩 떼서 집어넣고 한 글자씩 떼서 집어넣고 반복
        val tempItem = inputs.removeFirst()
        recordTranslator("${tempItem[0]}")
        if(tempItem.length != 1){
            inputs.add(tempItem.substring(1))
            inputs.sortByDescending { it.length }
        }
    }
    println( setTranslator(temp).sum() )
}

fun isNotExistSameLengthItem(inputs: List<String>):Boolean{
    if(inputs.size == 1) return false
    return inputs[0].length != inputs[1].length
}

fun getNextItemLength(inputs: List<String>) :Int{
    if(inputs.size == 1) return 0
    return inputs[1].length
}

fun recordTranslator(recordString :String){
    recordString.forEach { char ->
        translator[char] ?: run { translator[char] = num-- }
    }
}
fun setTranslator(inputs: List<String>) :List<Int>{
    return inputs.map { word ->
        word.map { c ->
            translator[c]!!.toInt()
        }.joinToString("").toInt()
    }
}