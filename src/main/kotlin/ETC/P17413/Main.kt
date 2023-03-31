package ETC.P17413

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private lateinit var S :String //길이는 100,000 이하
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P17413/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    S = br.readLine()
    println(getAnswer())
}
private fun getAnswer() :String{
    val answerS = mutableListOf<Char>()
    val temp = mutableListOf<Char>()
    var tagFlag = false
    for (i in 0..S.lastIndex){
        if (S[i] == '<') {
            tagFlag = true
            if (temp.isNotEmpty()){
                answerS += temp.reversed()
                temp.clear()
            }
            answerS += S[i]
            continue
        }
        if (S[i] == '>') {
            tagFlag = false
            answerS += S[i]
            continue
        }
        if (tagFlag) {
            answerS += S[i]
            continue
        }

        if (S[i] == ' '){
            if (temp.isEmpty()) continue
            answerS += temp.reversed()
            answerS += ' '
            temp.clear()
            continue
        }

        temp += S[i]
    }
    answerS += temp.reversed()

    return answerS.joinToString("")
}