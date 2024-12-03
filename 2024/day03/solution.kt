package day03

import java.io.File
import aocutils.*

private val DAY = (object {}).javaClass.packageName
private val INPUT = "$DAY/input.txt"
private val DEMO_INPUT = "$DAY/demo_input.txt"
private var actual = false
private const val PART1 = "Part 1: "
private const val PART2 = "Part 2: "

private fun readAndParseInput(toggle: Boolean = false): List<Pii> {
    val file = File(if (actual) INPUT else DEMO_INPUT)
    val input = mutableListOf<Pii>()
    val mulPattern = """mul\((\d{1,3}),(\d{1,3})\)"""
    val doPattern = """do\(\)"""
    val dontPattern = """don't\(\)"""
    val finalPattern = if (toggle) """$mulPattern|$doPattern|$dontPattern""" else mulPattern
    var enabled = true
    file.forEachLine { line ->
        finalPattern.toRegex().findAll(line).forEach {
            when (it.value) {
                "don't()" -> enabled = false
                "do()" -> enabled = true
                else -> {
                    if (enabled) {
                        val (first, second) = it.destructured
                        input.add(Pii(first.toInt(), second.toInt()))
                    }
                }
            }
        }
    }
    return input
}

fun solve(input: List<Pii>): Long =
    input.fold(0L) { acc, pair ->
        acc + pair.first * pair.second
    }

private fun main() {
    actual = true
    val inputP1 = readAndParseInput()
    solve(inputP1).println(PART1) // 189527826
    val inputP2 = readAndParseInput(toggle = true)
    solve(inputP2).println(PART2) // 63013756
}