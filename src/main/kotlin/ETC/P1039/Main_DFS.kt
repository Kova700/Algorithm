package ETC.P1039

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*
private lateinit var N :String
private var K = 0
private lateinit var DP :HashMap<Point,Int> //코틀린은 equals override하지 않아도 default로 내용물이 같으면 같은 키로 인식함
var answer = -1

private data class Point(val num :String,
                        val count :Int)

fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1039/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    N = st.nextToken() //String  //(백만이하 자연수)
    K = st.nextToken().toInt()  //10보다 작거나 같은 자연수
    DP = HashMap<Point,Int>()

    //N이 한 자리수라면 바꿀 수 없음
    //N이 두 자리수고 뒤에가 0이라면 바꿀 수 없음
    if ((N.length == 1) || (N.length == 2 && N[1] == '0')){
        println(answer)
        return
    }
    println(dfs(N,0))
}
//DP에 뭘 담아야 하는걸까?
    //예시에 담긴것 :지금 num에서 갈 수 있는 곳 다 돌아보고 얻은 최대값
        //돌아보다가 방문 했던 곳 다시 방문하면 저장되었던 값 리턴 
//DP에 메모이제이션을 이용하려면 DFS에 반환값이 있는형태가 시간복잡도 측면에서 좋다.
    //만약 반환값이 없다면 stack으로 혹은 다른 컬렉션으로 지나온 길을 저장하고
    //목적지에 도착했을때 DP에 기록하는형태로 구현을 해야하는데 시간복잡도 측면에서 낭비에 가깝다.


private fun dfs(num :String, count :Int) :Int{
    val currentPoint = Point(num,count)
    //저장된 값이 있는가?
    if(DP[currentPoint] != null){
        return DP[currentPoint]!!
    }

    //목적지인가? -> count가 K인가?
    if (count == K){
        val target = num.toInt()
        answer = if (target > answer) target else answer //answer 갱신
        return target
    }else{
        //연결된곳 순회 -> 자리를 바꿀 수 있는 모든 경우의 수
        val length = num.length
        for (i in 0 until length){
            for (t in i+1 until length){
                val newValue = swap(num,i,t)
                //갈 수 있는가? -> 맨 앞자리가 0이 아니고
                if (newValue[0] != '0'){
                    //간다.
                    val nextPoint = Point(newValue,count +1)
                    DP[nextPoint] = dfs(newValue,count +1)
                }
            }
        }
    }
    return answer
}

private fun swap (num :String, i :Int, t :Int) :String{
    val returnValue = StringBuilder(num)
    val temp = returnValue[i]
    returnValue[i] = num[t]
    returnValue[t] = temp

    return returnValue.toString()
}