package ETC.P10026_BFS_DFS_AreaCount

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private val MY = arrayOf(0,0,1,-1) //좌 ,우 ,위 ,아래
private val MX = arrayOf(-1,1,0,0) //좌 ,우 ,위 ,아래

private var N = 0
private lateinit var map :List<MutableList<Char>>
private lateinit var isVisited :List<MutableList<Boolean>>
private var count = 0
private var answerList = mutableListOf<Int>()

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P10026_BFS_DFS_AreaCount/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    map = List(N){br.readLine().toCharArray().toMutableList()}
    isVisited = List(N){ MutableList(N){false} }

    repeat(2){
        for (i in map.indices){
            for (j in map[i].indices){
                if (isVisited[i][j] == false){
                    count++
                    dfs(i, j)
                }
            }
        }
        answerList.add(count)
        isVisited = List(N){ MutableList(N){false} }
        count = 0
    }
    println(answerList.joinToString(" "))
}

private fun dfs(
    y :Int,
    x :Int
){
    //체크인
    isVisited[y][x] = true

    //목적지 인가? -> 없음

    //연결된 곳 순회
    for (i in 0 until 4){
        val nextY = y + MY[i]
        val nextX = x + MX[i]
        //갈 수 있나? -> 맵 내부 + 가보지 않은 곳 + 지금 색이랑 똑같은 색을 가진 곳
        if (isInMap(nextY, nextX) && (isVisited[nextY][nextX] == false) && (map[y][x] == map[nextY][nextX])){
            dfs(nextY, nextX)
        }
    }
    //체크 아웃 -> 중복 방문 방지를 위해 굳이 체크아웃 해주지 않아도될 듯
    if (map[y][x] == 'G') map[y][x] = 'R' //G가 보이면 R로 수정
}

private fun isInMap(y :Int, x :Int) :Boolean =
    (y in 0 until N) && (x in 0 until N)