package DAY01.P3055

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

val MX = arrayOf(-1,1,0,0) //좌 ,우 ,위 ,아래
val MY = arrayOf(0,0,1,-1) //좌 ,우 ,위 ,아래
var time = 0
val Q = ArrayDeque<Point>()
lateinit var dp :Array<Array<Int>> //고슴도치 발자취
var foundAnswer = false

fun main() {
    System.setIn(FileInputStream("src/main/kotlin/DAY01/P3055/input"))

    val br = BufferedReader(InputStreamReader(System.`in`))
    val (R,C) = br.readLine().split(" ").map { it.toInt() }
    val givenMap = Array(R){br.readLine().toCharArray()}

    dp = Array(R){Array(C){0}}
    lateinit var startPoint :Point

    for (indexL in givenMap.indices){
        for (indexC in givenMap[indexL].indices){
            if (givenMap[indexL][indexC] == '*'){
                Q.add(Point(indexL,indexC,'*'))
            }else if(givenMap[indexL][indexC] == 'S'){
                startPoint = Point(indexL,indexC,'S')
            }
        }
    }
    Q.add(startPoint) //Q [ *, S ] (물 먼저 움직여야 생각하기 편함)

    while (!Q.isEmpty()){
        //1. 큐에서 꺼내옴 -> *, S, .
        val tempPoint = Q.removeFirst()
        //2. 목적지 인가? -> D
        if(tempPoint.type == 'D'){
            time = dp[tempPoint.y][tempPoint.x]
            foundAnswer = true
            break
        }
        //3. 연결된 곳을 순회 -> 좌/ 우/ 위 /아래
        for (step in 0 until 4){
            var nextY = tempPoint.y + MY[step]
            var nextX = tempPoint.x + MX[step]

            //4. 갈 수 있는가? -> (공통) : 맵안인가?
            if(0 <= nextY && nextY < R && 0 <= nextX && nextX < C){
                //4. 갈 수 있는가? -> (고슴도치) : . Or D , 방문하지 않은 곳
                if(tempPoint.type =='S' || tempPoint.type == '.'){
                    if((givenMap[nextY][nextX] == '.' || givenMap[nextY][nextX] == 'D') && dp[nextY][nextX] == 0){
                        //5. 간다 (체크인)
                        dp[nextY][nextX] = dp[tempPoint.y][tempPoint.x] + 1
                        //6. 큐에 넣음
                        Q.add(Point(nextY,nextX,givenMap[nextY][nextX]))
                    }

                }else if(tempPoint.type == '*'){
                    //4. 갈 수 있는가? -> (물) : .
                    if(givenMap[nextY][nextX] == '.'){
                        //5. 간다 (체크인)
                        givenMap[nextY][nextX] = '*'
                        //6. 큐에 넣음
                        Q.add(Point(nextY,nextX,'*'))
                    }
                }
            }

        }
    }
    if(foundAnswer == true) println(time)
    else println("KAKTUS")
}

class Point(val y :Int,
            val x :Int,
            val type :Char)

