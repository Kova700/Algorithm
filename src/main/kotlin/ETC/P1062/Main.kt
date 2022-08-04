package ETC.P1062

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.StringTokenizer

var N = 0
var K = 0
lateinit var inputs :Array<String>
var answer = 0
val isVisited = Array(26){false}
var learnCount = 5
fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1062/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    N = st.nextToken().toInt()
    K = st.nextToken().toInt()
    inputs = Array(N){br.readLine()}
    // N (1~50) :주어진 단어의 개수  ,K (1~26) : 가르칠 수 있는 글자 수
    //어떤 글자 K개를 가르쳤을 때, 읽을 수 있는 단어 개수의 최댓값은?
    //남극의 모든 단어는 anta로 시작되고 tica로 끝난다. = 꼭 알아야 하는 글자 (a,n,t,i,c) 5개

    if(K < learnCount) {
        println("0")
        return
    }

    // 무조건 배워야 함으로 체크
    isVisited['a' - 'a'] = true
    isVisited['n' - 'a'] = true
    isVisited['t' - 'a'] = true
    isVisited['i' - 'a'] = true
    isVisited['c' - 'a'] = true

    countHowManyRead()
    for (i in 0 until 26) {
        if (isVisited[i] == false){
            dfs(i)
        }
    }
    println(answer)
}
fun dfs(i :Int){
    //체크인
    isVisited[i] = true
    learnCount++
    //목적지 인가? => 단어를 K개 배웠나?
    if (learnCount == K) {
        //단어 몇개 읽을 수 있는지 확인
        countHowManyRead()
    }else{
    //연결된 곳 순회
        for (t in i+1 until  26){
            //갈 수 있는가? => 다음 단어 && 배우지 않은 단어
            if (isVisited[t] == false){
                //간다.
                dfs(t)
            }
        }
    }
    //체크아웃
    isVisited[i] = false
    learnCount--
}

fun countHowManyRead(){
    var count = 0
    for (input in inputs) {
        var canRead = true
        for (c in input){
            if(isVisited[c - 'a'] == false) {
                canRead = false
                break
            }
        }
        if(canRead) count++
    }
    //answer 갱신
    if (answer < count) answer = count
}