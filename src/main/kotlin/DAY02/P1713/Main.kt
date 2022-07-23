package DAY02.P1713

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

class Picture (var recommendCount :Int,
               var time :Int,
               var studentNum :Int)

fun main(args: Array<String>) {
    System.setIn(FileInputStream("src/main/kotlin/DAY02/P1713/input"))

    val br = BufferedReader(InputStreamReader(System.`in`))
    val frameCount = br.readLine().toInt()
    val totalRecommendCount = br.readLine().toInt()
    val recommendList = br.readLine().split(" ").map { it.toInt() }

    val isIn = HashMap<String,Boolean>()
    var frames = ArrayList<Picture>()

    for ((index,num) in recommendList.withIndex()){
        //1.사진이 사진틀에 없다면
        if(isIn.get("$num") == null || isIn.get("$num") == false){
            //2. 사진틀이 꽉찼다면 => 추천수가 가장 낮은 사진 삭제하고 그 자리에 새로운 사진 추가
            //추천 수가 같다면 오래된 사진 삭제 && 삭제된 사진은 추천수 0으로 초기화
            if (frames.size == frameCount){
                frames.sortWith(compareBy<Picture>{ it.recommendCount }.thenBy { it.time })
                isIn.set("${frames[0].studentNum}",false)
                frames.removeFirst()
                frames.add(Picture(1,index,num))
            }else if(frames.size < frameCount){
                //사진틀이 꽉차지 않았다면 ==> 사진틀에 사진 추가
                frames.add(Picture(1,0,num))
            }
            isIn.put("$num",true)
        //1.이미 사진틀에 사진이 있다면 추천수 ++
        }else{
            for (pic in frames){
                if(pic.studentNum == num){
                    pic.recommendCount++
                    break
                }
            }
        }
    }
    println(frames.map { it.studentNum }.sorted().joinToString(" "))
}