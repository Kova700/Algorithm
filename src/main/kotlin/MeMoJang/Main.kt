package MeMoJang

fun main(){
    println("I'm Fine!")
}

//var caseCount = 0
//fun solution(n: Int): Int {
//    temp(0,n,listOf<Pair<Int,Int>>())
//
//    return caseCount
//}
//
//fun temp(nowFloor :Int,
//         target :Int,
//         QStore :List<Pair<Int,Int>>){ //Pair(층,인덱스)
//    println("QStore :$QStore")
//    if(nowFloor == target){
//        println("하나 카운트 +1 !!")
//        caseCount++
//        return
//    }else{
//        var canGoList = mutableListOf<Int>()
//        for(i in 0 until target){ //i = 검사할 인덱스
//            if(check(i,QStore,nowFloor)){ canGoList.add(i) }
//        }
//        println("canGoList :$canGoList")
//        if(canGoList.size == 0) return
//        else{
//            canGoList.forEach{
//                temp(nowFloor+1,target,QStore+Pair(nowFloor,it))
//            }
//        }
//    }
//}

fun check(a :Int,QStore :List<Pair<Int,Int>>,nowFloor :Int):Boolean{
    if(QStore.map{it.second}.contains(a)) return false //인덱스
    if(QStore.filter { Math.abs(it.first - nowFloor) == Math.abs(it.second - a) }.isNotEmpty()) return false
    return true
}

//fun main(){
//    val N = readLine()!!.toInt()
//    val AList = ArrayList<Int>()
//    AList.addAll(readLine()!!.split(' ').map(String::toInt))
//    val BList = ArrayList<Int>()
//    BList.addAll(readLine()!!.split(' ').map(String::toInt))
//    println(solution(N,AList,BList))
//}
//fun solution(N :Int, AList :ArrayList<Int>, BList :ArrayList<Int>) :Int{
//
//
//    return 0
//}

//var maxCount = 0
//fun main() {
//    val (N, K) = readLine()!!.split(' ').map(String::toInt)
//    val damageList = ArrayList<Int>()
//    damageList.addAll(readLine()!!.split(' ').map(String::toInt))
//    val peopleCountList = ArrayList<Int>()
//    peopleCountList.addAll(readLine()!!.split(' ').map(String::toInt))
//    println(solution(N,K,damageList,peopleCountList))
//}
//fun solution(N :Int,K :Int,damageList :ArrayList<Int>,peopleCountList :ArrayList<Int>) :Int{
//    // N : 마을 수 , K: 체력
//    combination(N,K,0,0,MutableList(N){false},damageList,peopleCountList)
//
//    return maxCount
//}
//fun combination(N :Int,
//        K :Int,
//        stackDamage :Int,
//        start :Int,
//        ck :MutableList<Boolean>,
//        damageList :ArrayList<Int>,
//        peopleCountList :ArrayList<Int>){
//    if(stackDamage > K) {
//        return
//    }
//    else{
//        val temp =peopleCountList.filterIndexed{ index, i -> ck[index] }.sum()
//        if (maxCount<temp) maxCount = temp
//
//        for(i in start until N){
//            ck[i] = true
//            combination(N,K,stackDamage + damageList[i],i+1,ck,damageList,peopleCountList)
//            ck[i] = false
//        }
//    }
//}


/*val cache = HashMap<String,Long>()
fun solution(n: Int): Long {
    var caseCount :Long = 0

    val maxTwoCount = n/2
    for(i in 0..maxTwoCount){
        //oneCount + i칸에서 i개의 2를 배치시키는 경우의 수 (중복 순열) = n개중r개 뽑기(조합)
        val oneCount = n-i*2
        val twoCount = i
        println("combination(${oneCount+twoCount},${twoCount}) = ${combination(oneCount+twoCount,twoCount)}")

        if(cache.get("combination(${oneCount+twoCount},${twoCount})") != null){
            val temp = cache.get("combination(${oneCount+twoCount},${twoCount})")
            caseCount += temp!!
        }else{
            cache.put("combination(${oneCount+twoCount},${twoCount})",combination(oneCount + twoCount , twoCount))
            val temp = cache.get("combination(${oneCount+twoCount},${twoCount})")
            caseCount += temp!!
        }
    }

    return caseCount.also { println("caseCount : $it") }
}

fun combination(n :Int, r :Int) :Long{
    var tempR = r
    if(r == 0) return 1
    if(n == r) return 1
    if(n-r < r) tempR = n-r

    if(cache.get("combination(${n},${tempR})") != null){
        val temp = cache.get("combination(${n},${tempR})")
        return temp!!
    }else{
        cache.put("combination(${n},${tempR})",combination(n-1,tempR-1) % 1234567 + combination(n-1,tempR) % 1234567)
        val temp = cache.get("combination(${n},${tempR})")
        return temp!!
    }
}*/



