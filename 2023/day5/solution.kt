package day5

import aocutils.extractAllIntegers
import aocutils.println
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import java.io.File

const val inputFile = "Kotlin/src/main/kotlin/day5/input.txt"
const val demoInputFile = "Kotlin/src/main/kotlin/day5/demo_input.txt"
var actual = false
const val part1 = "Part 1: "
const val part2 = "Part 2: "

data class Almanac(val seeds: List<Long>, val maps: List<List<Long>>)

fun readAndParseInput(): Almanac {
    val file = File(if (actual) inputFile else demoInputFile)
    val input = file.readLines()
    val seeds = input[0].extractAllIntegers()
    var ind = 3
    var maps = mutableListOf<List<Long>>()
    while (ind in input.indices)    {
        val list = mutableListOf<Long>()
        while (ind in input.indices && input[ind].isNotEmpty()) {
            list.addAll(input[ind].extractAllIntegers())
            ind++
        }
        maps.add(list.toList())
        ind += 2
    }
    maps = maps.map { map -> map.chunked(3).sortedBy { it[1] }.flatten() }.toMutableList()
    return Almanac(seeds = seeds, maps =  maps)
}

fun getLocationNumber(seed: Long, maps: List<List<Long>>): Long = runBlocking(Dispatchers.Default) {
    var parse = seed
    maps.forEach { map ->
        for ((destStart, srcStart, range) in map.chunked(3)) {
            if (parse in srcStart until srcStart + range) {
                parse = parse - srcStart + destStart
                return@forEach
            }
        }
    }
    parse
}

fun solvePart1(almanac: Almanac): Long  {
    val locationNumbers = mutableListOf<Long>()
    almanac.seeds.forEach { seed ->
        runBlocking(Dispatchers.Default) {
            locationNumbers.add(getLocationNumber(seed = seed, maps = almanac.maps))
        }
    }
    return locationNumbers.minOf { it }
}

fun solvePart2(almanac: Almanac): Long  {
    val seeds = almanac.seeds.chunked(2).map { listOf(it[0], it[1] + it[0]) }.toMutableList()
    val ans = mutableListOf<List<Long>>()
    while (seeds.size > 0)  {
        val (start, end) = seeds.removeLast()
        almanac.maps.forEach { map ->
            for ((desStart, srcStart, range) in map.chunked(3)) {
                print("$desStart $srcStart $range --> ")
                val rStart = maxOf(start, srcStart)
                val rEnd = minOf(end, srcStart + range)
                println("$rStart - $rEnd -> $start - $end")
                if (rStart < rEnd) {
                    ans.add(listOf(rStart + desStart - srcStart, rEnd + desStart - srcStart))
                    if (rStart > start) {
                        seeds.add(listOf(start, rStart))
                    }
                    if (end > rEnd) {
                        seeds.add(listOf(rEnd, end))
                    }
                    return@forEach
                }
            }
        }
        ans.add(listOf(start, end))
    }
    println(ans.sortedBy { it[0] })
    return ans.minOf { it[0] }
}

fun main()  {
    actual = false
    val almanac = readAndParseInput()
    solvePart1(almanac).println(part1) // 214922730
    solvePart2(almanac).println(part2)
}