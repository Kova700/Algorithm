package ETC.P2331_Sequence

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.StringTokenizer
import kotlin.math.pow

private var A = 0 //(1 ≤ A ≤ 9999)
private var P = 0 //(1 ≤ P ≤ 5)
private val map = HashMap<Int,Boolean>()
private var list = mutableListOf<Int>()
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2331_Sequence/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    A = st.nextToken().toInt()
    P = st.nextToken().toInt()

    var now = A
    while (true){
        if(map[now] == true){
            list = list.slice(0 until list.indexOf(now)).toMutableList()
            break
        }
        list.add(now)
        map[now] = true
        now = getNextValue(now)
    }
    println(list.size)
}

private fun getNextValue(num :Int) :Int{
    var returnValue = 0
    for (c in num.toString()){
        returnValue += c.digitToInt().toFloat().pow(P).toInt()
    }
    return returnValue
}