//네이버 부캠 코테문제 (시이발)
/*fun main() {
    println(solution("부스트캠프").contentToString()) //[30,4]
}

//글자 사이의 거리 매번 구해서 저장하고 그것들의 총합도 저장해야함
//글자가 중복으로 있는 경우는 가까운 글자를 선택( 같은 거리에 있는 경우는 (0.0)을 기준으로 더 가까운 길이값 선택)
//보드에 없는 단어를 찾은 후 다음 거리 측정은 처음부터 측정해야함

val keyboard = arrayOf(
    arrayOf("가","호","저","론","남","드","부","이","프","설"),
    arrayOf("알","크","청","울","키","초","트","을","배","주"),
    arrayOf("개","캠","산","대","단","지","역","구","너","양"),
    arrayOf("라","로","권","교","마","쿼","파","송","차","타"),
    arrayOf("코","불","레","뉴"," ","서","한","산","리","개"),
    arrayOf("터","강","봄","토","캠","상","호","론","운","삼"),
    arrayOf("보","람","이","경","아","두","프","바","트","정"),
    arrayOf("스","웨","어","쿼","일","소","라","가","나","도"),
    arrayOf("판","자","비","우","사","거","왕","태","요","품"),
    arrayOf("안","배","차","캐","민","광","재","봇","북","하"))
fun solution(word: String): IntArray {

    val list = word.toList().map{it.toString()}
    val distanceList = ArrayList<Int>()
    var pivot = Pair(0,0)

    for(a in list){
    loop@for((indexB,b) in keyboard.withIndex()){
            for((indexC,c) in b.withIndex()){
                if(checkContain(a)){//보드에 글자가 있는 경우
                    if(a == c){
                        if(!(pivot.first == 0 && pivot.second == 0)){
                            var tempPivot = pivot
                            //중복값이 있는 경우 현재 피봇과 가장 가까운 곳으로 가야함
                            pivot = getLocation(tempPivot,a) //다음 위치
                            val distance = Math.abs((tempPivot.first - pivot.first)) + Math.abs((tempPivot.second - pivot.second))
                            distanceList.add(distance)
                            break@loop
                        }else {
                            pivot = Pair(indexB,indexC)
                            break@loop
                        }
                    }
                }else{ //보드에 글자가 없는 경우
                    distanceList.add(20)
                    pivot = Pair(0,0)
                }

            }
        }
    }
    return intArrayOf(distanceList.sum(),distanceList.size)
}

//가장 가까운 피봇 구해줌
fun getLocation(nowLocation :Pair<Int,Int>,target :String) :Pair<Int,Int>{
    val tempList = ArrayList<Pair<Int,Int>>()
    for((indexB,b) in keyboard.withIndex()){
        for((indexC,c) in b.withIndex()){
            if(c == target){
                tempList.add(Pair(indexB,indexC))
            }
        }
    }
    val temp = tempList.mapIndexed{ index , k ->
        Pair(Math.abs(k.first -nowLocation.first) +Math.abs(k.second -nowLocation.second),index)
    }.sortedBy{it.first}

    return tempList[temp[0].second].also { println("getLocation 반환값 : $it") }
}
fun checkContain(s :String) :Boolean{
    val tempList = ArrayList<Boolean>()
    for(b in keyboard){
        tempList.add(b.contains(s))
    }

    return tempList.contains(true)
}*/



//class Solution {
//    fun solution(number: String, k: Int): String {
//        //맨앞 k개 짤라서 최대가 아닌값 지움 남은 k : a개
//        //그뒤로 k개씩 문자열 검사 해서 하위 a개 삭제
//        var count = k
//        var answer = ""
//        number.slice(0..k-1).toList().forEach{
//            if(it != number.slice(0..k-1).toList().maxOrNull()) {
//                count -= 1
//            }else answer += it
//        }
//        val secondCheckList = number.slice(k-1..(2*k-1)).toList()
//            for(a in secondCheckList){
//                //남은 count만큼 하위권 애들 지우기
//                //하위권 a개 뽑기
//                val temp = number.slice(k-1..(2*k-1)).toList().sorted()
//                    val deleteList = ArrayList<Char>()
//                for(i in 1..count){
//                    deleteList.add(temp[i])
//                }
//
//                if(deleteList.contains(a)){count -= 1}
//                else answer += a
//
//                if(count == 0) break
//            }
//
//            return answer
//    }
//}

/*fun solution(genres: Array<String>, plays: IntArray): IntArray {

    val genresMap = HashMap<String,ArrayList<Pair<Int,Int>>>()
    genres.toSet().forEach{genresMap.put(it,ArrayList<Pair<Int,Int>>())}

    val list = ArrayList<Pair<String,Int>>()
    genres.forEachIndexed{index , k -> list.add(Pair(k,plays[index])) }
    list.forEachIndexed{index , t -> genresMap.get(t.first)?.add(Pair(t.second,index)) }

    //장르별 합계 높은 순으로
    //장르 내에서 조회수 높은 순으로 상위 2개
    val genresRankedList = genresMap.toList().sortedByDescending{it.second.sum(it.second)}
        .map{it.second.sortedByDescending{it.first}}
    val answer = ArrayList<Int>()
    for(i in 0..1){
        for(t in 0..1){
            answer.add(genresRankedList[i][t])
        }
    }
    return answer.map{it.second}.toIntArray()
}*/

