package ETC.P1759

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.StringTokenizer

lateinit var candidateChars : List<String>
lateinit var bw :BufferedWriter
val answer = StringBuilder()
var L = 0; var C = 0
val mo = listOf('a', 'e', 'i', 'o', 'u')
fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1759/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    bw = BufferedWriter(OutputStreamWriter(System.out))
    val st = StringTokenizer(br.readLine())
    // L = 암호의 길이 , C = 암호에 사용될 수 있는 문자 종류 개수 (L,C : 3~15)
    L = st.nextToken().toInt(); C = st.nextToken().toInt()
    candidateChars = br.readLine().split(" ").sorted() //NlogN

    for (i in 0 .. candidateChars.lastIndex){
        dfs(i)
    }

    bw.flush()
    bw.close()
}

private fun dfs(
    currentIndex :Int
){
    answer.append(candidateChars[currentIndex])

    //목적지인가? -> 길이가 L
    if (answer.length == L) {
        //최소 한개의 모음, 최소 두개의 자음을 가졌는가
        if(checkString(answer)){
            bw.write("$answer\n")
        }
        answer.deleteAt(answer.lastIndex)
        return
    }
    //연결된 곳 순회
    for (i in currentIndex +1..candidateChars.lastIndex){
        //간다.
        dfs(i)
    }

    answer.deleteAt(answer.lastIndex)
}

//최소 1개의 모음 , 최소 두개의 자음으로 구성되어야함
private fun checkString(answer: StringBuilder):Boolean{
    var moCount = 0; var jaCount = 0
    for (c in answer) {
        if(mo.contains(c)) moCount++ else jaCount++
        if ((moCount >= 1) && (jaCount >= 2)) return true
    }
    return false
}