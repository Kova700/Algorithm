package DAY04.P2517

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

lateinit var tree :Array<Long>
var S = 1
fun main() {
    System.setIn(FileInputStream("src/main/kotlin/DAY04/P2517/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val N = br.readLine().toInt() //선수의 수(3 이상 50만 이하) 선형탐색 = 시간초과
    val inputs = Array<Long>(N){br.readLine().toLong()} //input(1이상 10억이하)

    while (S<N){
        S *= 2
    }
   tree = Array<Long>(S * 2){0L}

    //실력이 높은 순서대로 정렬
    val list = inputs.mapIndexed{ index :Int, input :Long -> Pair(index,input) }.sortedByDescending { it.second }
    for (inputPair in list){
        //실력이 높은애들부터 트리에 집어넣음
        updateTD(1,S,1,inputPair.first+1,1)
        //내 앞에 있는 나보다 실력이 높은 애들 수
        var upCount :Long= 0
        if (inputPair.first !=0){
            upCount = queryTD(1,S,1,1,inputPair.first)
        }
        inputs[inputPair.first] = upCount + 1
    }
    inputs.forEach { println(it) }
}

fun updateTD(left: Int, right: Int,
             node: Int,
             target :Int, diff :Long){
    //범위안에 target이 있음
    if (target in left..right){
        //현재 노드 갱신
        tree[node] += diff
        if (left != right){
            val mid = (left + right) / 2
            updateTD(left, mid,node*2, target, diff)
            updateTD(mid+1,right,node*2 +1,target, diff)
        }
    //범위안에 target이 없음
    }else return
}
fun queryTD(left :Int , right :Int,
            node :Int,
            queryLeft :Int ,queryRight :Int) :Long{
    //현재 노드가 범위 밖에 있음
    if (right < queryLeft || left > queryRight){
        return 0
    //현재 노드가 범위 내에 있음
    }else if ((queryLeft <= left) && (right <= queryRight) ){
        return tree[node]
    //현재 노드가 범위에 걸침
    }else {
        //자식 노드에게 위임 후 합 리턴
        val mid = (left + right) / 2
        val leftQuery = queryTD(left,mid,node*2,queryLeft,queryRight)
        val rightQuery = queryTD(mid +1,right,node*2 +1,queryLeft,queryRight)
        return leftQuery + rightQuery
    }
}