/*fun solution(n: Int, lost: IntArray, reserve: IntArray): Int {

    var count = 0
    val realLost = lost.filter{!reserve.contains(it)}
    val realReserve = reserve.filter{!lost.contains(it)}
    val list = ArrayList<Pair<Int,Int>>()
    val isUsed = MutableList<Boolean>(realReserve.size){false}
    realReserve.forEach{list.add(Pair(it-1,it+1))}

    realLost.forEach{ a ->
        list.forEachIndexed{ index , b ->
            if(b.first == a ||b.second == a) {
                if(isUsed[index] == false){
                    isUsed[index] = true
                    count++
                }
            }
            if(count >= realLost.size) return n - realLost.size + count
        }}
    return n - realLost.size + count
}*/
/*fun solution(bridge_length: Int, weight: Int, truck_weights: IntArray): Int {

    var time = 0
    val truckQ = ArrayDeque<Int>()
    val q = ArrayDeque<Int>()
    truckQ.addAll(truck_weights.toList())
    q.addAll(Array(bridge_length){0})
    println("q.sum() : ${q.sum()} , q.peek() : ${q.peek()} , truckQ.peek() : ${truckQ.peek()} , weight : $weight")
    println("q : $q , truckQ : $truckQ")
    while(q.isNotEmpty()){
        if (truckQ.isNotEmpty()){
            if((q.sum()-q.peek()) + truckQ.peek() <= weight){
                q.add(truckQ.poll())
                time++
            }else{//무게가 넘어가면 기다려야함
                time++
                q.poll()
                q.offer(0)
            }
        }else{
            time++
            q.poll()
            q.offer(0)
        }
        if(q.size > bridge_length) q.poll() //나갈놈들 배출(항상 사이즈 유지함)
        if (q.sum() == 0) break
    }
    return time
}*/

/*fun solution(progresses: IntArray, speeds: IntArray): IntArray {
    val isfinished = MutableList<Boolean>(progresses.size){false}
    val answer = ArrayList<Int>()

    while(!isfinished.fold(true){total , next -> total && next}){ //다 true가 되면 탈출
        //하루 작업 진행
        for(i in 0 until progresses.size){
            progresses[i] += speeds[i]
        }
        //정산
        var count = 0
        for(i in 0 until progresses.size){
            if(progresses[i] >=100 && isfinished[i] == false){
                isfinished[i] = true
                count++
            }
        }
        if(count != 0) answer.add(count)
    }
    return answer.toIntArray()
}*/

/*var answer = ArrayList<Int>()
fun solution(begin: String, target: String, words: Array<String>): Int {
    if(!words.contains(target)) return 0
    else{ temp(begin,target,words.toList(),0) }

    return answer.minOrNull() ?: -1
}

fun temp(nextHint :String,
         target :String,
         words :List<String>,
         count :Int,
         depth :Int = 0){
    if(nextHint == target) answer.add(count)
    else{
        //검색 해야되는 키워드들 추출
        val searchList = ArrayList<Any>()
        for(i in 0 .. nextHint.length-1){//i = pivot Index
            if(i == 0){
                searchList.add(nextHint.slice(1..nextHint.length-1))
            }else if(i == nextHint.length-1){
                searchList.add(nextHint.slice(0..nextHint.length-2))
            }else{
                //두개씩 짝임
                searchList.add(Pair(nextHint.slice(0..i-1),nextHint.slice(i+1..nextHint.length-1)))
            }
        }
        //키워드별 검색
        for(t in 0 until searchList.size){
            if(searchList.contains("cog")) answer.add(count+1)
            else{
                if(t==0 || t == searchList.size-1){
                    val nextHintList = words.filter{it.contains(searchList[t] as String)}
                    if(nextHintList.size != 0){ //검색된게 있으면 검색
                        nextHintList.forEach{
                            temp(it,target,words -it,count+1,depth +1)
                        }
                    }
                }else{
                    val item = searchList[t] as Pair<String,String>
                    val nextHintList2 = words.filter{it.contains(item.first) && it.contains(item.second)}
                    if(nextHintList2.size != 0){ //검색된게 있으면 검색
                        nextHintList2.forEach{
                            temp(it,target,words -it,count+1, depth +1)
                        }
                    }
                }
            }
        }
    }
}*/

/*//##매우매우 소중한 첫 level3 답
lateinit var answer: MutableList<String>
fun solution(tickets: Array<Array<String>>): Array<String> {
    answer = mutableListOf("ICN")
    temp("ICN",tickets.toList(),tickets.size+1)
    return answer.toTypedArray()
}

fun temp(nowCity :String,
         tickets :List<Array<String>>,
         targetSize : Int,
         depth :Int = 1){

    if(answer.size == targetSize) return
    else{
        val canGoList = ArrayList<Pair<Array<String>,Int>>()
        tickets.forEachIndexed{ index , a ->
            if(a[0] == nowCity){
                canGoList.add(Pair(a,index))
            }
        }
        if (canGoList.size == 0) {
            answer = mutableListOf("ICN")
        }
        canGoList.sortBy{it.first[1]} //갈 수 있는 곳이 여러 곳이면 알파벳 순으로 우선순위 지정
        val memoryAnswer = answer.toList()
        var t= 1
        canGoList.forEachIndexed{ index , k ->
            answer = memoryAnswer.toMutableList()
            answer.add(k.first[1])

            val tickets2 = tickets.toMutableList()
            tickets2.removeAt(k.second)
            temp(k.first[1],tickets2 ,targetSize,depth+1)
            if (answer.size == targetSize) return
        }
    }
}*/

