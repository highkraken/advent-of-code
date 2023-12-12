package day08

import aocutils.lcm
import aocutils.println
import java.io.File

const val inputFile = "Kotlin/src/main/kotlin/day08/input.txt"
const val demoInputFile = "Kotlin/src/main/kotlin/day08/demo_input2.txt"
var actual = false
const val part1 = "Part 1: "
const val part2 = "Part 2: "
val startersA = mutableListOf<Int>()
val startersZ = mutableListOf<Int>()

data class Node(val nodeId: Int, val left: Int, val right: Int)

data class Network(val instructions: String, val nodes: List<Node>)

fun readAndParseInput(): Network {
    val file = File(if (actual) inputFile else demoInputFile)
    val input = file.readLines().toMutableList()
    val instructions = input[0]
    input.removeFirst()
    input.removeFirst()
    val nodeNames = input.map { it.substring(0, 3) }.mapIndexed { index, s ->
        s to index
    }.toMap()
    val nodes = mutableListOf<Node>()
    input.forEach { line ->
        val (_, nodeId, left, right) = Regex("""(\w+) = \((\w+), (\w+)\)""").find(line)!!.groupValues
        nodes.add(
            Node(
                nodeId = nodeNames[nodeId]!!,
                left = nodeNames[left]!!,
                right = nodeNames[right]!!
            )
        )
        if (nodeId.last() == 'A')   startersA.add(nodeNames[nodeId]!!)
        if (nodeId.last() == 'Z')   startersZ.add(nodeNames[nodeId]!!)
    }
    return Network(instructions = instructions, nodes = nodes)
}

fun solvePart1(network: Network): Int   {
    var steps = 0
    var dir = 0
    var curr = 0
    while (curr != network.nodes.lastIndex) {
        curr =
            if (network.instructions[dir] == 'L')
                network.nodes[curr].left
            else
                network.nodes[curr].right
        dir = (dir + 1) % network.instructions.length
        steps++
    }
    return steps
}

fun solvePart2(network: Network): Long  {
    val cycle = MutableList(startersA.size)  { 0 }
    for (index in startersA.indices)    {
        val set = mutableSetOf<Int>()
        var pos = startersA[index]
        var dir = 0
        while (pos !in set) {
            pos =
                if (network.instructions[dir] == 'L')
                    network.nodes[pos].left
                else
                    network.nodes[pos].right
            if (pos in startersZ)   set.add(pos)
            dir = (dir + 1) % network.instructions.length
            cycle[index]++
        }
    }
    return cycle.fold(1L)   { acc, steps ->
        acc.lcm(steps.toLong())
    }
}

fun main()  {
    actual = true
    val network = readAndParseInput()
    solvePart1(network = network).println(part1) // 16697
    solvePart2(network = network).println(part2) // 10668805667831
}