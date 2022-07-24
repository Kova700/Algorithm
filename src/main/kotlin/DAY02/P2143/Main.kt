package DAY02.P2143

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

fun main() {
    System.setIn(FileInputStream("src/main/kotlin/DAY02/P2143/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val T = br.readLine().toLong() ; val N = br.readLine().toInt()
    val AList = br.readLine().split(" ").map { it.toLong() } ; val M = br.readLine().toInt()
    val BList = br.readLine().split(" ").map { it.toLong() }
    //A부배열의 합에 B부배열의 합을 더해서 T가 되는 경우의 수를 출력해라
    //가능한 경우가 한 가지도 없는 경우에는 0을 출력
    var answer :Long = 0

    //부배열의 합을 구한 배열을 각각 구한다
    //A는 오름차순 , B는 내림차순 정렬 한다.
    var subAList = getSubList(AList,"A")
    var subBList = getSubList(BList,"B")

    //2Pointer로 조건에 해당하는 곳을 찾는다.
    var APointer = 0; var BPointer = 0
    while (true){
        val sum :Long = subAList[APointer] + subBList[BPointer]
        //합이 T보다 큰 경우
        if(sum > T){
            BPointer++
        //합이 T인 경우
        }else if(sum == T){
            //현재 값 뒤에 같은 값이 있는지 확인 (중복 값 확인)
            //있다면 몇 개 있는지 카운트
            val countA :Long = countSameValue(subAList,APointer)
            val countB :Long = countSameValue(subBList,BPointer)
            answer += countA * countB
            //중복값 건너뛰어야함
            APointer += countA.toInt() ; BPointer += countB.toInt()
        //합이 T보다 작은 경우
        }else{
            APointer++
        }

        //탈출 조건
        if ((APointer == subAList.size) || (BPointer == subBList.size)) break
    }

    println(answer)
}
fun getSubList(AList :List<Long>,type :String) :ArrayList<Long>{
    val subList = ArrayList<Long>()
    for (i in 0 until AList.size){
        var sum :Long = 0
        //매번 다시 합을 구하지않고 쌓은 값을 저장하는 방식(시간 아낌)
        for (t in i until AList.size){
            sum += AList[t]
            subList.add(sum)
        }
    }
    if(type == "A") { subList.sort() } else{ subList.sortDescending() }
    return  subList
}

fun countSameValue(list :ArrayList<Long> , currentIndex :Int) :Long{
    var count :Long= 0
    val target = list[currentIndex]
    for (i in currentIndex until list.size){
        if(list[i] == target) count++
        else break
    }

    return count
}
