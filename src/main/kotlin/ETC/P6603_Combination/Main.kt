package ETC.P6603_Combination

import java.io.*

private val bw = BufferedWriter(OutputStreamWriter(System.out))
private val dataSetList :MutableList<List<Int>> = mutableListOf()
//combination Case 출력 문제
private fun main(){
    System.setIn(FileInputStream("src/main/kotlin/ETC/P6603_Combination/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))

    while (true){
        val line = br.readLine().split(" ").map { it.toInt() }
        if(line[0] == 0) break
        dataSetList.add(line.slice(1..line.lastIndex)) //(6 < size < 13)
    }

    for (row in dataSetList.indices){
        combination(row,0)
        bw.write("\n")
    }

    bw.flush()
    bw.close()
}

private fun combination(
    row :Int,
    nowIndex :Int,
    list :List<Int> = listOf()
){
    //listSize = 6이면 탈출
    if (list.size == 6){
        bw.write(list.joinToString(" ") + "\n")
        return
    }
    for (i in nowIndex..dataSetList[row].lastIndex){
        combination(row, i+1, list + dataSetList[row][i])
    }
}