package ETC.P15684_DFS_implement

import java.io.FileInputStream
import java.util.*

private var N = 0 //세로선 개수 (2 ≤ N ≤ 10)
private var M = 0 //가로선 개수 (0 ≤ M ≤ (N-1)×H)
private var H = 0 //세로선마다 가로선을 놓을 수 있는 위치의 개수 (1 ≤ H ≤ 30)
private lateinit var bridge: Array<BooleanArray>
private var answer = Int.MAX_VALUE
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P15684_DFS_implement/input"))
    val br = System.`in`.bufferedReader()
    var st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()
    H = st.nextToken().toInt()
    //bridge[i][j] == i층에 j번 사다리와 j+1번 사다리 사이에 다리가 있는지 유무
    bridge = Array(H) { BooleanArray(N - 1) }
    repeat(M) {
        //가로선의 정보는 두 정수 a과 b로 나타낸다. (1 ≤ a ≤ H, 1 ≤ b ≤ N-1)
        // b번 세로선과 b+1번 세로선을 a번 점선 위치에서 연결했다는 의미이다.
        val (a, b) = br.readLine().split(" ").map { it.toInt() }
        bridge[a - 1][b - 1] = true
    }

    dfs(0, 0)
    println(if (answer == Int.MAX_VALUE) -1 else answer)
}

private fun dfs(y: Int, count: Int) {
    if (count > 3) return
    if (isCompleted()) {
        answer = minOf(answer, count)
        return
    }

    //사다리 생성
    for (i in y until H) {
        for (j in 0 until N - 1) {
            //현재칸에 사다리가 있거나, 이전칸 혹은 다음칸에 사다리가 있다면 PASS
            if (bridge[i][j] ||
                (j != 0 && bridge[i][j - 1]) ||
                (j != N - 2 && bridge[i][j + 1])
            ) continue
            bridge[i][j] = true
            dfs(i, count + 1)
            bridge[i][j] = false
        }
    }
}

private fun isCompleted(): Boolean {
    for (i in 0 until N) {
        if (!climbLadder(i)) return false
    }
    return true
}

private fun climbLadder(x: Int): Boolean {
    var cY = 0
    var cX = x
    while (cY < H) {
        //왼쪽이나 오른쪽에 다리 있으면 타고 (양쪽에 있을 수는 없음)
        //없으면 위로 한 칸 이동
        when (cX) {
            0 -> if (bridge[cY][0]) cX++
            N - 1 -> if (bridge[cY][N - 2]) cX--
            else -> {
                when {
                    bridge[cY][cX - 1] -> cX--
                    bridge[cY][cX] -> cX++
                }
            }
        }
        cY++
    }
    return cX == x
}