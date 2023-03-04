package ETC.P12904_string

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private var S = ""
private var T = ""
private var answer = 0

private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P12904/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    // (1 ≤ S의 길이 ≤ 999)
    // (2 ≤ T의 길이 ≤ 1000)
    // (S의 길이 < T의 길이)
    S = br.readLine()
    T = br.readLine()
    if (S.length == T.length){
        if (S==T) println(1)
        else println(0)
        return
    }

    dfs(T)
    println(answer)
}
private fun dfs(t :String){
    if (S.length == t.length){
        if (S == t) answer = 1
        return
    }
    when(t.last()){
        'A' -> {dfs(t.substring(0 until t.lastIndex))}
        'B' -> {dfs(t.substring(0 until t.lastIndex).reversed())}
    }
}