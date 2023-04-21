package ETC.P5052_Trie

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

private lateinit var trie: Array<Node>
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P5052_Trie/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))

    repeat(br.readLine().toInt()) {
        val n = br.readLine().toInt() // 전화번호의 수 (1 ≤ n ≤ 10000)
        val numList = Array(n) { "" }
        repeat(n) { i ->
            numList[i] = br.readLine()
        }
        initTrie(numList)
    }
}

private fun initTrie(numList: Array<String>) {
    trie = arrayOf(Node())
    var currentNode: Node = trie[0]

    for (num in numList) {
        for (i in num.indices) {
            val cNum = num[i].digitToInt()
            //해당 숫자가 자식노드에 있다면
            if (currentNode.child[cNum] != null) {

                //마지막 문자인데 해당 문자가 자식노드를 가지고 있다면 종료
                if (i == num.lastIndex && currentNode.child.filterNotNull().isNotEmpty()){
                    println("NO")
                    return
                }

                //만약 자식이 마지막 노드로 체크가 되어있다면 종료
                if (currentNode.child[cNum]?.isLast == true) {
                    println("NO")
                    return
                }

                //해당 자식으로 들어간다.
                currentNode = currentNode.child[cNum]!!
                continue
            }
            //해당 숫자가 현재 노드에 자식으로 없다면
            //해당 자식 노드를 만든다.
            currentNode.child[cNum] = Node()
            //해당 자식으로 들어간다.
            currentNode = currentNode.child[cNum]!!
        }
        //단어가 완성되면 현재 노드에 isLast를 체크 해준다.
        currentNode.isLast = true
        //현재노드 위치 되돌리기
        currentNode = trie[0]
    }
    println("YES")
}

private data class Node(
    val child: Array<Node?> = Array(10) { null },
    var isLast: Boolean = false
)