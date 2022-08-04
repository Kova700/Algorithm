import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

var N = 0
var K = 0
lateinit var isVisited :MutableList<Boolean>
lateinit var givenWords :Array<String>
var maxCount = 0
fun main(){
    System.setIn(FileInputStream("src/main/kotlin/DAY01/P1062/input.txt"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    K = st.nextToken().toInt()
    givenWords = Array(N){br.readLine().toString()}
    // N (1~50) :주어진 단어의 개수  ,K (1~26) : 가르칠 수 있는 글자 수
    //어떤 글자 K개를 가르쳤을 때, 읽을 수 있는 단어 개수의 최댓값은?
    //남극의 모든 단어는 anta로 시작되고 tica로 끝난다. = 꼭 알아야 하는 글자 (a,n,t,i,c) 5개

    if (K < 5) {
        println(0)
        return
    } else if (K == 26) {
        println(N)
        return
    }
    isVisited = MutableList<Boolean>(26){false}
    isVisited['a' - 'a'] = true
    isVisited['n' - 'a'] = true
    isVisited['t' - 'a'] = true
    isVisited['i' - 'a'] = true
    isVisited['c' - 'a'] = true

    dfs(0,5)

    println(maxCount)
}
//재귀시에 메모리 소비가 많다면 파라미터에 메모리를 많이 잡아먹는 요소를 줄이자.
//(전역 변수로 변경)
fun dfs(index :Int,
        cnt :Int){
    //2.목적지인가? : 목적지 = 깊이가 K
    if(cnt == K){
        // -> 읽은 수 있는 단어의 개수 계산 후 Max 갱신
        var count = 0
        for (word in givenWords){
            var canRead = true
            for (c in word){
                if(isVisited[c - 'a'] == false) {
                    canRead = false
                    break
                }
            }
            if(canRead) count++
        }
        if(count > maxCount) maxCount = count
        return
    }
    //3.연결된 곳을 순회 : 연결된 곳 = 모든 알파벳
    for (i in index until  26){
        //4.갈 수 있는가? : 아직 배우지 않은 알파벳
        if(isVisited[i] == false){
            //5.간다
            //1.체크인
            isVisited[i] = true
            dfs(i+1,cnt+1)
            //6.체크아웃
            isVisited[i] = false
        }
    }
}
