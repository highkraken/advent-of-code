package day12

//import aocutils.extractAllIntegers
//import aocutils.println
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.async
//import kotlinx.coroutines.runBlocking
//import java.io.File
//import kotlin.system.measureTimeMillis
//
//const val inputFile = "Kotlin/src/main/kotlin/day12/input.txt"
//const val demoInputFile = "Kotlin/src/main/kotlin/day12/demo_input.txt"
//var actual = false
//const val part1 = "Part 1: "
//const val part2 = "Part 2: "
//
//data class Record(val configuration: String, val groups: List<Int>)
//
//fun readAndParseInput(): List<Record> {
//    val file = File(if (actual) inputFile else demoInputFile)
//    val records = mutableListOf<Record>()
//    file.forEachLine { line ->
//        val (spring, groups) = line.split(" ")
//        records.add(Record(configuration = spring, groups = groups.extractAllIntegers().map { it.toInt() }))
//    }
//    return records.toList()
//}
//
//fun String.isValidArrangement(groups: List<Int>): Boolean   {
//    val springs = Regex("""(#+)""").findAll(this).map { it.value }.toList()
//    if (springs.size == groups.size)    {
//        for (ind in groups.indices) {
//            if (springs[ind].length != groups[ind]) return false
//        }
//    }   else    {
//        return false
//    }
//    return true
//}
//
//fun String.extractUnknownIndices(): List<Int> =
//    this.indices.filter { this[it] == '?' }
//
//fun findArrangements(spring: MutableList<Char>, index: Int, arrangements: MutableList<Long>, unknowns: List<Int>, groups: List<Int>)    {
//    if (index !in unknowns.indices) {
//        if (spring.joinToString("").isValidArrangement(groups)) arrangements[0]++
//        return
//    }
//    spring[unknowns[index]] = '.'
//    findArrangements(spring, index + 1, arrangements, unknowns, groups)
//    spring[unknowns[index]] = '#'
//    findArrangements(spring, index + 1, arrangements, unknowns, groups)
//}
//
//fun solvePart1(records: List<Record>): Long = runBlocking(Dispatchers.Default) {
//    val deferredResults = records.chunked(10).map { chunk ->
//        async {
//            chunk.sumOf { record ->
//                val arrangements = mutableListOf(0L)
//                findArrangements(record.configuration.map { it }.toMutableList(), 0, arrangements, record.configuration.extractUnknownIndices(), record.groups)
//                arrangements[0]
//            }
//        }
//    }
//    deferredResults.sumOf { it.await() }
//}
//
//fun solvePart2(records: List<Record>): Long = runBlocking(Dispatchers.Default)  {
//    val deferredResults = records.chunked(10).map { chunk ->
//        async {
//            chunk.sumOf { record ->
//                val cfg = List(5) { record.configuration }.joinToString("?")
//                val grp = List(5) { record.groups }.flatten()
//                val arrangements = mutableListOf(0L)
//                findArrangements(cfg.map { it }.toMutableList(), 0, arrangements, cfg.extractUnknownIndices(), grp)
//                arrangements[0]
//            }
//        }
//    }
//    deferredResults.sumOf { it.await() }
//}
//
//fun main()  {
//    actual = false
//    val records = readAndParseInput()
//    solvePart1(records).println(part1) // 7307
//    solvePart2(records).println(part2)
//}