package ETC.P5373_Super_Hard_Implement

import java.io.FileInputStream

private var T = 0 //테스트케이스 개수 (1 ≤ T ≤ 100)
private var N = 0 //큐브를 돌린 횟수 (1 ≤ N ≤ 1000)
private val cube = Array(6) { Array(3) { CharArray(3) } } //6 x 3 x 3 size로 각면의 큐브색 표현
private val color = arrayOf('w', 'y', 'r', 'o', 'g', 'b')
private fun main() {
    System.setIn(FileInputStream("src/main/kotlin/ETC/P5373_Super_Hard_Implement/input"))
    val br = System.`in`.bufferedReader()
    T = br.readLine().toInt()
    val sb = StringBuilder()
    repeat(T) {
        initCube()
        N = br.readLine().toInt()
        // 첫 번째 문자는 돌린 면이다.
        // U: 윗 면, D: 아랫 면, F: 앞 면, B: 뒷 면, L: 왼쪽 면, R: 오른쪽 면이다.
        // 두 번째 문자는 돌린 방향이다.
        // +인 경우에는 시계 방향 (그 면을 바라봤을 때가 기준), -인 경우에는 반시계 방향이다.
        br.readLine().split(" ").forEach { rotate(getViewIndex(it[0]), it[1]) }
        cube[0].forEach { sb.append("${it.joinToString("")}\n") }
    }
    println(sb)
}

private fun initCube() {
    //위[0],아래[1],앞[2],뒤[3],왼[4],오른[5] 순
    //흰, 노, 빨, 오 ,초 ,파
    for (i in 0 until 6) {
        //j = 0 면의 윗부분
        for (j in 0 until 3) {
            for (k in 0 until 3) {
                cube[i][j][k] = color[i]
            }
        }
    }
}

private fun getViewIndex(viewType: Char) =
    when (viewType) {
        'U' -> 0
        'D' -> 1
        'F' -> 2
        'B' -> 3
        'L' -> 4
        'R' -> 5
        else -> throw Exception("Oh my god!!!")
    }

private fun rotate(viewIndex: Int, direction: Char) {
    //돌아가는 면 회전
    rotateLookingView(viewIndex, direction)
    //돌아가는 면 side에 붙어있는 면들 회전
    rotateSideView(viewIndex, direction)
}

private fun rotateLookingView(viewIndex: Int, direction: Char) {
    val tempNewSide = Array(3) { CharArray(3) }

    when (direction) {
        '+' -> {
            var y = 0
            var x = 2
            for (i in 0 until 3) {
                for (j in 0 until 3) {
                    tempNewSide[y][x] = cube[viewIndex][i][j]
                    y++
                    if (y > 2) {
                        y = 0
                        x--
                    }
                }
            }
        }

        '-' -> {
            var y = 2
            var x = 0
            for (i in 0 until 3) {
                for (j in 0 until 3) {
                    tempNewSide[y][x] = cube[viewIndex][i][j]
                    y--
                    if (y < 0) {
                        y = 2
                        x++
                    }
                }
            }
        }
    }
    cube[viewIndex] = tempNewSide
}

//돌리다 보면 각면의 인덱스 시작점이 헷갈릴텐데 미리 정해두자.
//앞면, 왼쪽면, 오른쪽면, 뒷면은 앞면과 같이 위에서부터 아래로 인덱스를 똑같이 잡음
//윗면은 앞면과 먼쪽이 1층 , 왼쪽부터 1번
//아랫면은 앞면과 가까운쪽이 1층, 왼쪽부터 1번

