package DAY04.P9202_Trie

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

lateinit var trie :Array<Node>
lateinit var words :Array<String>
lateinit var boards :ArrayList<Array<String>>
lateinit var board :Array<String>
val MX = arrayOf(-1,1,0,0,-1,1,-1,1) //좌 ,우 ,위 ,아래, 좌위, 우위, 좌아래, 우아래
val MY = arrayOf(0,0,1,-1,1,1,-1,-1) //좌 ,우 ,위 ,아래, 좌위, 우위, 좌아래, 우아래

var isVisited = Array<Array<Boolean>>(4){Array(4){false}}
var point = 0
var longestWord = ""
var foundWordsCount = 0
fun main() {
    System.setIn(FileInputStream("src/main/kotlin/DAY04/P9202_Trie/input"))
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    val w = br.readLine().toInt() //(1~30만)
    words = Array<String>(w){br.readLine()}
    br.readLine()
    val b = br.readLine().toInt()
    boards = ArrayList<Array<String>>()
    repeat(b){
        boards.add(Array<String>(4){br.readLine()})
        br.readLine()
    }
    trie = arrayOf<Node>(Node())
    initTrie()

    //Boggle보드 읽고 DFS (S.P = 모든 칸 (16개))
    for (t in boards) {
        board = t
        for (row in board.indices) {
            for (col in board[row].indices){
                //트라이의 루트 노드에 없는 애들은 생략
                val currentNode = trie[0].child[board[row][col] - 'A']
                if(currentNode != null){
                    //dfs 호출
                    dfs(row,col,currentNode,"" + board[row][col])
                }else continue
            }
        }
        //얻을 수 있는 최대점수 , 가장 긴 단어, 찾은 단어의 수
        bw.write("$point $longestWord $foundWordsCount\n")
        point = 0
        longestWord = ""
        foundWordsCount = 0
        clearHit(trie[0])
    }
    bw.flush()
    bw.close()
}

data class Node(
    val child :Array<Node?> = Array(26){null},
    var isWord :Boolean = false,
    var isHit :Boolean = false
)

fun initTrie(){
    var currentNode = trie[0]
    //word읽고 트라이 만들기
    for (word in words){
        word.forEach { c ->
            //이미 해당 단어가 현재 트라이 노드에 자식으로 있다면
            if (currentNode.child[c - 'A'] != null){
                //해당 자식으로 들어간다.
                currentNode = currentNode.child[c - 'A']!!
            //해당 단어가 현재 트라이 노드에 자식으로 없다면
            }else{
                //해당 자식 노드를 만든다.
                currentNode.child[c - 'A'] = Node()
                //해당 자식으로 들어간다.
                currentNode = currentNode.child[c - 'A']!!
            }
        }
        //단어가 완성되었다면 현재 트라이 노드에 isWord를 체크해준다.
        currentNode.isWord = true
        //현재노드 위치 되돌리기
        currentNode = trie[0]
    }
}

fun dfs(row :Int ,col :Int,
        currentNode :Node,
        stack :String){
    //체크인
    isVisited[row][col] = true
    //목적지인가?
    if(currentNode.isWord == true){
        //처음 찾는 단어인가?
        if(currentNode.isHit == false){
            //찾은 단어 카운트 + 1
            foundWordsCount++
            currentNode.isHit = true
            //점수 채점
            point += getPoint(stack)
            //가장 긴 단어 갱신
            if (stack.length > longestWord.length){
                longestWord = stack
            //길이가 같다면 알파벳 앞인 단어로 갱신
            }else if(stack.length == longestWord.length){
                longestWord = if (stack < longestWord) stack else longestWord
            }
        }
    }
    //연결된 곳 (목적지를 찾아도 탐색을 이어가야함)
    for (i in 0..7){
        //갈 수 있는가? -> 맵 내부 / visited /  trie 유무
        val nextRow = row + MY[i]; val nextCol = col + MX[i]
        if((nextRow in 0..3) && (nextCol in 0..3)){
            val nextNode = currentNode.child[board[nextRow][nextCol] - 'A']
            if((isVisited[nextRow][nextCol] == false) && (nextNode != null) ){
                dfs(nextRow, nextCol, nextNode, stack + board[nextRow][nextCol])
            }
        }
    }
    //체크아웃
    isVisited[row][col] = false
}

fun getPoint(word :String) :Int{
    return when(word.length){
        1,2 -> 0
        3,4 -> 1
        5 -> 2
        6 -> 3
        7 -> 5
        8 -> 11
        else -> -1
    }
}
fun clearHit(currentNode :Node){
    for(i in 0 until 26){
        currentNode.isHit = false
        val nextNode = currentNode.child[i]
        //자식 노드가 있으면 내려감
        if (nextNode != null){
            clearHit(nextNode)
        }
    }
}