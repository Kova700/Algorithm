package DAY01.P1759

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

lateinit var isVisited :MutableList<Boolean>
lateinit var answer :ArrayList<String>
lateinit var givenChars :List<Char>
fun main(){
    System.setIn(FileInputStream("src/main/kotlin/DAY01/P1759/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))

    val (L,C) = br.readLine()!!.split(' ').map{it.toInt()}
    givenChars = br.readLine()!!.split(' ').map { it[0] }.sorted()
    isVisited = MutableList(C){false}
    answer = ArrayList()

    dfs(L,0)

    answer.forEach{
        println(it)
    }

}
fun dfs(L :Int,
        index :Int ,
        count :Int = 0){

    //2.목적지인가? : L개의 알파벳을 뽑았는가?
    if(count == L){
        val temp = givenChars.filterIndexed{index, _ ->  isVisited[index]}
        // 최소 한 개의 모음 && 최소 두 개의 자음을 갖추었는가?
        if(checkStore(temp,L)){
            //암호를 이루는 알파벳이 암호에서 증가하는 순서로 배열되었을 거다.(사전에 이미 정렬함)
            answer.add(temp.joinToString(""))
            return
        }
    }

    //3.연결된 곳을 순회 : 0 ~ c Or index ~ c
    for (i in index until givenChars.size ){
        // 4.갈 수 있는가? : visited[]
        if (isVisited[i] == false){
            // 5.간다
            //1.체크인 : visited[] = true
            isVisited[i] = true
            dfs(L,i+1,count+1)
            //6.체크아웃 : visited[] = false
            isVisited[i] = false
        }

    }
}
fun checkStore(list : List<Char>, L :Int) : Boolean{
    val moList = listOf('a','e','i','o','u')
    var count = 0
    for(mo in moList){
        if(list.contains(mo)) count++
    }
    //최소 한 개의 모음 , 최소 두 개의 자음을 가지고 있어야함
    if( L-2 < count || count < 1) return false

    return true
}