package ETC.P3425

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.Stack

lateinit var programsList :ArrayList<ArrayList<String>>
lateinit var inputsList :ArrayList<Array<Long>>
lateinit var stack :Stack<Long>
fun main() {
    System.setIn(FileInputStream("src/main/kotlin/DFS_BFS/P3425/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    //인풋 받기
    programsList = ArrayList<ArrayList<String>>()
    inputsList = ArrayList<Array<Long>>()
    while (true){
        val temp = br.readLine()
        if (temp == "QUIT") break
        else{
            //program의 한 사이클을 담을 List
            val programs = ArrayList<String>()
            if(temp != "END") {
                programs.add(temp)
                while (true){
                    val program = br.readLine()
                    if (program == "END") break
                    else programs.add(program)
                }
            }
            programsList.add(programs)
            val N = br.readLine().toInt() //(1 ~ 1만)
            val inputs = Array(N){br.readLine().toLong()} // (0~10억)
            inputsList.add(inputs)
            br.readLine()
        }
    }
    
    //인풋으로 프로그램 실행
    stack = Stack<Long>()
    var errorFlag = false
    inputsList.forEachIndexed{ index, inputs ->
        for (input in inputs) {
            stack.add(input)
            //프로그램 하나씩 해석해서 작동
            for (program in programsList[index]) {
                when(program){
                    "POP" -> {
                        if(pop() != 0) {
                            errorFlag = true
                            break
                        }
                    }
                    "INV" -> {
                        if(inv() != 0) {
                            errorFlag = true
                            break
                        }
                    }
                    "DUP" -> {
                        if(dup() != 0) {
                            errorFlag = true
                            break
                        }
                    }
                    "SWP" -> {
                        if(swp() != 0){
                            errorFlag = true
                            break
                        }
                    }
                    "ADD" -> {
                        if(add() != 0){
                            errorFlag = true
                            break
                        }
                    }
                    "SUB" -> {
                        if(sub() != 0){
                            errorFlag = true
                            break
                        }
                    }
                    "MUL" -> {
                        if(mul() != 0){
                            errorFlag = true
                            break
                        }
                    }
                    "DIV" -> {
                        if (div() != 0){
                            errorFlag = true
                            break
                        }
                    }
                    "MOD" -> {
                        if (mod() != 0){
                            errorFlag = true
                            break
                        }
                    }
                    else -> {
                        val temp = program.split(" ")
                        num(temp[1].toLong())
                    }
                }
            }
            var temp = 0L
            if (stack.size != 1){
                errorFlag = true
            }else{
                temp = stack.pop()
                if (Math.abs(temp) > 1000000000L){
                    errorFlag = true
                }
            }
            if(errorFlag == false){
                bw.write("${temp}\n")
            }else{
                bw.write("ERROR\n")
            }
            errorFlag = false
        }
        stack = Stack<Long>()
        bw.write("\n")
    }
    bw.flush()
    bw.close()
}

fun num(input: Long){
    stack.add(input)
}
fun pop() :Int{
    if (stack.isEmpty()) return -1
    else stack.pop()
    return 0
}
fun inv() :Int{
    if (stack.isEmpty()) return -1
    else stack.add(-stack.pop())
    return 0
}
fun dup() :Int{
    if (stack.isEmpty()) return -1
    else stack.add(stack.peek())
    return 0
}
fun swp() :Int{
    if (stack.size < 2) return -1
    else{
        val first = stack.pop()
        val second = stack.pop()
        stack.add(first)
        stack.add(second)
    }
    return 0
}
fun add() :Int{
    if (stack.size < 2) return -1
    else{
        val first = stack.pop()
        val second = stack.pop()
        stack.add(first + second)
    }
    return 0
}
fun sub() :Int{
    if (stack.size < 2) return -1
    else{
        val first = stack.pop()
        val second = stack.pop()
        stack.add(second - first)
    }
    return 0
}
fun mul() :Int{
    if (stack.size < 2) return -1
    else{
        val first = stack.pop()
        val second = stack.pop()
        stack.add(first * second)
    }
    return 0
}
fun div() :Int{
    if (stack.size < 2) return -1
    else{
        // 0으로 나눴을 때 (DIV, MOD) 에러,
        val first = stack.pop()
        val second = stack.pop()
        if (first != 0L){
            stack.add(second / first)
            return 0
        }else return -1
    }
}
fun mod() :Int{
    if (stack.size < 2) return -1
    else{
        // 0으로 나눴을 때 (DIV, MOD) 에러,
        val first = stack.pop()
        val second = stack.pop()
        if (first != 0L){
            stack.add(second % first)
            return 0
        }else return -1
    }
}