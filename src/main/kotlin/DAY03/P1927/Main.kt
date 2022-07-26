package DAY03.P1927

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

fun main() {
    System.setIn(FileInputStream("src/main/kotlin/DAY03/P1927/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val N = br.readLine().toInt() //(1~ 10만)
    val inputs = Array(N){br.readLine().toInt()}

    val heap = ArrayList<Int>()
    heap.add(-1) //index 0은 사용하지 않음으로
    
    for (input in inputs){
        //println("$heap , input : $input")
        //x가 0이라면 답 출력
        if (input == 0){
            //만약 배열이 비어있는데 가장 작은 값을 출력하라고 한다면 0을 출력
            if(heap.size == 1){
                println(0)
            }else{
                //배열에서 가장 작은 값을 출력하고 그 값을 배열에서 제거한다.
                println(pop(heap))
            }
        }else{
        //x가 자연수라면 배열에 x를 추가하고
        //x는 int범위까지 들어간다. (음수는 들어가지 않음)
            insert(heap,input)
        }
    }
}

fun pop(heap :ArrayList<Int>) :Int{
    var root = 1
    val returnValue = heap[root]
    heap[root] = heap.last() //정권 교체

    //루트와 자식노드와 비교하면서 어느 자식이 더 작은지도 확인하고 자리 교체
    //아래 노드보다 현재 노드가 값이 더 작을 때까지 반복
    while (true){
        var childLPointer = root*2
        var childRPointer = root*2 + 1
        if(childLPointer >= heap.size) childLPointer  = root
        if(childRPointer >= heap.size) childRPointer = root
        //루트가 제일 작음
        if(heap[root] <= heap[childLPointer] && heap[root] <= heap[childRPointer]) break
        //아래노드가 루트보다 더 작음(힙 재구성)
        else{
            //왼쪽 자식노드가 더 작거나 둘 다 값이 같다면 (왼쪽 노드랑 바꿈)
            if(heap[childLPointer] <= heap[childRPointer]){
                val temp = heap[root]
                heap[root] = heap[childLPointer]
                heap[childLPointer] = temp
                root = root * 2 //아래로 내려가야하니까 루트 갱신
            //오른쪽 자식노드가 더 작다면
            }else{
                val temp = heap[root]
                heap[root] = heap[childRPointer]
                heap[childRPointer] = temp
                root = root * 2 +1 //아래로 내려가야하니까 루트 갱신
            }
        }
    }
    heap.removeLast()
    return returnValue
}

// insert = 제일 끝에 넣고 루트 까지 올라가면서 부모랑 힙 조건 비교 해야함
fun insert(heap :ArrayList<Int>, value :Int){
    heap.add(value)
    var currentPointer = heap.lastIndex
    while (true){
        val parentsPointer = currentPointer/2
        if (parentsPointer == 0) break
        //부모노드가 현재 노드보다 작음
        if (heap[currentPointer] >= heap[parentsPointer]) break
        //부모노드가 현재 노드보다 큼
        else{
            val temp = heap[parentsPointer]
            heap[parentsPointer] = heap[currentPointer]
            heap[currentPointer] = temp
            currentPointer /= 2 //위로 올라가야하니까 갱신
        }
    }
}