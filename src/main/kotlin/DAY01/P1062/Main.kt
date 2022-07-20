import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

lateinit var isVisited :MutableList<Boolean>
var maxCount = 0
fun main(){
    System.setIn(FileInputStream("src/main/kotlin/DAY01/P1062/input.txt"))

    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N,K) = br.readLine().split(' ').map{it.toInt()}
    //val givenWords = Array(N){br.readLine().toString().replace("[antic]".toRegex(),"")}
    val givenWords = Array(N){br.readLine().toString()}

    // anta, tica 에서 배울 수 있는 글자 5개
    if (K < 5) {
        println(0)
        return
    } else if (K == 26) {
        // 알파벳 다 배울 수 있으면 단어 다 배울 수 있음
        println(N)
        return
    }
    isVisited = MutableList<Boolean>(26){false}
    isVisited['a' - 'a'] = true
    isVisited['n' - 'a'] = true
    isVisited['t' - 'a'] = true
    isVisited['i' - 'a'] = true
    isVisited['c' - 'a'] = true

    //사실상 개수만 세면 됨으로 어떤 조합이 있는지는 계산할 필요가 없음
//    val store = listOf('a','n','t','i','c')
//    val allAlphabets = ('a'..'z').toList()

    //K개의 글자를 가르칠 때 , 학생들이 읽을 수 있는 단어 개수의 최댓값을 출력
    dfs(K,givenWords,0)
    println(maxCount)
}
//재귀시에 문제 메모리 커트라인이 낮다면 파라미터에 메모리를 많이 잡아먹는 요소를 줄이자.
fun dfs(K :Int ,
        givenWords :Array<String> ,
        index :Int,
        cnt :Int = 0){
//2.목적지인가? : 목적지 = 깊이가 K
//    -> 읽은 수 있는 단어의 개수 계산 후 Max 갱신
    if(cnt == K - 5){ //여기 차이 인가?
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
    for (i in index+1 until  26){
        //4.갈 수 있는가? : 아직 배우지 않은 알파벳
        if(isVisited[i] == false){
            //5.간다
            //1.체크인
            isVisited[i] = true
            dfs(K,givenWords,i,cnt+1)
            isVisited[i] = false
            //6.체크아웃
        }
    }
}
