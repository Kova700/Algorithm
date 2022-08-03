package ETC.P16236

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.PriorityQueue

val MX = arrayOf(0,0,-1,1)//상하좌우
val MY = arrayOf(1,-1,0,0)//상하좌우
lateinit var Q : ArrayDeque<Point>
lateinit var priorityQueue: PriorityQueue<Point>
lateinit var dp : Array<Array<Int>> //BFS 이동 흔적
var answer = 0
var time = 0
var sharkLevel = 2
var eatCount = 0
fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P16236/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val N = br.readLine().toInt()
    val map = Array(N){br.readLine().split(" ").map { it.toInt() }.toMutableList()}

    dp = Array(N){Array(N){0}}
    //먹잇감 우선순위 : 거리 작은 순 > Y작은 순  > X작은 순
    priorityQueue = PriorityQueue<Point>(
        compareBy<Point> { it.y }
        .thenBy { it.x })

    //BFS
    //StartPoint 큐에 넣음
    Q = ArrayDeque<Point>()
    for (r in map.indices) {
        for (c in map[r].indices) {
            if(map[r][c] == 9){
                map[r][c] = 0
                Q.add(Point(r,c,0))
            }
        }
    }
    while (Q.isNotEmpty()){

        if(Q.first().time == time){
            //큐에서 꺼냄
            val point = Q.removeFirst()
            //연결된 곳 순회 (상하좌우) //목적지를 찾는 과정
            for (i in 0..3){
                val nextY = point.y + MY[i]; val nextX = point.x + MX[i]
                // 맵안인가?
                if((nextY in 0 until N) && (nextX in 0 until N)){
                    //레벨이 낮거나 같은 물고기인가? (혹은 빈칸) && 가지 않았던 길인가?
                    if((map[nextY][nextX] <= sharkLevel) && (dp[nextY][nextX] == 0)){
                        //체크인
                        dp[nextY][nextX] = dp[point.y][point.x] + 1
                        //먹잇감인가? 맞으면 P.Q에도 넣음
                        if((map[nextY][nextX] != 0) && (map[nextY][nextX] < sharkLevel)){
                            priorityQueue.add(Point(nextY,nextX,dp[nextY][nextX]))
                        }
                        //아니면 일반 큐에만 넣음
                        Q.add(Point(nextY,nextX,dp[nextY][nextX]))
                    }
                }
            }
        //한 사이클로 나올 수 있는 큐가 다 나왔다면
        }else{
            time++ //전체 시간 갱신
            //P.Q에 들어가있는 우선순위가 가장 높은 장소로 이동
            if (priorityQueue.isNotEmpty()) {
                val target = priorityQueue.poll()
                //맵 빈칸 표시
                map[target.y][target.x] = 0
                eatCount++
                //levelUp 가능 여부 체크
                if (eatCount == sharkLevel) {
                    sharkLevel++
                    eatCount = 0
                }
                //옮겨진 위치에서 다시 우선순위 높은 지점을 탐색해야함으로 초기화
                Q.clear()
                priorityQueue.clear()
                answer += time //시간 백업
                dp = Array(N) { Array(N) { 0 } }
                //현재 위치에서 다시 BFS 탐색
                Q.add(Point(target.y, target.x, 0))
                time = 0
            }
        }
        //P.Q가 비어 있으면 일반큐 순회
    }
    println(answer)
}
class Point (val y :Int = 0,
             val x :Int =0,
             val time :Int =0) //time : 현재 위치로부터의 거리 (= 걸린 시간)