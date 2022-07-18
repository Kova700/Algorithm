var maxCount = 0
fun main(){
    val (N,K) = readLine()!!.split(' ').map{it.toInt()}
    val words = Array(N){readLine()!!.toString().replace("[antic]".toRegex(),"")}
    val allAlphabets = ('a'..'z').toList()
    val baseAlphabets = listOf('a','n','t','i','c')
    //K개의 글자를 가르칠 때 , 학생들이 읽을 수 있는 단어 개수의 최댓값을 출력
    dfs(K,words,baseAlphabets,allAlphabets,0)
    println(maxCount)
}
fun dfs(K :Int ,
        words :Array<String> ,
        store :List<Char> ,
        allAlphabets : List<Char>,
        index :Int){
//2.목적지인가? : 목적지 = 깊이가 K
//    -> 읽은 수 있는 단어의 개수 계산 후 Max 갱신
    if(store.size == K){
        var count = 0
        words.forEach { word ->
            if(store.containsAll(word.toList())) count++
        }
        if(count > maxCount) maxCount = count
        return
    }
    //3.연결된 곳을 순회 : 연결된 곳 = 모든 알파벳
    for (i in index+1 until  allAlphabets.size){
        //4.갈 수 있는가? : 아직 배우지 않은 알파벳
        if(!store.contains(allAlphabets[i])){
            //5.간다
            dfs(K,words,store + allAlphabets[i],allAlphabets,i)
        }
    }
//    1.체크인 (생략)
//    6.체크아웃 (생략)

}
