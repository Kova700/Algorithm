package ETC.P14719

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.abs

//(1 ≤ H, W ≤ 500)
private var H = 0 //세로
private var W = 0 //가로
private var answer = 0
private lateinit var map :List<Int>
private lateinit var isVisited :BooleanArray
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P14719/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    br.readLine().split(" ").map { it.toInt() }.apply {
        H = this[0]
        W = this[1]
    }
    //0이상 H이하의 정수 W개 (블록이 쌓인 높이를 의미하는 수 주어짐)
    map = br.readLine().split(" ").map { it.toInt() }
    isVisited = BooleanArray(W) { false }

    //맨위에서부터 해당 기둥값으로 물웅덩이를 만들 수 있는지 탐색한다.
    //높이가 4인 기둥이 두개가 있으면 (4 - 그 기둥 사이에 있는 기둥의 높이)의 높이를 가지는 물 웅덩이가 생긴다.
    //물 웅덩이를 만들었으면 isVisited 체크 해주고 해당 체크가안된 구역을 다시 검사한다. 높이가 1이 될 때 까지

    for (i in H downTo 1){
        val indexList = mutableListOf<Int>()
        for (k in map.indices){
            if (map[k] >= i){
                indexList.add(k)
            }
        }
        getWaterSpace(indexList, i)
    }

    println(answer)
}

private fun getWaterSpace(indexList :List<Int>, targetH :Int){
    if (indexList.size <= 1) return
    var p1 = indexList[0]
    var p2 = indexList[1]
    //두개의 인덱스 사이에 있는 애들 전부 Visited 처리 (두개의 인덱스는 포함하지 않음)
    for (i in 2..indexList.size){
        if (abs(p2-p1) != 1){
            for(k in minOf(p1,p2)+1 ..maxOf(p1,p2)-1){
                if (!isVisited[k]){
                    answer += targetH - map[k]
                    isVisited[k] = true
                }
            }
        }
        //두개의 포인터중 숫자가 작은 포인터가 값 바뀜
        if (i > indexList.lastIndex) break
        if (p1 > p2) p2 = indexList[i] else p1 = indexList[i]
    }
}