/*lateinit var combList : MutableList<List<Int>>

fun solution(numbers: IntArray): String {
    combList = mutableListOf()
    permutation(numbers.toList(),listOf<Int>())

    return combList.map{it.joinToString("").toInt()}.maxOrNull().toString()
}

tailrec fun permutation(elList :List<Int>,result :List<Int>){
    if(result.isNotEmpty()){ combList.add(result) }
    if(elList.isEmpty()) return

    elList.forEachIndexed{ index, item ->
        permutation(elList-item,result+item)
    }
}*/


/*fun solution(numbers: String): Int {
    val elList = numbers.toList()
    val combList = mutableListOf<List<Char>>()
    for(r in 1 .. elList.size){
        combination(combList,elList,r)
    }

    return combList.map{permutation(it)}.flatMap{it}
        .map{it.joinToString("").toInt()}
        .filter{isPrime(it)}.distinct()
        .also { println(it) }
        .count()
}

fun <T> combination(answer :MutableList<List<T>>,
                    el :List<T>,
                    r :Int,
                    start :Int = 0,
                    ck :MutableList<Boolean> = MutableList<Boolean>(el.size){false}){
    if(r==0){
        answer.add(el.filterIndexed{ index,i -> ck[index]})
    }else{
        for (i in start until el.size){
            ck[i] = true
            combination(answer, el, r-1, i+1, ck)
            ck[i] = false
        }
    }
}

fun <T> permutation(el :List<T>,
                    fin :List<T> = listOf<T>(),
                    sub :List<T> = el) :List<List<T>>{
    return if(sub.isEmpty()) listOf(fin)
    else sub.flatMap{permutation(el,fin+it,sub-it)}
}

fun isPrime(a :Int) :Boolean{
    if (a == 1 || a == 0) return false
    for(i in 2..a/2){
        if(a%i == 0) return false
    }
    return true
}*/

/*lateinit var combNumbers : MutableList<Int>
fun solution(numbers: String): Int {
    var answer = 0
    combNumbers = mutableListOf()

    temp(numbers,"")

    combNumbers.also { println(it) }.distinct().forEach {

        if(isPrime(it)){
            answer++
        }
    }
    return answer
}

fun temp(numbers:String,result:String){
    //모든 결과 집어 넣고
    if(!result.isNullOrEmpty()){
        combNumbers.add(result.toInt())
    }
    //더이상 조합할 놈들이 없다면 재귀 종료
    if(numbers.isEmpty()){
        return
    }
    //재귀 돌리기
    numbers.forEachIndexed { index, c ->
        temp((numbers.removeRange(index..index)),result+c)
    }
}
fun isPrime(num:Int):Boolean{

    if(num == 1 || num == 0 ){return false}
    for(i in 2..num/2){
        if(num%i == 0){
            return false
        }
    }
    return true
}*/
//###########################조합 연습 코드######################################
/*fun main(args: Array<String>) {
    var arr = listOf(1, 2, 3, 4)    // 1. Int
    //var arr = "asdf".toList()   // 2. String

    for (i in 1..arr.size) {
        val answer = mutableListOf<List<Int>>()     // 1. Int
        // val answer = mutableListOf<List<Char>>() // 2. String
        println("================$i 개====================")
        combination(answer, arr, Array<Boolean>(arr.size) { false }, 0, i)
        *//*answer.forEach { println(it) }*//*
        println("####################################################최종 answer : $answer")
    }
}

fun <T> combination(answer: MutableList<List<T>>, //조합의 원소를 저장할 이중 리스트
                    el: List<T>, //조합을 구할 원소들의 집합
                    ck: Array<Boolean>, //원소 선택 여부를 확인하기 위한 배열
                    start: Int, //탐색 시작 인덱스
                    target: Int) { //뽑아야 하는 원소 개수
    if(target == 0) {
        println("이제 target이 0이니까 answer배열에 담는다! ck : ${ck.contentToString()}")
        answer.addAll( listOf(el.filterIndexed { index, t -> ck[index] }) ) //더이상 뽑을게 없으면 ck에서 true 체크 된에들 배열에 담아서 answer에 담기
        println("현재 담긴 answer 목록 : $answer")
    } else {
        println("answer: $answer , el: $el , ck: ${ck.contentToString()}, start: $start , target: $target")
        for(i in start until el.size) {
            println("for문 시작 i : $i")
            ck[i] = true
            println("ck체크 : ${ck.contentToString()}")
            combination(answer, el, ck, i + 1, target - 1)
            println("조합 끝 : i : $i")
            ck[i] = false
            println("ck초기화 : ${ck.contentToString()}")

        }
    }
}*/



