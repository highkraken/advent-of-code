package day4

import aocutils.extractAllIntegers
import aocutils.println
import java.io.File
import kotlin.math.pow

const val inputFile = "Kotlin/src/main/kotlin/day4/input.txt"
const val demoInputFile = "Kotlin/src/main/kotlin/day4/demo_input.txt"
var actual = false
const val part1 = "Part 1: "
const val part2 = "Part 2: "

data class Card(val cardNumbers: List<Int>, val winningNumbers: List<Int>)

fun readAndParseInput(): List<Card> {
    val file = File(if (actual) inputFile else demoInputFile)
    val input = mutableListOf<Card>()
    file.forEachLine { line ->
        val (_, numbers) = line.split(": ")
        val (cardNumbers, winningNumbers) = numbers.split(" | ").map { it.extractAllIntegers() }
        input.add(Card(cardNumbers = cardNumbers, winningNumbers = winningNumbers))
    }
    return input.toList()
}

fun findCountOfMatchingNumbers(card: Card): Int {
    return card.cardNumbers.intersect(card.winningNumbers.toSet()).size
}

fun solvePart1(cards: List<Card>): Int  {
    var ans = 0
    cards.forEach { card ->
        ans += 2.0.pow((findCountOfMatchingNumbers(card) - 1).toDouble()).toInt()
    }
    return ans
}

fun solvePart2(cards: List<Card>): Int  {
    val copies = MutableList(cards.size) { 1 }
    cards.withIndex().forEach { (index, card) ->
        val matchingCount = findCountOfMatchingNumbers(card)
        for (i in index + 1 .. index + matchingCount)   {
            copies[i] += copies[index]
        }
    }
    return copies.sum()
}

fun main()  {
    actual = true
    val cards = readAndParseInput()
    solvePart1(cards).println(part1) // 21568
    solvePart2(cards).println(part2) // 11827296
}