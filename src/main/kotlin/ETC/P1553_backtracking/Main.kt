package ETC.P1553_backtracking

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var count = 0
private val MY = arrayOf(0,1)//우 상
private val MX = arrayOf(1,0)//우 상
private var isDominoExist = Array(7){ BooleanArray(7){true} }
private val isVisited = Array(8){ BooleanArray(7){false} }
private var map = Array(8){ intArrayOf() }

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1553_backtracking/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    repeat(8){//8×7인 격자 , 격자에는 0부터 6까지의 수만 존재
        map[it] = br.readLine().toCharArray().map { i -> i.digitToInt() }.toIntArray()
    }
    dfs(0,0)
    println(count)
}

private fun dfs(y :Int, x :Int){
    if (y > 7) {
        count++
        return //끝 도달
    }
    isVisited[y][x] = true

    //전체 도미노 중에서 해당 칸(y,x)에 놓을 수 있는 도미노 탐색
    for (i in 0..6){
        if (i != map[y][x]) continue // 첫번째 숫자부터 다르면 Pass

        for (j in 0..6) {
            if (!isDominoExist[i][j]) continue //도미노가 해당 번호가 없으면 Pass
            //첫번째 숫자는 같으니 두번째 숫자만 확인하면 됨
            //i | j를 가로로 놓아보고, 세로로 놓아봐야함 (우측칸 아랫칸 확인해야함)
            for (k in 0..1){
                val my = y + MY[k]
                val mx = x + MX[k]
                //맵안, 가보지 않은 곳, j값을 가지고 있는 곳
                if (!isInMap(my,mx) || isVisited[my][mx] ||map[my][mx] != j) continue

                isVisited[my][mx] = true
                isDominoExist[i][j] = false
                isDominoExist[j][i] = false

                val (nextY, nextX) = getNextPosition(y,x)
                dfs(nextY, nextX)

                isDominoExist[i][j] = true
                isDominoExist[j][i] = true
                isVisited[my][mx] = false
            }
        }
    }
    isVisited[y][x] = false
}
private fun isInMap(y :Int, x :Int) =
     (y in 0 until 8) && (x in 0 until 7)

private fun getNextPosition(y :Int, x :Int) :Pair<Int,Int>{
    var (nextY ,nextX) = if (x == 6) Pair(y + 1, 0) else Pair(y, x + 1)

    //다음칸이 가봤던곳이라면 pass
    while (isVisited[nextY][nextX]){
        nextX++
        if (nextX > 6){
            nextX = 0
            nextY += 1
        }
        if (nextY > 7) break
    }
    return Pair(nextY,nextX)
}