/*fun solution(n: Int): Long {
    var count :Long = 0

    val fix = 50

    //2칸씩 이동할 수 있는 최대 횟수
    val max2 = (fix/2).toInt()

    //2가 등장하는 횟수 별 분기
    for(i in 0..max2){

        //1이 등장하는 칸수 세기
        val oneCount = (fix-(i*2))

        //2가 등장하는 칸수
        val twoCount= i

        //총 칸 수
        val totalCount = oneCount + twoCount

        //2 배치 가능 경우의 수
        //= 총 칸 수 에서 2를 앉힐 "자리를 뽑는 경우의 수"와 같음
        var caseCount :Long = 0

        if(i!=0){
            caseCount = combination(totalCount,twoCount)
        }

        hashMapOf<String,String>("s" to "s").size

        println("caseCount : $caseCount")
        count = count + caseCount
        println("totalCount: $totalCount, twoCount: $twoCount, oneCount: $oneCount , count: $count")
    }
    return count
}

fun combination(n :Int, r :Int) :Long{
    if (n == r || r == 0){
        return 1
    }else {
        return combination(n-1,r-1) + combination(n-1,r)
    }
}*/

/*fun solution(s: String): Int {

    val countList = ArrayList<Int>()

    for (i in 1..s.length){ //자를 글자 길이
        val tempList = ArrayList<String>()
        for (j in s.indices step i){
            tempList.add(s.slice(j .. if (j+i > s.lastIndex) s.lastIndex else j+i-1))
        }

        var count = 1
        var stringStore = StringBuffer()
        for ((index,s) in tempList.withIndex()){
            if (index!=tempList.lastIndex && (tempList[index] == tempList[index+1])){
                count++
            }else {
                if (count != 1){
                    stringStore.append("$count$s")
                    count = 1
                }else{
                    stringStore.append(s)
                }
            }
        }
        countList.add(stringStore.length)
    }
    return countList.minOf { it }.also { println(it) }
}*/

/*fun solution(N: Int, stages: IntArray): IntArray {
    return (1..N)
        .map { t -> Pair(t,(
                if (stages.count { it>=t } != 0) stages.count { it==t }.toDouble() / stages.count { it>=t }.toDouble() else 0.0 ) ) }
        .sortedWith( compareBy(
            {it.second},
            {-it.first}))
        .reversed().also { println(it) }
        .map { it.first }
        .toIntArray().also { println(it.contentToString()) }
}*/

/*fun solution(lottos: IntArray, win_nums: IntArray): IntArray {
    var answer = intArrayOf(
        checkRank(lottos.count { win_nums.contains(it) || it == 0}),
        checkRank(lottos.count { win_nums.contains(it) }) //맞춘 최저
    )
    return answer
}
fun checkRank(item : Int): Int =
    when(item){
        6 -> 1
        5 -> 2
        4 -> 3
        3 -> 4
        2 -> 5
        else -> 6
    }*/


//fun solution(n: Int): Array<IntArray> {
//
//    var answer = ArrayList<IntArray>()
//    val countStoneList = arrayListOf<Int>(n,0,0)
//    moveStone(countStoneList,answer,n)
//
//    return answer.toTypedArray()
//}
//fun moveStone(countStoneList : ArrayList<Int>
//              ,list: ArrayList<IntArray>
//              ,n : Int) : ArrayList<IntArray>{
//
//    list.add(intArrayOf())
//
//
//    if (countStoneList[3] == n) return list
//    return list
//}

fun solution(board: Array<IntArray>, moves: IntArray): Int {

    //배열 옮겨 담기
    val changedBoard = ArrayList<ArrayList<Int>>()
    for (i in 0 until board[0].size){
        changedBoard.add(ArrayList<Int>())
    }
    for ((i,row) in board.withIndex()){
        for (j in 0 until board[0].size){
            changedBoard[j].add(row[j])
        }
    }//굳이 따로 안만들고 해당 조건에 맞는 곳을 찾아가서
    //stack에 추가하는 방식으로 구현했어도 됐음


    val dollList = changedBoard.map { it.filter { it!=0 }.toMutableList() }.toMutableList()
    val boxList = ArrayList<Int>()
    var count = 0
    moves.forEach { index ->
        boxList.add(dollList[index-1].firstOrNull() ?:0).also { dollList[index-1].removeFirstOrNull() }
        if (boxList.contains(0)) boxList.remove(0) //스택으로 구현하면 깔끔함
        if ((boxList.size>=2) && (boxList.last() == boxList[boxList.size-2])) {
            boxList.removeAt(boxList.size-1)
            boxList.removeAt(boxList.size-1)
            count +=2
        }
    }
    return count
}



