package ETC.P2852

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.text.DecimalFormat

private var N = 0 // (1<=N<=100) 골이 들어간 횟수
private var scoreArray = IntArray(2 +1){0}
private var secondTimeArray = IntArray(2 + 1){0}
private var recordStartSecondTime = 0
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2852/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()

    repeat(N){
        br.readLine().split(" ").apply {
            recordSecondTime(this[0].toInt(),this[1])
        }
    }

    if (secondTimeArray[1] == 0 && secondTimeArray[2] == 0){
        val team = if (scoreArray[1] > scoreArray[2]) 1 else 2
        secondTimeArray[team] += 48 * 60 - recordStartSecondTime
        println(getTimeString(secondTimeArray[1]))
        println(getTimeString(secondTimeArray[2]))
        return
    }

    recordLastTime((48 * 60) - recordStartSecondTime)

    println(getTimeString(secondTimeArray[1]))
    println(getTimeString(secondTimeArray[2]))
}

//점수가 같아진 시점 - 점수 차이가 처음 발생한 시점 = 적립될 시간
private fun recordSecondTime(teamNum :Int, timeString :String){
    if (scoreArray[1] > scoreArray[2]){
        recordScore(teamNum)
        if (scoreArray[1] == scoreArray[2]){
            secondTimeArray[1] += getSecondTime(timeString) - recordStartSecondTime
        }
        //동점이 되었다면 점수 차이가 처음 발생한 시점과 현재 시간 차이 만큼 적립
        //동점이 아니라면 그냥 패스
    }else if (scoreArray[1] == scoreArray[2]){
        //동점이었으니까 지금 들어온 골과 이전 골 사이 시간이 이기고 있던 시간이 아님 (사이 시간 버림)
        recordScore(teamNum)
        recordStartSecondTime = getSecondTime(timeString)
    }else{
        recordScore(teamNum)
        if (scoreArray[1] == scoreArray[2]){
            secondTimeArray[2] += getSecondTime(timeString) - recordStartSecondTime
        }
        ///동점이 되었다면 점수 차이가 처음 발생한 시점과 현재 시간 차이 만큼 적립
        //동점이 아니라면 그냥 패스
    }
}
private fun recordLastTime(secondTime :Int){
    if (scoreArray[1] > scoreArray[2]){
        secondTimeArray[1] += secondTime
    }else if (scoreArray[1] < scoreArray[2]){
        secondTimeArray[2] += secondTime
    }
}
private fun recordScore(teamNum :Int){
    scoreArray[teamNum]++
}
private fun getSecondTime(timeString :String) :Int{
    val (time, second) = timeString.split(":").map { it.toInt() }
    return time *60 + second
}
private fun getTimeString(secondTime :Int) :String{
    val timeFormat = DecimalFormat("00")
    val minute = timeFormat.format(secondTime / 60) // 분
    val second = timeFormat.format(secondTime % 60) // 초
    return "$minute:$second"
}
