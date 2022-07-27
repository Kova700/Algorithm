package DAY03.P1202

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.PriorityQueue

fun main() {
    System.setIn(FileInputStream("src/main/kotlin/DAY03/P1202/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N,K) = br.readLine().split(" ").map { it.toInt() }
    //N : 보석 개수 , K : 가방 개수
    //N,K == 1~ 30만 (N^2나오면 무조건 시간 초과)
    // ==> 정렬해두고 가방 무게에 맞는 보석 찾기 하면 무조건 시간 초과
    // ==> 탐색을 한 번만 하는 다른 방법을 찾아야함
    var answer :Long = 0

    //Greedy 문제
    //최적의 선택이 무엇인지 결정해야한다 (그게 최적의 선택인지 증명도 해야함)
    //어느 보석부터 넣을지 : 무게가 큰거 Or 가격이 큰거 ==> 가방 용량 범위 내에서 가격이 큰거
    //어느 가방부터 넣을지 : 용량이 큰거 Or 작은거 ==> 작은거 :: 작은애들이 넣을 수 있는 애들은 큰 애들도 넣을 수 있지만 역은 성립하지 않음으로
    //==> 한 개 조건을 사람이 먼저 보고 나머지 조건은 PQ에게 맡긴다.

    val jewels = Array(N){
        val temp = br.readLine().split(" ").map { it.toInt() }
        Jewel(temp[0],temp[1])
    }.sortedBy { it.weight }
    val bags = Array<Int>(K){br.readLine().toInt()}.sorted()

    val heap = PriorityQueue<Jewel>(compareByDescending{it.price})
    var checkPointer = 0
    for (bagsize in bags){
        while (checkPointer < jewels.size && jewels[checkPointer].weight <= bagsize){
                heap.add(jewels[checkPointer++])
        }
        if (heap.isNotEmpty()){
            //현재 가방으로 담을 수 있는 가장비싼 보석 뽑기
            answer += heap.poll().price //(1~ 30만)인 값을 계속 더하니까 answer은 Long타입이어야 함 (제발..ㅠㅠ)
        }
    }

    println(answer)
}
class Jewel(val weight :Int, //(1~100만)
            val price :Int)  //(1~100만)