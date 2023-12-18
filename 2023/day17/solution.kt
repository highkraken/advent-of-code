package day17

import aocutils.println
import java.io.File
import java.util.PriorityQueue

private const val INPUT = "Kotlin/src/main/kotlin/day17/input.txt"
private const val DEMO_INPUT = "Kotlin/src/main/kotlin/day17/demo_input.txt"
private var actual = false
private const val PART1 = "Part 1: "
private const val PART2 = "Part 2: "

private fun readAndParseInput(): List<List<Int>> {
    val file = File(if (actual) INPUT else DEMO_INPUT)
    val regex = Regex("""\d""")
    val heatLoss = mutableListOf<List<Int>>()
    file.forEachLine { line ->
        heatLoss.add(regex.findAll(line).map { it.value.toInt() }.toList())
    }
    return heatLoss.toList()
}

private fun dijkstra(heatLossMap: List<List<Int>>, maxStraight: Int, minStraight: Int = 0): Int    { // Again HyperNeutrino to the rescue https://youtu.be/2pDSooPLLkI?si=hu9PsOiBWUApSG3M
    val priorityQueue = PriorityQueue(compareBy<List<Int>> { it[0] }.thenBy { it[1] }.thenBy { it[2] }.thenBy { it[3] }.thenBy { it[4] }.thenBy { it[5] })
    priorityQueue.add(listOf(0, 0, 0, 0, 0, 0))
    val visited = mutableSetOf<List<Int>>()
    while (priorityQueue.isNotEmpty())  {
        val head = priorityQueue.poll()
        val heatLoss = head[0]
        val r = head[1]
        val c = head[2]
        val dr = head[3]
        val dc = head[4]
        val n = head[5]

        if (r == heatLossMap.lastIndex && c == heatLossMap[0].lastIndex && n >= minStraight)
            return heatLoss

        if (!visited.add(listOf(r, c, dr, dc, n)))  continue
        if (n < maxStraight && (dr to dc) != (0 to 0))  {
            val nr = r + dr
            val nc = c + dc
            if (nr in heatLossMap.indices && nc in heatLossMap[0].indices)  {
                priorityQueue.add(listOf(heatLoss + heatLossMap[nr][nc], nr, nc, dr, dc, n + 1))
            }
        }

        if (n >= minStraight || (dr to dc) == (0 to 0)) {
            for ((ndr, ndc) in listOf((0 to 1), (0 to -1), (1 to 0), (-1 to 0))) {
                if ((ndr to ndc) != (dr to dc) && (ndr to ndc) != (-dr to -dc)) {
                    val nr = r + ndr
                    val nc = c + ndc
                    if (nr in heatLossMap.indices && nc in heatLossMap[0].indices) {
                        priorityQueue.add(listOf(heatLoss + heatLossMap[nr][nc], nr, nc, ndr, ndc, 1))
                    }
                }
            }
        }
    }
    return 0
}

private fun solvePart1(heatLossMap: List<List<Int>>): Int =
    dijkstra(heatLossMap = heatLossMap, maxStraight = 3)

private fun solvePart2(heatLossMap: List<List<Int>>): Int =
    dijkstra(heatLossMap = heatLossMap, maxStraight = 10, minStraight = 4)

private fun main()  {
    actual = true
    val heatLossMap = readAndParseInput()
    solvePart1(heatLossMap = heatLossMap).println(PART1) // 668
    solvePart2(heatLossMap = heatLossMap).println(PART2) // 788
}