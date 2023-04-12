package ETC.P14891

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.math.pow

private lateinit var gearList: Array<String>
private var K = 0 //회전 횟수(1 ≤ K ≤ 100)
private var isVisited = BooleanArray(4) { false }
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P14891/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    gearList = Array(4) { "" }

    repeat(4) {
        gearList[it] = br.readLine()
    }
    K = br.readLine().toInt()
    repeat(K) {
        br.readLine().split(" ").apply {
            rollGear(this[0].toInt() - 1, this[1].toInt())
        }
        isVisited = BooleanArray(4) { false }
    }

    println(getAnswer())
}

private fun rollGear(targetNum: Int, direction: Int) {
    isVisited[targetNum] = true

    //현재 톱니바퀴의 오른쪽과, 다음 톱니바퀴의 왼쪽의 극이 다르다면
    //오른쪽 톱니바퀴도 회전
    if ((targetNum != 3) && !isVisited[targetNum + 1] &&
        gearList[targetNum][2] != gearList[targetNum + 1][6]
    ) {
        rollGear(targetNum + 1, -direction)
    }

    //현재 톱니바퀴의 왼쪽과, 이전 톱니바퀴의 오른쪽의 극이 다르다면
    //이전 톱니바퀴도 회전
    if ((targetNum != 0) && !isVisited[targetNum - 1]
        && gearList[targetNum][6] != gearList[targetNum - 1][2]
    ) {
        rollGear(targetNum - 1, -direction)
    }

    //방향이 1인 경우는 시계 방향이고, -1인 경우는 반시계 방향이다.
    when (direction) {
        1 -> {
            gearList[targetNum] = gearList[targetNum].last() + gearList[targetNum].dropLast(1)
        }
        -1 -> {
            gearList[targetNum] = gearList[targetNum].drop(1) + gearList[targetNum].first()
        }
    }
}

private fun getAnswer(): Int {
    var point = 0
    for (i in 0 until 4) {
        if (gearList[i].first() == '1') {
            point += 2.0.pow(i).toInt()
        }
    }
    return point
}