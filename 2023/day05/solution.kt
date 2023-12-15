package day05

import aocutils.extractAllIntegers
import aocutils.println
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.io.File

const val inputFile = "Kotlin/src/main/kotlin/day05/input.txt"
const val demoInputFile = "Kotlin/src/main/kotlin/day05/demo_input.txt"
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

fun solvePart2(almanac: Almanac): Long  { // Thanks to HyperNeutrino https://youtu.be/NmxHw_bHhGM?si=z6W11FSj6dMev7EP
    var seeds = almanac.seeds.chunked(2).map { listOf(it[0], it[1] + it[0]) }.toMutableList()
    for (map in almanac.maps)   {
        val ans = mutableListOf<List<Long>>()
         xd@ while (seeds.size > 0) {
            val (start, end) = seeds.removeLast()
            for ((desStart, srcStart, range) in map.chunked(3)) {
                val rStart = maxOf(start, srcStart)
                val rEnd = minOf(end, srcStart + range)
                if (rStart < rEnd) {
                    ans.add(listOf(rStart + desStart - srcStart, rEnd + desStart - srcStart))
                    if (rStart > start) {
                        seeds.add(listOf(start, rStart))
                    }
                    if (end > rEnd) {
                        seeds.add(listOf(rEnd, end))
                    }
                    continue@xd
                }
            }
            ans.add(listOf(start, end))
        }
        seeds = ans
    }
    return seeds.minOf { it[0] }
}

fun main()  {
    actual = true
    val almanac = readAndParseInput()
    solvePart1(almanac).println(part1) // 214922730
    solvePart2(almanac).println(part2) // 148041808
}