package DAY03.P2243

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

lateinit var tree :Array<Int>
var S = 1
fun main() {
    //중간중간에 계속해서 값이 바뀌는 문제 -> 인덱스 트리 우선 접근
    System.setIn(FileInputStream("src/main/kotlin/DAY03/P2243/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val N = br.readLine().toInt() // (N의 크기 : 1~10만)
    val inputs = Array(N){br.readLine().split(" ").map { it.toInt() }}
    //사탕의 총 개수는 20억을 넘지 않는다. (= 정수 범위를 넘지 않음) (선형 탐색하면 무조건 타임 아웃!)
    //사탕은 맛있음이 1~1백만 정수로 나타남 (1이 가장 맛있음)

    //맛의 종류 : 100만
    val flavorsNumber = 1_000_000
    while (S < flavorsNumber){
        S *= 2
    }
    tree = Array<Int>(S * 2){0}

    for (input in inputs){
        //A가 1인 경우 : 사탕상자에서 사탕을 꺼내는 경우 / B : 상자에서 #번째로 맛있는 사탕
        if(input[0] == 1){
            //꺼낸 사탕의 맛의 번호 출력
            val answer = pickTD(1,S,1,input[1],-1)
            println(answer)
        //A가 2인 경우 : 사탕을 넣는 경우 / B : 넣을 사탕의 맛 / C : 넣을 사탕의 개수 (양수 음수 가능)
        }else{
            //업데이트
            updateTD(1,S,1,input[1],input[2])
        }
    }
}

fun pickTD(left: Int, right: Int,
             node: Int,
             targetNumber :Int,
             diff: Int) :Int{
    //꺼낸값 갱신
    tree[node] += diff

    //리프노드가 아니라면
    if (left != right){
        val mid = (left + right) / 2
        val leftNode = node * 2
        //트리의 좌측 자식노드의 값이 targetNumber보다 크거나 같다면
        if (tree[leftNode] >= targetNumber) {
            return pickTD(left, mid, node * 2, targetNumber,diff)
            //트리의 좌측 자식노드의 값이 targetNumber보다 작다면
        } else {
            //차감하고 오른쪽으로 가야지
            return pickTD(mid + 1, right, node * 2 + 1, targetNumber - tree[leftNode],diff)
        }
    }else return right
}

fun updateTD(left : Int, right :Int,
             node :Int,
             target :Int, diff :Int) {
    //target이 범위 안에 들어올 때
    if(left <= target && target <= right){
        tree[node] += diff
        val mid = (left + right) / 2
        if (left != right){
            updateTD(left,mid,node*2,target,diff)
            updateTD(mid+1,right,node*2 +1,target,diff)
        }
    //target이 범위 밖에 있을 때
    }else return
}