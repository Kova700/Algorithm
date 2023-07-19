package ETC.P14889_BruteForce

import java.io.FileInputStream
import kotlin.math.abs

private var N = 0 //축구 인원 (4 ≤ N ≤ 20, N은 짝수)

//board[i][j] == i가 j와 같은팀이 될 때 얻는 능력치 (board[i][j] != board[j][i]일 수 있음)
private lateinit var board: Array<List<Int>>
private lateinit var isPicked: BooleanArray //해당 번호를 뽑았는지 체크
private var answer = Int.MAX_VALUE
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P14889_BruteForce/input"))
    val br = System.`in`.bufferedReader()
    N = br.readLine().toInt()
    board = Array(N) { listOf() }
    isPicked = BooleanArray(N)

    repeat(N) { i ->
        //board[i][i]는 항상 0이고,
        //board[i][j]는 1보다 크거나 같고, 100보다 작거나 같은 정수이다.
        board[i] = br.readLine().split(" ").map { it.toInt() }
    }

    //한 팀을 뽑으면 자동으로 다른 팀은 완성됨을 이용
    dfs(0, 0)
    println(answer)
}

private fun dfs(start: Int, count: Int) {
    if (count == N / 2) {
        calculatePoint()
        return
    }
    //여기 for문의 시작점을 0부터 하면 20의 20승의 시간 복잡도가 나옴으로 시간 초과가 나온다.
    //그래서 앞에서 검사한 숫자는 넘어서 시작하는게 좋다. (DFS와 브루트포스의 차이점)
    for (i in start until N) {
        if (isPicked[i]) continue
        isPicked[i] = true
        dfs(i + 1, count + 1)
        isPicked[i] = false
    }
}

private fun calculatePoint() {
    var startPoint = 0
    val startTeam = (0 until N).filter { isPicked[it] }
    for (i in startTeam) {
        for (j in startTeam) {
            if (i == j) continue
            startPoint += board[i][j]
        }
    }
    var linkPoint = 0
    val linkTeam = (0 until N).filter { !isPicked[it] }
    for (i in linkTeam) {
        for (j in linkTeam) {
            if (i == j) continue
            linkPoint += board[i][j]
        }
    }
    answer = minOf(answer, abs(startPoint - linkPoint))
}