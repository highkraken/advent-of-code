package day7

import aocutils.println
import java.io.File

const val inputFile = "Kotlin/src/main/kotlin/input.txt"
const val demoInputFile = "Kotlin/src/main/kotlin/demo_input.txt"
var actual = false
const val part1 = "Part 1: "
const val part2 = "Part 2: "

data class Hand(val hand: String, val bid: Int) {
    var strongnessLevel: Int = -1
        get() = setStrongnessLevel()
        private set

    var modifiedStrongnessLevel = -1
        get() = setStrongnessLevel(modified = true)
        private set

    private fun setStrongnessLevel(modified: Boolean=false): Int    {
        val characters = this.hand.groupingBy { it }.eachCount().toMutableMap()
        if (modified && characters.containsKey('J') && characters.size > 1)   {
            val char = characters.entries.filter { it.key != 'J' }.maxByOrNull { it.value }!!.key
            characters[char] = characters[char]!! + characters['J']!!
            characters.remove('J')
        }
        return when (characters.size)  {
            1 -> 7
            2 -> if (characters.values.contains(4)) 6 else 5
            3 -> if (characters.values.contains(3)) 4 else 3
            4 -> 2
            5 -> 1
            else -> -1
        }
    }
}

val ranks = mutableListOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2')

class HandComparator(private val modified: Boolean=false): Comparator<Hand> {
    override fun compare(hand1: Hand, hand2: Hand): Int {
        val strongness1 = if (modified) hand1.modifiedStrongnessLevel else hand1.strongnessLevel
        val strongness2 = if (modified) hand2.modifiedStrongnessLevel else hand2.strongnessLevel
        if (strongness1 > strongness2) return 1
        if (strongness1 < strongness2) return -1
        if (modified) ranks.add('J') else ranks.add(3, 'J')
        for (index in 0 until 5) {
            val rank1 = ranks.indexOf(hand1.hand[index])
            val rank2 = ranks.indexOf(hand2.hand[index])
            if (rank1 < rank2) return 1
            if (rank1 > rank2) return -1
        }
        return 0
    }
}

fun readAndParseInput(): List<Hand> {
    val file = File(if (actual) inputFile else demoInputFile)
    val hands = mutableListOf<Hand>()
    file.forEachLine { line ->
        val (hand, bid) = line.split(" ")
        hands.add(Hand(hand = hand, bid = bid.toInt()))
    }
    return hands.toList()
}

fun solvePart1(hands: List<Hand>): Int  {
    val sortedHands = hands.sortedWith(HandComparator())
    var ans = 0
    for (index in sortedHands.indices)  {
        ans += (index + 1) * sortedHands[index].bid
    }
    return ans
}

fun solvePart2(hands: List<Hand>): Int  {
    val sortedHands = hands.sortedWith(HandComparator(true))
    var ans = 0
    for (index in sortedHands.indices)  {
        ans += (index + 1) * sortedHands[index].bid
    }
    return ans
}

fun main()  {
    actual = true
    val hands = readAndParseInput()
    solvePart1(hands).println(part1) // 246424613
    solvePart2(hands).println(part2) // 248256639
}