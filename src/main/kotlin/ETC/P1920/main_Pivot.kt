package ETC.P1920

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1920/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val N = br.readLine().toInt() //1~ 10만
    val inputs = br.readLine().split(" ").map {it.toInt()}.toMutableList()
    val M = br.readLine().toInt() //1~10만
    val checkList = br.readLine().split(" ").mapIndexed { index, s -> Pair(index,s.toInt()) }.toMutableList()
    val answer = Array(M){0}
    //그냥 선형탐색 하면 N^2으로 최대 1000억까지 나옴으로 시간초과
    //==> 사전 정렬 후 탐색

    inputs.sort() //NlogN
    checkList.sortBy { it.second } //NlogN
    var inputsPivot = 0; var checkListPivot = 0

    while (true){ //2N
        if ((inputsPivot > inputs.lastIndex) || (checkListPivot > checkList.lastIndex)) break

        //값이 같으면 Pivot 동시증가 (answer에 1저장) ==> 적절한 위치에 값 집어넣어야함
        //같은 값이 있을때 뒤에놈도 체크해줘야함
        if (inputs[inputsPivot] == checkList[checkListPivot].second){
            answer[checkList[checkListPivot].first] = 1
            checkListPivot++
        //값이 다르면 값이 작은쪽 Pivot 증가 (answer에 0저장)
        }else{
            if (inputs[inputsPivot] > checkList[checkListPivot].second){
                checkListPivot++
            }else{
                inputsPivot++
            }
        }
    }

    answer.forEach { println(it) }
}
