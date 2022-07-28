package DAY03.P2042

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

lateinit var datas : Array<Long>
lateinit var inputs :Array<List<Long>>
lateinit var tree :Array<Long>
var S = 1 //리프노드의 개수
fun main() {
    System.setIn(FileInputStream("src/main/kotlin/DAY03/P2042/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N,M,K) = br.readLine().split(" ").map { it.toInt() }
    //N : 수의 개수 (1~100만)
    //M : 수의 변경이 일어나는 횟수 (1~1만)
    //K : 구간의 합을 구하는 횟수 (1~1만) //수의 개수가 100만이고, 수가 바뀌는 횟수가 1만이라 선형탐색시에 1억이 그냥 넘어감 다른방법 사용해야함
    datas = Array(N){br.readLine().toLong()} //N개의 주어진 수 (데이터 : Long타입)
    inputs= Array(M+K){br.readLine().split(" ").map { it.toLong() }}

    while (S < N){
        S *= 2
    }
    tree = Array<Long>(S * 2){0}
    initTreeBU(N)

    for (input in inputs){
        //a가 1인경우 b번째 수를 c로 바꾸고
        if (input[0] == 1L){
            val diff :Long = input[2] - tree[S+input[1].toInt()-1]
            //updateTD(1,S,1,input[1],diff)
            updateBU(input[1].toInt(),input[2])
        //a가 2인경우 b번째 수부터 c번째 수까지의 합을 구하여 출력
        }else{
            //합은 Long타입
            //println(queryTD(1,S,1,input[1].toInt(),input[2].toInt()))
            println(queryBU(input[1].toInt(),input[2].toInt()))
        }
    }
}

//initBU
fun initTreeBU(N :Int){
    //리프 노드
    for(i in 0 until N){
        tree[S + i] = datas[i]
    }
    //내부 노드
    for (i in (S-1) downTo  1){  //이진트리는 인덱스 0을 사용하지 않음
        tree[i] = tree[i*2] + tree[i*2 + 1]
    }
}

//query(TD)
fun queryTD(left :Int, right :Int,
            node :Int,
            queryLeft :Int, queryRight :Int) :Long{
    //노드가 query범위 밖
    if (right < queryLeft || queryRight < left){
        return 0
    //노드가 query범위 안에 들어옴
    }else if (queryLeft <= left || right <= queryRight){
        return tree[node]
    //노드가 query범위에 걸침 (자식에게 위임 : 자식에서 올라온 합을 return)
    }else{
        val mid = (left + right) / 2
        val resultLeft = queryTD(left,mid,node*2,queryLeft,queryRight)
        val resultRight = queryTD(mid+1,right,node*2 +1,queryLeft,queryRight)
        return resultLeft + resultRight
    }
}

//update(TD)
fun updateTD(left :Int, right :Int,
             node :Int,
             target :Int, diff :Long){
    //리프노드가 되면 종료
    if (left == right) return

    //현재 노드가
    //노드 수정에 연관이 있다.
    if(left<= target && target <= right){
        tree[node] += diff
        val mid = (left + right) /2
        updateTD(left,mid,node*2,target,diff)
        updateTD(mid +1,right,node*2 +1,target,diff)
    //노드 수정에 연관이 없다.
    }else return
}

//query(BU)
fun queryBU(queryLeft: Int,queryRight: Int) :Long{
    var nodeLeft = S + queryLeft -1
    var nodeRight = S + queryRight -1
    var sum :Long = 0
    while (nodeLeft <= nodeRight){
        //nodeLeft가 홀수일 때
        if ((nodeLeft % 2) == 1){
            sum += tree[nodeLeft++]
        }
        //nodeRight 짝수일 때
        if ((nodeRight % 2) == 0){
            sum += tree[nodeRight--]
        }
        nodeLeft /= 2
        nodeRight /= 2
    }
    return sum
}

//update(BU)
fun updateBU(target :Int , value :Long){
    //leaf에서 target 찾음
    var node = S + target -1
    //타겟 노드 갱신
    tree[node] = value
    node /= 2
    //루트까지 갱신
    while (node > 0){
        tree[node] = tree[node*2] + tree[node*2 +1]
        node /= 2
    }
}
