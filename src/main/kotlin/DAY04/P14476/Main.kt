package DAY04.P14476

import java.io.*
import java.util.PriorityQueue

fun main() {
    System.setIn(FileInputStream("src/main/kotlin/DAY04/P14476/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    val N = br.readLine().toInt() // 1 ~ 1백만 == N^2 나오면 시간초과
    val inputs = br.readLine().split(" ").map { it.toInt() }// 1~20억

    val leftGcds = ArrayList<Int>()
    val rightGcds = ArrayList<Int>()
    val answerPQ = PriorityQueue<Pair<Int,Int>>(compareByDescending { it.first })

    //좌측 누적 GCD
    var tempLeft = inputs[0]
    leftGcds.add(tempLeft)
    for (i in 1 until inputs.size) {
        tempLeft = gcd(tempLeft,inputs[i])
        leftGcds.add(tempLeft)
    }
    //우측 누적 GCD
    var tempRight = inputs[inputs.lastIndex]
    rightGcds.add(tempRight)
    for (i in inputs.lastIndex-1 downTo 0){
        tempRight = gcd(tempRight,inputs[i])
        rightGcds.add(tempRight)
    }

    //answerGcd 후보군 탐색
    for (i in inputs.indices) {
        //i = 빠질 K의 인덱스
        if (i == 0){
            if(!isKDivisor(rightGcds[rightGcds.lastIndex - 1],inputs[i])){
                val tempPair = Pair(rightGcds[rightGcds.lastIndex - 1],inputs[i])
                answerPQ.add(tempPair)
            }
            continue
        }
        if (i == inputs.lastIndex){
            if(!isKDivisor(leftGcds[leftGcds.lastIndex - 1],inputs[i])){
                val tempPair = Pair(leftGcds[leftGcds.lastIndex - 1],inputs[i])
                answerPQ.add(tempPair)
            }
            continue
        }
        val tempGCD = gcd(leftGcds[i - 1],rightGcds[rightGcds.lastIndex -i -1])
        if(!isKDivisor(tempGCD,inputs[i])){
            val tempPair = Pair(tempGCD,inputs[i])
            answerPQ.add(tempPair)
        }
    }

    //만약 정답이 없으면 -1 출력
    if (answerPQ.isEmpty())bw.write("-1")
    else{
        val answerPair = answerPQ.poll()
        val answerGcd = answerPair.first
        val answerK = answerPair.second
        bw.write("${answerGcd} ${answerK}")
    }
    bw.flush()
    bw.close()
}
tailrec fun gcd(a :Int,b :Int) :Int{
    if(b == 0) return a
    return gcd(b,a%b)
}
fun isKDivisor(a :Int, K :Int) :Boolean{
    return K % a == 0
}