package DAY01.P1759

val answer = ArrayList<String>()
fun main(){
//    val (L,C) = readLine()!!.split(' ').map{it.toInt()}
//    val givenChars = readLine()!!.split(' ').map { it[0] }.sorted()
//    val visited = MutableList(C){false}
//
//    dfs(L,visited, listOf(),givenChars,0)
//
//    answer.forEach{
//        println(it)
//    }

}
//fun dfs(L :Int,
//        visited : MutableList<Boolean>,
//        store : List<Char>,
//        givenChars : List<Char>,
//        start :Int ){
//    for (i in start until givenChars.size ){
//        //1.체크인 : visited[] = true
//        visited[i] = true
//        //2.목적지인가? : 글자 수 체크 & 자음 모음의 개수
//        if(store.size == L){
//            if(checkStore(store)){
//                answer.add(store.joinToString(""))
//                return
//            }
//        }else{
//            dfs(L,visited,store + ,givenChars,i+1)
//            //3.연결된 곳을 순회 : 0 ~ c Or index ~ c
//            //   4.갈 수 있는가? : visited[]
//            //      5.간다
//        }
//        //6.체크아웃 : visited[] = false
//        visited[i] = false
//    }
//}
//fun checkStore(list : List<Char>) : Boolean{
//    //최소 한 개의 모음
//    //최소 두 개의 자음을 가지고 있어야함
//
//    return false
//}