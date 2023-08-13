package ETC.P20055_implement

import java.io.FileInputStream
import java.util.*

private var N = 0 //컨베이어 벨트 길이 (2 ≤ N ≤ 100)
private var K = 0 //목표 내구도가 0인 칸의 개수(1 ≤ K ≤ 2N)
private lateinit var durabilityList: IntArray //durabilityList[i] = i번 칸의 내구도
private lateinit var isExistRobotInBelt: BooleanArray  //isExistRobotInBelt[i] = i번 칸의 로봇 유무
private var zeroCount = 0
private var beltStart = 0 //로봇을 올리는 위치 인덱스
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P20055_implement/input"))
    val br = System.`in`.bufferedReader()

    var st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    K = st.nextToken().toInt()

    //1 ≤ durabilityList[i] ≤ 1,000
    //durabilityList[i] = i번 칸의 내구도
    durabilityList = br.readLine().split(" ").map { it.toInt() }.toIntArray()
    isExistRobotInBelt = BooleanArray(2 * N)

    var count = 1
    while (true) {
        rotateBelt()
        moveRobot()
        putRobotOnBelt()
        if (zeroCount >= K) break
        count++
    }

    println(count)
}

private fun rotateBelt() {
    //벨트가 로봇과 같이 한 칸 회전(빠질 로봇들 빠져야함)
    val beltEnd = getBeltEnd()
    isExistRobotInBelt[beltEnd] = false
    beltStart = getNextIntToMinus(beltStart)
}

private fun moveRobot() {
    //로봇이 이동하면 이동한 칸의 내구도는 1감소
    //가장 오른쪽 로봇부터 오른쪽으로 한칸 이동
    //  이동 할 수없으면 가만히 있음
    //      이동하려는 칸에 로봇이 없고 그 칸의 내구도가 1이상 있어야함

    val beltEnd = getBeltEnd()
    isExistRobotInBelt[beltEnd] = false
    var i = getNextIntToMinus(beltEnd)
    val target = getNextIntToMinus(beltStart)
    while (i != target) {
        val nextI = getNextIntToPlus(i)
        if (!isExistRobotInBelt[i] || durabilityList[nextI] < 1 || isExistRobotInBelt[nextI]) {
            i = getNextIntToMinus(i)
            continue
        }
        isExistRobotInBelt[i] = false
        durabilityList[nextI]--
        if (durabilityList[nextI] == 0) zeroCount++
        isExistRobotInBelt[nextI] = true
        i = getNextIntToMinus(i)
    }
}

private fun putRobotOnBelt() {
    //로봇을 올리면 올라간 칸의 내구도는 1감소
    //가장 왼쪽칸에 내구도가 0이 아니라면 로봇을 올림
    if (durabilityList[beltStart] == 0) return

    isExistRobotInBelt[beltStart] = true
    durabilityList[beltStart]--
    if (durabilityList[beltStart] == 0) zeroCount++
}

private fun getNextIntToMinus(i: Int) =
    if (i == 0) 2 * N - 1 else i - 1

private fun getNextIntToPlus(i: Int) =
    if (i == 2 * N - 1) 0 else i + 1

private fun getBeltEnd(): Int {
    if (beltStart + (N - 1) <= (2 * N - 1)) return beltStart + (N - 1)
    return beltStart + (N - 1) - (2 * N - 1) - 1
}