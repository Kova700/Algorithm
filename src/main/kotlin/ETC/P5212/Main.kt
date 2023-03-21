package ETC.P5212

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private val MY = arrayOf(0,1,0,-1)//동남서북
private val MX = arrayOf(1,0,-1,0)//동남서북
private var R = 0
private var C = 0
private lateinit var map : Array<CharArray>
private lateinit var pickedMap : Array<BooleanArray>
private var maxR = Int.MIN_VALUE
private var minR = Int.MAX_VALUE
private var maxC = Int.MIN_VALUE
private var minC = Int.MAX_VALUE
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P5212/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    br.readLine().split(" ").apply {
        R = this[0].toInt()
        C = this[1].toInt()
    }
    map = Array(R){charArrayOf()}
    pickedMap = Array(R){ BooleanArray(C){false} }
    repeat(R){
        // 'X'는 땅을 나타내고, '.'는 바다를 나타낸다.
        map[it] = br.readLine().toCharArray()
    }

    for (r in map.indices) {
        for (c in map[r].indices){
            if (map[r][c] == '.') continue

            var oceanCount = 0
            for (i in 0 until 4){
                val nextY = r + MY[i]
                val nextX = c + MX[i]
                if(!isInMap(nextY,nextX) || map[nextY][nextX] == '.') oceanCount++
            }
            if (oceanCount in 3..4) {
                pickedMap[r][c] = true
                continue
            }
            maxR = Math.max(maxR, r)
            minR = Math.min(minR, r)
            maxC = Math.max(maxC, c)
            minC = Math.min(minC, c)
            //삼면이상이 바다 혹은 맵 밖인가( == 바다)
                //바다로 수정
            //아니다
                //아닌 X들 중에서 최소 col, 최대 col , 최소 row, 최소 row 저장
        }
    }

    //구했던 row범위 , col범위 출력
    for(r in minR..maxR){
        for (c in minC..maxC){
            if(pickedMap[r][c] == true) map[r][c] = '.'
            print(map[r][c])
        }
        println()
    }
}
private fun isInMap(y :Int, x :Int) =
    (y in 0 until R) && (x in 0 until C)