//fun solution(word: String): Int {
//
//    val charArray = arrayOf("A","E","I","O","U")
//    val stringList = ArrayList<String>()
//
//    //글자가 1개 일 때
//    stringList.addAll(charArray)
//
//    //글자가 2개 일 때
//    for (a in charArray.indices){
//        for (b in charArray.indices){
//            stringList.add(charArray[a]+charArray[b])
//        }
//    }
//    //글자가 3개 일 때
//    for (a in charArray.indices){
//        for (b in charArray.indices){
//            for (c in charArray.indices)
//            stringList.add(charArray[a]+charArray[b]+charArray[c])
//        }
//    }
//    //글자가 4개 일 때
//    for (a in charArray.indices){
//        for (b in charArray.indices){
//            for (c in charArray.indices){
//                for (d in charArray.indices){
//                    stringList.add(charArray[a]+charArray[b]+charArray[c]+charArray[d])
//                }
//            }
//        }
//    }
//    //글자가 5개 일 때
//    for (a in charArray.indices){
//        for (b in charArray.indices){
//            for (c in charArray.indices){
//                for (d in charArray.indices){
//                    for (e in charArray.indices){
//                        stringList.add(charArray[a]+charArray[b]+charArray[c]+charArray[d]+charArray[e])
//                    }
//                }
//            }
//        }
//    }
//    return stringList.sorted().also { println(it) }.indexOf(word)+1
//}


/*fun solution(n: Int): Int {

    val answer = intArrayOf(0,1).toCollection(ArrayList<Int>())
    for (i in 0..n){
        answer.add((answer[i] + answer[i+1])%1234567)
    }

    return answer[n]
}*/

/*fun solution(new_id: String): String {

    if (isGoodId(new_id)) {
        return new_id
    }else return recommendId(new_id)
}
fun isGoodId(id : String) : Boolean {

    val charList = ArrayList<Char>()
    for (i in 'a'..'z'){
        charList.add(i)
    }
    for (i in '0'..'9'){
        charList.add(i)
    }
    charList.add('-')
    charList.add('_')
    charList.add('.')

    if (id.length !in 3..15) return false
    if (id.filter { !charList.contains(it)}.isNotEmpty()){ return false }
    if (id[0] =='.' || id[id.length-1] =='.') return false
    return true
}
fun recommendId(id: String) : String{

    val charList = ArrayList<Char>()
    for (i in 'a'..'z'){
        charList.add(i)
    }
    for (i in '0'..'9'){
        charList.add(i)
    }
    charList.add('-')
    charList.add('_')
    charList.add('.')

    var temp = id.map { it.lowercaseChar() }
        .filter { charList.contains(it) }.joinToString("")
    while (temp.contains("..")){
        temp = temp.replace("..",".")
    }
    if (temp[0]=='.') temp = temp.slice(1..temp.lastIndex)
    if ((temp.lastOrNull()=='.')) temp = temp.slice(0 until temp.lastIndex)
    if (temp.length==0) temp = "a"
    if (temp.length>=16) temp = temp.slice(0..14).let { if (it.lastOrNull() == '.') it.slice(0 until it.lastIndex) else it}
    if (temp.length<=2) {
        while (temp.length<3){
            temp += temp.lastOrNull()
        }
    }
    return temp
}*/


/*fun solution(citations: IntArray): Int {

    val list = ArrayList<Int>()
    for (h in 0..citations.size){
        var upCount = 0; var downCount =0
        for (j in citations){
            if (j>=h) upCount++
            if (j<=h) downCount++
        }
        if (upCount>=h && downCount<=h) list.add(h)
    }
    return list.maxOrNull() ?: 0
}*/

/*fun solution(n: Int): Int {

    val list = ArrayList<Int>()
    for(i in 1..n){
        if(n%i == 0) list.add(i)
    }
    return list.sum()
}*/

/*fun solution(a: IntArray, b: IntArray): Int {
    return a.mapIndexed { index, i -> i*b[index]}.sum()
}*/

/*fun solution(s: String): Int {

    return s.replace("zero","0")
        .replace("one","1")
        .replace("two","2")
        .replace("three","3")
        .replace("four","4")
        .replace("five","5")
        .replace("six","6")
        .replace("seven","7")
        .replace("eight","8")
        .replace("nine","9")
        .toInt()
}*/


/*fun solution(strings: Array<String>, n: Int): Array<String> {
    strings.sortBy { it }
    strings.sortBy { it[n] }
    return strings
}*/

/*fun solution(s: String): String {
    return String(s.toList().sortedDescending().toCharArray())
}*/


/*fun solution(absolutes: IntArray, signs: BooleanArray): Int {
    return absolutes.mapIndexed { index, i ->if (signs[index] == false) i*(-1) else i}.sum()
}*/


/*fun solution(n: Int): String {

    var answer = StringBuffer()
    for (i in 1..n){
        if (i%2 ==1) answer.append('수') else answer.append('박')
    }
    return answer.toString()
}*/

/*fun solution(s: String): Int {
    return s.toInt()
}*/

/*fun solution(a: Int, b: Int): Long {

    var sum : Long =0
    val min: Long = Math.min(a.toLong(),b.toLong())
    val max: Long = Math.max(a.toLong(),b.toLong())
    for (i in min..max){
        sum +=i
    }
    return sum
}*/


/*fun solution(seoul: Array<String>): String {

        seoul.forEachIndexed { index, s ->
            if (s == "Kim") return "김서방은 ${index}에 있다"
        }

        return "* v *"
    }*/
/*fun solution(seoul: Array<String>): String = "김서방은 ${seoul.indexOf("Kim")}에 있다"*/

