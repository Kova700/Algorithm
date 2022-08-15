package ETC.P1039

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.collections.HashMap

private lateinit var N :String
private lateinit var Q :ArrayDeque<Pair>
private lateinit var isVisited :HashMap<String,Boolean>
private var K = 0

private data class Pair(val num :String,
                        val count :Int)
fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1039/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    N = st.nextToken()          //(백만이하 자연수)
    K = st.nextToken().toInt()  //10보다 작거나 같은 자연수
    Q = ArrayDeque<Pair>()
    isVisited = HashMap<String,Boolean>()
    var answer = -1

    //N이 한 자리수라면 바꿀 수 없음
    //N이 두 자리수고 뒤에가 0이라면 바꿀 수 없음
    if ((N.length == 1) || (N.length == 2 && N[1] == '0')){
        println(answer)
        return
    }

    //K번의 위치 교환을 수행한 수 중에 가장 큰 수를 구하는 문제
    var count = 0
    Q.add(Pair(N, count))
    while (Q.isNotEmpty()){
        //큐에서 꺼냄
        val pair = Q.removeFirst()

        //한 층에서 탐색을 다 끝냈다면 isVisited 초기화
        if (count != pair.count) {
            isVisited = HashMap<String,Boolean>()
            count = pair.count
        }

        //목적지인가? -> count가 K인가?
        if (pair.count == K){
            val target = pair.num.toInt()
            answer = if(target > answer) target else answer
            continue
        }
        //연결된 곳 순회 -> 자리를 바꿀 수 있는 모든 경우의 수
        val length = pair.num.length
        for (i in 0 until length){
            for (t in i+1 until length){
                val newValue = swap(pair.num,i,t)
                //갈 수 있는가? ->  맨 앞자리가 0이 아니고, 방문하지 않은 수
                if((newValue[0] != '0') && (isVisited[newValue] == null)){
                    Q.add(Pair(newValue,pair.count +1))
                    isVisited[newValue] = true
                }
            }
        }
    }
    println(answer)
}
fun swap (num :String, i :Int, t :Int) :String{
    val returnValue = StringBuilder(num)
    val temp = returnValue[i]
    returnValue[i] = num[t]
    returnValue[t] = temp

    return returnValue.toString()
}