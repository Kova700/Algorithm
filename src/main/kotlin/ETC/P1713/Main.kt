package ETC.P1713

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P1713/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val N = br.readLine().toInt() // (1~20) 사진틀의 개수
    val K = br.readLine().toInt() // (1~1000) 총 추천의 횟수
    val inputs = br.readLine().split(" ").map { it.toInt() } // 원소 : 1~100번 까지

    val isIn = Array(101){false}
    val picFrame = ArrayList<Student>()

    var time = 0
    for (input in inputs) {
        //이미 사진틀에 있다면
        if(isIn[input] == true){
            //추천수 올려줘야함
            picFrame.forEach { if(it.num == input) it.count++ }
        //사진틀에 없다면
        }else{
            //사진틀이 비어있다면
            if (picFrame.size < N){
                picFrame.add(Student(input,1,time++))
            }//사진틀이 꽉 찼다면
            else{
                //삭제 우선순위 : 추천수 낮은 순 > 시간이 오래된 순
                picFrame.sortWith(compareBy<Student?> { it?.count }.thenBy { it?.time })
                val out = picFrame.removeFirst()
                isIn[out.num] = false
                picFrame.add(Student(input,1,time++))
            }
            isIn[input] = true
        }
    }

    println(picFrame.map { it.num }.sorted().joinToString(" "))
}
class Student (val num :Int, 
               var count :Int,
               var time :Int)