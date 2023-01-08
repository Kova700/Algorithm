package ETC.P2178

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.StringTokenizer
import kotlin.math.min

private val MY = arrayOf(0,1,0,-1) //좌 ,우 ,위 ,아래
private val MX = arrayOf(1,0,-1,0) //좌 ,우 ,위 ,아래
private var N = 0
private var M = 0
private lateinit var map : List<MutableList<Int>>
private lateinit var isVisited :List<MutableList<Boolean>>

private var answer = Int.MAX_VALUE
private fun main(){
   System.setIn(FileInputStream("src/main/kotlin/ETC/P2178/input"))
   val br = BufferedReader(InputStreamReader(System.`in`))
   val st = StringTokenizer(br.readLine())
   N = st.nextToken().toInt()
   M = st.nextToken().toInt()
   map = List(N){ br.readLine().map { it.digitToInt() }.toMutableList() }
   isVisited = List(N){MutableList(M){false}}

   //(1, 1)에서 출발하여 (N, M)의 위치로 이동할 때 지나야 하는 최소의 칸 수를 구하는 프로그램을 작성하시오.
   //첫째 줄에 지나야 하는 최소의 칸 수를 출력한다. 항상 도착위치로 이동할 수 있는 경우만 입력으로 주어진다.
   dfs(0,0,1)
   println(answer)
}

//시간 초과 나옴
private fun dfs(
   y :Int,
   x :Int,
   count :Int
){
   //체크인
   isVisited[y][x] = true
   //목적지인가? (최소값 찾아야함으로 return 하지 않음)
   if ((y == N-1) && (x == M-1)){
      answer = min(count, answer)
   }
   //연결된 곳 순회
   for (i in 0 until 4){
      val nextY = y + MY[i]
      val nextX = x + MX[i]
      //갈 수 있는가? -> 맵 내부 + 값이 1 + 가보지 않은 곳
      if (isInMap(nextY,nextX) && (map[nextY][nextX] == 1) && (isVisited[nextY][nextX] == false)){
         //간다.
         dfs(nextY,nextX,count+1)
      }
   }
   //체크아웃
   isVisited[y][x] = false
}
private fun isInMap(
   y :Int,
   x :Int
) :Boolean{
   return (y in 0 until N) && (x in 0 until M)
}