/*
* fun solution(arr: IntArray, divisor: Int): IntArray {
        val answer = ArrayList<Int>()
        arr.distinct().forEach {
            if(it%divisor ==0) answer.add(it)
        }

        return if (answer.size != 0) answer.sorted().toIntArray() else IntArray(1,{-1})
    }*/

/*fun solution(s: String): Boolean {
    val intArray = Array(10,{it}).map { it.toString() }

    return s.takeIf { it.length==4 || it.length ==6 }
        ?.toList()?.filterNot { item ->
        intArray.contains(item.toString()) }
        ?.isEmpty() ?: false
}*/


/*fun solution(n: Long): IntArray {
    return n.toString().map { it.toString().toInt() }.reversed().toIntArray()
}*/

/*fun solution(numbers: IntArray): Int {
        return Array(10,{it}).filter { !numbers.contains(it) }.sum()
    }*/

/*fun solution(s: String): String {
        return s.split(" ").map { it.toInt() }.minOf { it }.toString() +
                " " +
                s.split(" ").map { it.toInt() }.maxOf { it }.toString()
    }*/
/*fun solution(s: String): String {
    return s.split(" ").map { it.toInt() }.let { "${it.minOrNull()} ${it.maxOrNull()}" }
}*/

/*fun solution(id_list: Array<String>, //이용자 ID 배열
                 report: Array<String>, // 신고 기록 배열  ["신고자 피신고자", ...]
                 k: Int): IntArray { //정지 기준 신고 횟수

        //report 배열에 담기
        val rArray = ArrayList<List<String>>()
        report.distinct().forEach {
            rArray.add(it.split(' '))
        }

        //사람별 신고 당한 횟수 저장
        val outCountMap = HashMap<String,Int>()
        id_list.forEach { outCountMap.set(it,0) }
        rArray.forEach { lt ->
            outCountMap.set(lt[1] , outCountMap.get(lt[1])?.plus(1)  ?: -100)
        }

        //정지 대상 구분
        val outList = ArrayList<String>()
        outCountMap.forEach {
            if (it.value >= k) outList.add(it.key)
        }

        //사람별 메일 받을 횟수 저장
        val mailCountMap = HashMap<String,Int>()
        id_list.forEach { mailCountMap.set(it,0) }
        rArray.forEach {
            if (outList.contains(it[1]))
                mailCountMap.set(it[0],mailCountMap.get(it[0])?.plus(1) ?: -100)
        }

        //주어진 이름 순서대로 횟수 넣기
        val answer = id_list.map {
            mailCountMap.get(it) ?: -200
        }.toIntArray()

        return answer
    }*/


/*fun solution(left: Int, right: Int): Int {
        var sum = 0

        for (i in left..right){
            var count = 0

            for (t in 1..i){
                if(i%t == 0) count++
            }
            if (count%2 == 0) sum += i else sum -= i
        }

        return sum
    }*/


/*fun solution(n: Int): Int {
    return n.toString(3).reversed().toInt(3)
}*/


/*fun solution(numbers: IntArray): IntArray {
        val answer = ArrayList<Int>()
        numbers.withIndex()
        for (i in numbers.indices){
            for (t in i+1 until  numbers.size)
                answer.add(numbers[i] + numbers[t])
        }
        return answer.distinct().sorted().toIntArray()
    }*/

/*fun solution(a: Int, b: Int): String {
        var sum = 0

        for (i in 1..a){
            when(i){
                2 -> sum += 31
                3 -> sum += 29
                4 -> sum += 31
                5 -> sum += 30
                6 -> sum += 31
                7 -> sum += 30
                8 -> sum += 31
                9 -> sum += 31
                10 -> sum += 30
                11 -> sum += 31
                12 -> sum += 30
            }
        }
        sum += b

        return when(sum%7){
            1 -> "FRI"
            2 -> "SAT"
            3 -> "SUN"
            4 -> "MON"
            5 -> "TUE"
            6 -> "WED"
            0 -> "THU"
            else -> "* v *"
        }
    }*/

/*fun solution(sizes: Array<IntArray>): Int {
        var width =0; var hight =0

        sizes.map { it.sort() }
        for (row in sizes){
            if (width < row[0]) width = row[0]
            if (hight < row[1]) hight = row[1]
        }
        return hight * width
    }*/

/*fun solution(n: Int): Int {

        var x = 1
        while (n%x !=1){
            x++
        }

        return x
    }*/

/*
* fun solution(s: String): String {
        return if((s.length%2) ==0 )
            s[(s.length-1)/2].toString() + s[(s.length-1)/2 + 1].toString()
        else s[(s.length-1)/2].toString()
    }*/

/*
* fun solution(n: Int): Int {
        return n.toString().toCharArray().map { it.toString().toInt() }.sum()
    }*/

/*//price : 한 번 가격 , money : 잔고, count : N번
//return : N번 타면 얼마나 모자라는지
fun solution(price: Int, money: Int, count: Int): Long {
    var sum : Long = 0

    for (i in 1..count){
        sum += price * i
        println("sum : $sum")
    }

    return if (money > sum) 0 else Math.abs(money - sum)
}*/



/*fun solution(n: Long): Long {

        var answer = StringBuffer()
        n.toString()
            .toList()
            .sortedDescending()
            .forEach {
                answer.append(it)
            }

        return answer.toString().toLong()
    }*/

