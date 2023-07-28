package ETC.P16235_Implement

import java.io.FileInputStream
import java.util.*

private var N = 0 //땅 크기 (1 ≤ N ≤ 10)
private var M = 0 //처음 심은 나무 정보 수 (1 ≤ M ≤ N^2)
private var K = 0 //목표 년수 (1 ≤ K ≤ 1,000)
private var year = 0
private lateinit var nourishmentBoard: Array<IntArray>
private lateinit var treeBoard: Array<Array<MutableList<Int>>> //tree age 보관
private lateinit var winterNourishment: Array<List<Int>> //1 ≤ winterNourishment[i][j] ≤ 100
private val MY = arrayOf(1, 1, 1, 0, -1, -1, -1, 0) //좌상, 상, 우상, 우, 우하, 하, 좌하, 좌
private val MX = arrayOf(-1, 0, 1, 1, 1, 0, -1, -1) //좌상, 상, 우상, 우, 우하, 하, 좌하, 좌
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P16235_Implement/input"))
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()
    K = st.nextToken().toInt()
    nourishmentBoard = Array(N) { IntArray(N) { 5 } }
    treeBoard = Array(N) { Array(N) { mutableListOf() } }
    winterNourishment = Array(N) { listOf() }
    repeat(N) { i ->
        winterNourishment[i] = br.readLine().split(" ").map { it.toInt() }
    }
    repeat(M) {
        //x,y = 나무의 위치, z = 나무의 나이(1 ≤ z ≤ 10)
        //입력으로 주어지는 나무의 위치는 모두 서로 다름
        val (x, y, z) = br.readLine().split(" ").map { it.toInt() }
        treeBoard[x - 1][y - 1].add(z)
    }

    while (year != K) {
        spring()
        autumn()
        winter()
        year++
    }

    var sum = 0
    for (i in 0 until N) {
        for (j in 0 until N) {
            sum += treeBoard[i][j].size
        }
    }

    println(sum)
    //출력
    //K년이 지난 후 살아남은 나무의 수를 출력
}

private fun spring() {
    for (i in 0 until N) {
        for (j in 0 until N) {
            var deadTreeNourishment = 0
            //나이 어린 나무는 뒤로 추가됨으로 인덱스 뒤에서부터 확인
            for (k in treeBoard[i][j].lastIndex downTo 0) {
                if (nourishmentBoard[i][j] >= treeBoard[i][j][k]) {
                    nourishmentBoard[i][j] -= treeBoard[i][j][k]
                    treeBoard[i][j][k]++
                    continue
                }
                //여기서 죽을 나무 결정하고, 미리 양분 계산해 놓고 끝나면 추가
                deadTreeNourishment += treeBoard[i][j][k] / 2
                treeBoard[i][j].removeAt(k)
            }
            nourishmentBoard[i][j] += deadTreeNourishment
        }
    }
}

private fun autumn() {
    //나무 번식
    for (i in 0 until N) {
        for (j in 0 until N) {
            for (k in 0..treeBoard[i][j].lastIndex) {
                if (treeBoard[i][j][k] % 5 != 0) continue

                for (q in 0 until 8) {
                    val nY = i + MY[q]
                    val nX = j + MX[q]
                    if (!isInMap(nY, nX)) continue
                    treeBoard[nY][nX].add(1)
                }
            }
        }
    }
}

private fun winter() {
    //주어진 값으로 다시 양분 추가
    for (i in 0 until N) {
        for (j in 0 until N) {
            nourishmentBoard[i][j] += winterNourishment[i][j]
        }
    }
}

private fun isInMap(y: Int, x: Int) =
    y in 0 until N && x in 0 until N