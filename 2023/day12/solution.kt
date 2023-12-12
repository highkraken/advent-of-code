package day12

import aocutils.extractAllIntegers
import aocutils.println
import java.io.File

const val inputFile = "Kotlin/src/main/kotlin/day12/input.txt"
const val demoInputFile = "Kotlin/src/main/kotlin/day12/demo_input.txt"
var actual = false
const val part1 = "Part 1: "
const val part2 = "Part 2: "
val memoized = mutableMapOf<Pair<String, List<Int>>, Long>()

data class Record(val configuration: String, val groups: List<Int>)

fun readAndParseInput(): List<Record> {
    val file = File(if (actual) inputFile else demoInputFile)
    val records = mutableListOf<Record>()
    file.forEachLine { line ->
        val (spring, groups) = line.split(" ")
        records.add(Record(configuration = spring, groups = groups.extractAllIntegers().map { it.toInt() }))
    }
    return records.toList()
}

fun String.subStr(startIndex: Int): String    {
    if (startIndex >= this.length)  return ""
    return this.substring(startIndex)
}

fun solve(configuration: String, groups: List<Int>): Long   { // Thanks to HyperNeutrino https://youtu.be/g3Ms5e7Jdqo?si=UGQmKhpITpsbzFD9
    if (configuration == "")    {
        return if (groups.isEmpty()) 1L else 0L
    }

    if (groups.isEmpty())   {
        return if ('#' in configuration) 0L else 1L
    }

    val KEY = configuration to groups

    if (KEY in memoized.keys)   {
        return memoized[KEY]!!
    }

    var ans = 0L

    if (configuration[0] in ".?")   {
        ans += solve(configuration.subStr(1), groups)
    }

    if (configuration[0] in "#?")   {
        if (
            groups[0] <= configuration.length &&
            '.' !in configuration.substring(0, groups[0]) &&
            (groups[0] == configuration.length || configuration[groups[0]] != '#')
        )   {
            ans += solve(configuration.subStr(groups[0] + 1), groups.subList(1, groups.size))
        }
    }

    memoized[KEY] = ans
    return ans
}

fun solvePart1(records: List<Record>): Long {
    return records.sumOf { (configuration, groups) ->
        solve(configuration = configuration, groups = groups)
    }
}

fun solvePart2(records: List<Record>): Long {
    return records.sumOf { (configuration, groups) ->
        val cfg = List(5) { configuration }.joinToString(separator = "?")
        val grp = List(5) { groups }.flatten()
        solve(configuration = cfg, groups = grp)
    }
}

fun main()  {
    actual = true
    val records = readAndParseInput()
    solvePart1(records).println(part1) // 7307
    solvePart2(records).println(part2) // 3415570893842
}