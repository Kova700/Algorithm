package ETC.P1039

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.StringTokenizer
private lateinit var N : CharArray
private lateinit var sortedN: List<Data>
private var K = 0
fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1039/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    N = st.nextToken().toCharArray() //String  //(백만이하 자연수)
    K = st.nextToken().toInt()  //10보다 작거나 같은 자연수

/*
        1488 2 일 때,
        8841 이 나오겠지만,
        4188 2 일 때,
        8814가 나옴으로
        아래 그리디 방식으로는 풀 수 없다..
        (다른 블로그도 그리디는 다 실패하신듯 하다..)
*/

    //탐욕법 원칙
    // 현재 제일 앞에있는 수가 제일 큰 수인지 확인한다.
        // 제일 큰 수라면 그냥 둔다
        // 제일 큰 수가 아니라면
            // 제일 큰 수를 찾고 제일 앞에 세운다.(앞에 넘어온 수는 놔두고 그 다음 위치로)
                // 제일 큰 수가 두 개 이상이라면 뒤에 있는 놈을 뽑아서 제일 앞에 세운다. (큰 수가 최대한 앞에 있을 수록 숫자가 커짐으로)
                // 바꾸려고 하는 숫자가 0이고 가야하는 자리가 맨앞이라면 혹은 N이 한자리 숫자라면 -1을 출력한다.
            // 인덱스 끝까지 검사를 다 했는데 K가 남아있다면 맨 뒤에있는 숫자 두 개 위치를 바꾼다.
    val temp = N.clone().toCollection(ArrayList())
    val answer = ArrayList<Char>()

    //N이 한 자리수라면 바꿀 수 없음
    //N이 두 자리수고 뒤에가 0이라면 바꿀 수 없음
    if ((temp.size == 1) || (temp.size == 2 && temp[1] == '0')){
        println("-1")
        return
    }

    while (true){
        //K 체크
        if (K == 0) {
            if (N.size != answer.size){
                answer.addAll(temp)
            }
            break
        }

        // 인덱스 끝까지 검사를 다 했는데 K가 남아있다면 맨 뒤에있는 숫자 두 개 위치를 바꾼다.
        if((K != 0) && (temp.size == 0)){
            if (K % 2 == 0) break
            else {
                val t = answer[answer.lastIndex -1]
                answer[answer.lastIndex -1] = answer[answer.lastIndex]
                answer[answer.lastIndex] = t
            }
            break
        }

        //우선순위 1.숫자 큰 순, 2. 인덱스 큰 순
        sortedN = temp.mapIndexed { index, c -> Data(index,c) }.sortedWith(compareByDescending<Data> { it.value }.thenByDescending{ it.index }) //NlogN

        //현재 pivot에 있는 수가 제일 큰 수 인가?
        if(temp.first() == sortedN.first().value){
            // 제일 큰 수라면 그냥 둔다
            answer.add(temp.removeFirst())
            // 제일 큰 수가 아니라면
        }else{
            // 제일 큰 수를 찾고 제일 앞에 세운다.
            val t = temp[0]
            temp[0] = sortedN.first().value
            temp[sortedN.first().index] = t
            answer.add(temp.removeFirst())
            K--
        }
    }

    println(answer.joinToString(""))
}

data class Data(var index: Int, var value :Char)