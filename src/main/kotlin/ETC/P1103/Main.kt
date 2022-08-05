package ETC.P1103

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.StringTokenizer

val MX = arrayOf(0,0,-1,1)//상하좌우
val MY = arrayOf(1,-1,0,0)//상하좌우
var N = 0; var M = 0
lateinit var map : Array<List<Char>>
lateinit var isVisited : Array<Array<Boolean>>
var answer = 0
var isInfinity = false
lateinit var dp :Array<Array<Int>>
fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1103/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt();  M = st.nextToken().toInt() //N,M (1~50)
    map = Array(N){br.readLine().toList()}
    isVisited = Array(N){Array(M){false}}
    dp = Array(N){Array(M){0}}

    dfs(0,0,1)

    if (isInfinity == false) println(answer)
    else println(-1)
}

fun dfs(y :Int,
        x :Int,
        moveCount : Int){
    val currentValue = map[y][x].digitToInt()
    //체크인
    dp[y][x] = moveCount
    isVisited[y][x] = true
    //연결된 곳 순회
    for (i in 0..3){
        if (isInfinity == true) return

        val nextY = y + (MY[i] * currentValue); val nextX = x + (MX[i] * currentValue)
        //갈 수 있는가? 맵안, H가 아닌곳
        if((nextY !in 0 until N) || (nextX !in 0 until M) || map[nextY][nextX] == 'H'){
            if (answer < moveCount) answer = moveCount
            continue
        }
        //간다.
        if (isVisited[nextY][nextX] == false){
            if(dp[nextY][nextX] > moveCount) continue
            else{
                dfs(nextY,nextX,moveCount + 1)
            }
        //이미 밟았던 곳이라면 무한가능
        }else{
            isInfinity = true
            return
        }
    }
    //체크아웃
    isVisited[y][x] = false
}