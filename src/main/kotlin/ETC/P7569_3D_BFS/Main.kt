package ETC.P7569_3D_BFS

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

private var M = 0 //가로 (2 이상 100이하)
private var N = 0 //세로 (2 이상 100이하)
private var H = 0 //높이 (1 이상 100이하)

private lateinit var box : List<List<MutableList<Int>>>
private lateinit var isVisited :Array<Array<Array<Boolean>>>
private val Q :Queue<Point> = LinkedList()
private val MZ = arrayOf(0,0,0,0,1,-1) //좌 ,우 ,위 ,아래 ,위(z), 아래(z)
private val MY = arrayOf(0,0,1,-1,0,0) //좌 ,우 ,위 ,아래 ,위(z), 아래(z)
private val MX = arrayOf(-1,1,0,0,0,0) //좌 ,우 ,위 ,아래 ,위(z), 아래(z)
private var day = 0

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P7569_3D_BFS/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    M = st.nextToken().toInt()
    N = st.nextToken().toInt()
    H = st.nextToken().toInt()
    box = List(H) { List(N) { br.readLine().split(' ').map { it.toInt() }.toMutableList() } }
    isVisited = Array(H){ Array(N){ Array(M){ false } } }

    checkBox{ z,y,x ->
        if(box[z][y][x] == 1){
            Q.add(Point(z, y, x, box[z][y][x]))
        }
    }

    bfs()

    //덜 익은게 남아있나 체크
    checkBox{ z,y,x ->
        if(box[z][y][x] == 0){
            day = -1
            return@checkBox
        }
    }

    println(day)
}

private fun checkBox(checkingFun :(z :Int, y:Int, x:Int) -> Unit){
    for (z in box.indices){
        for (y in box[z].indices){
            for (x in box[z][y].indices){
                checkingFun(z,y,x)
            }
        }
    }
}

private fun bfs(){

    while (Q.isNotEmpty()){

        for (i in 0 until Q.size){
            //큐에서 꺼낸다.
            val point = Q.poll()
            //목적지인가? -> 없음

            //연결된 곳 순회
            for (i in 0 until 6){
                val nextZ = point.z + MZ[i]
                val nextY = point.y + MY[i]
                val nextX = point.x + MX[i]
                //갈 수있는가? -> 박스 좌표 내인가? + 익지않은 토마토 있는 곳 + 가보지 않은 곳
                if (isInBox(nextZ, nextY, nextX) && (box[nextZ][nextY][nextX] == 0) && (isVisited[nextZ][nextY][nextX] == false)){
                    //간다.(체크인)
                    isVisited[nextZ][nextY][nextX] = true
                    box[nextZ][nextY][nextX] = 1
                    //큐에 넣음
                    Q.add(Point(nextZ, nextY, nextX, 1))
                }
            }
        }
        day++ //한 사이클(하루)가 끝나면 day++
    }
    day-- //마지막 카운팅 제외(다 익고나서도 마지막 날 +1되고 있음으로)
}

private fun isInBox(z :Int, y :Int, x :Int) :Boolean {
    return (z in 0 until H) && (y in 0 until N) && (x in 0 until M)
}

data class Point(
    val z :Int,
    val y :Int,
    val x :Int,
    val type :Int
)
