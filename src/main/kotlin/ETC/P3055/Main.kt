package ETC.P3055

import java.io.*

val MX = arrayOf(0,0,-1,1) //상,하,좌,우
val MY = arrayOf(1,-1,0,0) //상,하,좌,우
lateinit var dp : Array<Array<Int>> //고슴도치가 지나온 길을 체크인 하기 위해
fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P3055/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    //동시에 움직이는게 2개 = BFS로 접근
    //물이 먼저 움직이는게 생각하기 편하니 물 먼저 움직이자

    val (R,C) = br.readLine().split(" ").map { it.toInt() } // (1~50)
    val map = Array(R){br.readLine().toCharArray()}
    dp = Array(R){Array(C){0}}
    val Q = ArrayDeque<Point>()

    //큐에 startPoint 집어 넣기
    var temp = Point()
    for (r in map.indices) {
        for (c in map[r].indices) {
            if(map[r][c] == '*'){
                Q.add(Point(r,c,map[r][c]))
            }else if (map[r][c] == 'S'){
                temp = Point(r,c,map[r][c])
            }
        }
    }
    Q.add(temp)

    while (Q.isNotEmpty()){
        //큐에서 꺼내옴
        val point = Q.removeFirst()
        //목적지인가? (고슴도치가 D에 도착)
        if (point.data == 'D') {
            println(point.time)
            return
        }
        //연결된 곳을 순회
        for (i in 0..3){
            val nextY = point.y + MY[i]; val nextX = point.x + MX[i]
            //갈 수 있는가? 공통 : 맵내부
            if ((nextY in 0 until R) && (nextX in 0 until C)){
                //고슴도치 : (. 혹은 D) 이고  방문하지 않았던 곳
                if ((point.data == 'S') || (point.data == '.')){
                    if(((map[nextY][nextX] == '.') || (map[nextY][nextX] == 'D')) && (dp[nextY][nextX] == 0)){
                        //체크인
                        dp[nextY][nextX] = 1
                        //큐에 넣음
                        Q.add(Point(nextY,nextX,map[nextY][nextX],point.time+1))
                    }
                //물 : . 혹은 S
                }else if (point.data == '*'){
                    if((map[nextY][nextX] == '.') || (map[nextY][nextX] == 'S')){
                        //체크인
                        map[nextY][nextX] = '*'
                        //큐에 넣음
                        Q.add(Point(nextY,nextX,'*'))
                    }
                }
            }
        }
    }
    println("KAKTUS")
}
class Point(val y :Int =0,
            val x :Int =0,
            val data :Char = ' ',
            val time :Int = 0)