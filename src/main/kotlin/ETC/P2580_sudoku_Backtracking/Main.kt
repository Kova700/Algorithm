package ETC.P2580_sudoku_Backtracking

import java.io.FileInputStream

private lateinit var board: Array<IntArray>
private var findAnswer = false
fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P2580_sudoku_Backtracking/input"))
    val br = System.`in`.bufferedReader()
    board = Array(9) { br.readLine().split(" ").map { it.toInt() }.toIntArray() }

    //board을 탐색하다가 0을 만나면
    //가로줄, 세로줄, 9칸내부 확인하고 해당 칸에 들어 갈 수 있는 숫자 후보군을 구한다.
    //해당 후보군들을 브루트포스식으로 대입하며 모든 경우의 수를 확인

    dfs(0)
}

//답을 한 개라도 찾았으면 강제 종료하는게 좋아 보임
private fun dfs(y: Int) {
    if (findAnswer) return //답을 한 개라도 찾았으면 강제 종료

    loop@ for (r in y until 9) {
        for (c in 0 until 9) {
            if (board[r][c] != 0) continue

            for (num in 1 until 10) {
                if (!isAvailable(r, c, num)) continue
                board[r][c] = num
                dfs(r)
                board[r][c] = 0
            }
            break@loop //0이었던 지점에서 후보군을 다 집어넣고, 다음칸을 탐색하지 않아도 됨으로 break
        }

        if (findAnswer) return //답을 한 개라도 찾았으면 강제 종료

        if (r == 8) {
            findAnswer = true
            val sb = StringBuilder()
            for (i in 0 until 9) {
                sb.append("${board[i].joinToString(" ")}\n")
            }
            println(sb)
        }
    }
}

private fun isAvailable(r: Int, c: Int, num: Int): Boolean {
    for (i in 0 until 9) {
        if (board[r][i] == num || board[i][c] == num) return false
    }

    val rowStart = getStartIndex(r)
    val colStart = getStartIndex(c)
    for (row in rowStart..rowStart + 2) {
        for (col in colStart..colStart + 2) {
            if (board[row][col] == num) return false
        }
    }

    return true
}

//처음에는 IntRange로 반환했었는데 메모리사용량이 너무 커서 Int반환으로 수정★★
fun getStartIndex(num: Int): Int {
    return when (num) {
        in 0..2 -> 0
        in 3..5 -> 3
        in 6..8 -> 6
        else -> throw Exception("unknown Range")
    }
}