/*fun solution(n: Long): Long = String(n.toString()
    .toCharArray()
    .sortedArrayDescending())
    .toLong()*/

/*fun solution(n: Long): Long {
        val x = Math.sqrt(n.toDouble())
        val xL = x.toLong()

        return if((x-xL) == 0.0) Math.pow((x+1),2.0).toLong()
        else -1L
    }*/

/*fun solution(arr: IntArray): IntArray {

//        val tempArray = arr.toCollection(ArrayList<Int>())
//        if (arr.size > 1) {
//            tempArray.remove(tempArray.minOrNull())
//            return tempArray.toIntArray()
//        }
//        else return IntArray(1,{-1})

        if(arr.size > 1) return arr.filter { it != arr.minOrNull() }.toIntArray()
        else return IntArray(1,{-1})

    }*/

/*fun solution(num: Int): String =
        if(num%2 == 0)  "Even" else "Odd"*/

/*fun solution(n: Int, m: Int): IntArray {
    val nArray = ArrayList<Int>()
    val mArray = ArrayList<Int>()
    var answer = ArrayList<Int>()

    //최대 공약수 구하기 (유클리드 호제법)
    for(i in 1..n){
        if (n%i == 0) nArray.add(i) //n의 약수 구하기
    }
    for(i in 1..m){
        if (m%i == 0) mArray.add(i) //m의 약수 구하기
    }

    val commonArray = mArray.filter { nArray.contains(it) }

    answer.add(commonArray.maxOf { it }) //최대공약수
    nArray.clear(); mArray.clear()

    //최소 공배수 구하기
    var t = 0 ; var k = 0
    while (t != 1){
        for(i in 1+ k*10..(1+k)*10){
            nArray.add(n*i) //n의 배수 구하기
        }
        for(i in 1+ k*10..(1+k)*10){
            mArray.add(m*i) //m의 배수 구하기
        }
        nArray.retainAll(mArray)
        if(nArray.size != 0) {
            answer.add(nArray[0])
            t = 1
        }
        k++
    }

    return answer.toIntArray()
}*/

/*temp = num.takeIf { num % 2 == 0 }?.div(2)!!
            temp = num.takeIf { num % 2 == 1 }?.times(3)?.plus(1)!!*/

/*fun solution(num: Int): Int {
    var temp = num.toLong()
    var count = 0

    while (temp != 1L ){
        if (temp % 2 == 0L){
            temp = temp.div(2)
            count++.also { if (count >= 500) return -1 }
            continue
        }
        if (temp % 2 == 1L){
            temp = temp.times(3).plus(1)
            count++.also { if (count >= 500) return -1 }
            continue
        }

    }
    return count
}*/


/*
fun solution(arr: IntArray): Double {
        return arr.average()
    }
* */

/*
fun solution(phone_number: String): String {
        return "${"".padStart(phone_number.length - 4, '*')}${phone_number.takeLast(4)}"
    }
* */
/*
fun solution(phone_number: String): String {

        val s= StringBuilder()
        phone_number.reversed().forEachIndexed { index, c ->
            if (index>3){
                s.append("*")
            }else{
                s.append(c)
            }
        }
        return s.toString().reversed()
    }*/

//    fun solution(x: Int): Boolean {
//        return x % x.toString().fold(0){acc, c -> acc + c.toInt() - 48} == 0
//    }


//    fun solution(x: Int): Boolean {
//
//        if (x >= 1000){
//            val a = (x * 0.001).toInt()         //1000의 자리
//            val b = (x * 0.01).toInt() % 10     //100의 자리
//            val c = (x * 0.1).toInt() % 10      //10의 자리
//            val d = x % 10                      //1의 자리
//
//            return x % (a+b+c+d) == 0
//        }else if (x >= 100){
//            val b = (x * 0.01).toInt()          //100의 자리
//            val c = (x * 0.1).toInt() % 10      //10의 자리
//            val d = x % 10                      //1의 자리
//
//            return x % (b+c+d) == 0
//        }else if (x >= 10){
//            val c = (x * 0.1).toInt()           //10의 자리
//            val d = x % 10                      //1의 자리
//
//            return x % (c+d) == 0
//        }else{
//            return true
//        }
//
//        return false
//    }

/*
    fun solution(x: Int, n: Int): LongArray {

        //var answer = LongArray(n) { i  -> (x + x*i).toLong() } 사실 i가 int범위를 벗어날 일이 없는데 오류뜸

        //var answer = LongArray(n) { (x + x*it).toLong() } //사실 i가 int범위를 벗어날 일이 없는데 오류뜸

        var answer = LongArray(n) { x.toLong() * (it + 1) }
//        val answer = ArrayList<Long>()
//        for (i in 0L until n){
//            answer.add(x + x*i)
//        }
//
//        return answer.toLongArray()

        return answer
    }*/



//fun main(args: Array<String>) {
//    val (a, b) = readLine()!!.split(' ').map(String::toInt)
//    for (i in 0 until b){
//        for (t in 0 until a){
//            print("*")
//        }
//        println().takeIf { i != b-1 }
//    }
//}