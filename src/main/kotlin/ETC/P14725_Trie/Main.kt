package ETC.P14725_Trie

import java.io.*

private var N = 0 //먹이의 정보 개수 (1 ≤ N ≤ 1000)
private val trieRootNode = Node()
private val bw = BufferedWriter(OutputStreamWriter(System.out))
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P14725_Trie/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))

    N = br.readLine().toInt()
    repeat(N) {
        br.readLine().split(" ").drop(1).also { initTrie(it) }
    }

    dfs(trieRootNode)
    bw.flush()
    bw.close()
}

private fun dfs(currentNode: Node, depth: Int = 0) {
    val childMap = currentNode.childMap.toSortedMap()
    if (childMap.isEmpty()) return

    for (childNode in childMap) {
        bw.write("${getDepthText(depth)}${childNode.key}\n")
        dfs(childNode.value, depth + 1)
    }
}

private fun getDepthText(depth: Int): String {
    val returnDepthText = StringBuilder()
    for (i in 1..depth) {
        returnDepthText.append("--")
    }
    return returnDepthText.toString()
}


private fun initTrie(dataList: List<String>) {
    var currentNode = trieRootNode
    for (data in dataList) {
        if (currentNode.childMap[data] == null) {
            currentNode.childMap[data] = Node()
        }
        currentNode = currentNode.childMap[data]!!
    }
}

private data class Node(
    val childMap: HashMap<String, Node> = hashMapOf(),
    var isLast: Boolean = false
)