package ETC.P2667_BFS_DFS

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private val MY = arrayOf(0,0,1,-1) //좌 ,우 ,위 ,아래
private val MX = arrayOf(-1,1,0,0) //좌 ,우 ,위 ,아래
private var N = 0   //정사각형 크기 (5이상 25이하)
private lateinit var map : MutableList<MutableList<Int>>

private var count = 0
private var danji = 0
private var countList = mutableListOf <Int>()

private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2667/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    N = br.readLine().toInt()
    map = MutableList(N){br.readLine().map { it.digitToInt() }.toMutableList()}

    for (i in map.indices){
        for (j in map[i].indices){
            //처음 방문하는 단지 찾으면 DFS 돌려~
            if ((map[i][j] == 1)){
                count++
                dfs(i, j)
                countList.add(count)
                count = 0
                danji++
            }
        }
    }
    println(danji)
    countList.sorted().forEach { println(it) }
}

private fun dfs(
    y :Int,
    x :Int
){
    //체크인
    map[y][x] = 0
    //목적지 인가? -> 없음

    //연결된 곳 순회
    for (i in 0 until 4){
        val nextY = y + MY[i]
        val nextX = x + MX[i]
        //갈 수 있는가? -> 지도 안인가? + 1인가? + 처음 밞은 땅인가?
        if (isInMap(nextY, nextX) && (map[nextY][nextX] == 1)){
            //간다.
            count++
            dfs(nextY,nextX)
        }
    }
    //체크아웃 -> 할 필요가 없음 (한 분기가 연결된 곳의 끝까지가면 다른 분기는 굳이 돌려보지 않아도 됨으로)
}

private fun isInMap(y :Int, x :Int) :Boolean{
    return (y in 0 until N) && (x in 0 until N)
}