private fun rotateSideView(viewIndex: Int, direction: Char) {
    // U(0): 윗 면,
    // D(1): 아랫 면,
    // F(2): 앞 면,
    // B(3): 뒷 면,
    // L(4): 왼쪽 면,
    // R(5): 오른쪽 면
    //반 시계방향은 시계방향 3번돌린 것과 같음을 이용
    //( 단순 시뮬레이션 문제는 보통 시간 초과의 문제는 걱정할 필요는 없기 때문에
    // 최대한! 구현이 간편한 쪽으로 방향을 잡는 것이 좋다. )

    when (viewIndex) {
        0 -> {
            //윗 면 돌리면 아랫 면을 제외한 면들의 0번 행 돌림
            for (i in 0 until 3) {
                val temp = cube[2][0].copyOf()
                //오른쪽 면 -> 앞면
                cube[2][0] = cube[5][0]
                //뒷 면 -> 오른쪽 면
                cube[5][0] = cube[3][0]
                //왼쪽 면 -> 뒷면
                cube[3][0] = cube[4][0]
                //앞면 -> 왼쪽 면
                cube[4][0] = temp
                if (direction == '+') break
            }
        }

        1 -> {
            //아랫 면 돌리면 윗 면을 제외한 면들의 2번 행 돌림
            for (i in 0 until 3) {
                val temp = cube[2][2].copyOf()
                //왼쪽 면 -> 앞면
                cube[2][2] = cube[4][2]
                //뒷 면 -> 왼쪽 면
                cube[4][2] = cube[3][2]
                //오른쪽 면 -> 뒷 면
                cube[3][2] = cube[5][2]
                //앞 면 -> 오른 쪽면
                cube[5][2] = temp
                if (direction == '+') break
            }
        }

        2 -> {
            //앞 면 돌리면 뒷 면을 제외한 면들 중
            //  윗 면(0) : 2번 행 돌림 == cube[0][2][j]
            //  왼쪽 면(4) : 2번 열 돌림 == cube[4][j][2]
            //  오른쪽 면(5) : 0번 열 돌림 == cube[5][j][0]
            //  아랫 면(1) : 0번 행 돌림 == cube[1][0][j]
            for (i in 0 until 3) {
                val temp = cube[0][2].copyOf()
                //왼쪽 면 -> 윗면
                for (j in 0 until 3) {
                    cube[0][2][j] = cube[4][2 - j][2]
                }
                //아랫 면 -> 왼쪽 면
                for (j in 0 until 3) {
                    cube[4][j][2] = cube[1][0][j]
                }
                //오른쪽 면 -> 아랫 면
                for (j in 0 until 3) {
                    cube[1][0][j] = cube[5][2 - j][0]
                }
                //윗면 -> 오른쪽 면
                for (j in 0 until 3) {
                    cube[5][j][0] = temp[j]
                }
                if (direction == '+') break
            }
        }

        3 -> {
            //뒷 면(3) 돌리면 앞 면을 제외한 면들 중
            //  윗 면(0) : 0번 행 돌림 == cube[0][0][j]
            //  왼쪽 면(4) : 0번 열 돌림 == cube[4][j][0]
            //  오른쪽 면(5) : 2번 열 돌림 == cube[5][j][2]
            //  아랫 면(1) : 2번 행 돌림 == cube[1][2][j]
            for (i in 0 until 3) {
                val temp = cube[0][0].copyOf()
                //오른쪽 면 -> 윗면 (그 면을 봤을 때 기준)
                for (j in 0 until 3) {
                    cube[0][0][j] = cube[5][j][2]
                }
                for (j in 0 until 3) {
                    //아랫 면 -> 오른쪽 면
                    cube[5][j][2] = cube[1][2][2 - j]
                }
                for (j in 0 until 3) {
                    //왼쪽 면 -> 아랫 면
                    cube[1][2][j] = cube[4][j][0]
                }
                for (j in 0 until 3) {
                    //윗 면 -> 왼쪽 면
                    cube[4][j][0] = temp[2 - j]
                }
                if (direction == '+') break
            }
        }

        4 -> {
            //왼쪽 면(4) 돌리면 오른쪽 면을 제외한 면들 중
            //  윗 면(0) : 0번 열 돌림 == cube[0][j][0]
            //  앞 면(2) : 0번 열 돌림 == cube[2][j][0]
            //  아랫 면(1) : 0번 열 돌림 == cube[1][j][0]
            //  뒷 면(3) : 2번 열 돌림 == cube[3][j][2]
            for (i in 0 until 3) {
                val temp = CharArray(3)
                for (j in 0 until 3) {
                    temp[j] = cube[0][j][0]
                }

                //뒷면 -> 윗면
                for (j in 0 until 3) {
                    cube[0][j][0] = cube[3][2 - j][2]
                }
                //아랫면 -> 뒷면
                for (j in 0 until 3) {
                    cube[3][j][2] = cube[1][2 - j][0]
                }
                //앞면 -> 아랫 면
                for (j in 0 until 3) {
                    cube[1][j][0] = cube[2][j][0]
                }
                //윗면 -> 앞면
                for (j in 0 until 3) {
                    cube[2][j][0] = temp[j]
                }
                if (direction == '+') break
            }
        }

        5 -> {
            //오른쪽 면(5) 돌리면 왼쪽 면을 제외한 면들 중
            //  윗 면(0) : 2번 열 돌림 == cube[0][j][2]
            //  앞 면(2) : 2번 열 돌림 == cube[2][j][2]
            //  아랫 면(1) : 2번 열 돌림 == cube[1][j][2]
            //  뒷 면(3) : 1번 열 돌림 == cube[3][j][1]
            for (i in 0 until 3) {
                val temp = CharArray(3)
                for (j in 0 until 3) {
                    temp[j] = cube[0][j][2]
                }

                //앞면 -> 윗면
                for (j in 0 until 3) {
                    cube[0][j][2] = cube[2][j][2]
                }
                //아랫면 -> 앞면
                for (j in 0 until 3) {
                    cube[2][j][2] = cube[1][j][2]
                }
                //뒷면 -> 아랫면
                for (j in 0 until 3) {
                    cube[1][j][2] = cube[3][2 - j][0]
                }
                //윗면 -> 뒷면
                for (j in 0 until 3) {
                    cube[3][j][0] = temp[2 - j]
                }
                if (direction == '+') break
            }
        }
    }
}