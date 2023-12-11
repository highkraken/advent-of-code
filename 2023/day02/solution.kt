package day02

import aocutils.println
import java.io.File
import kotlin.math.max

const val inputFile = "Kotlin/src/main/kotlin/day2/input.txt"
const val demoInputFile = "Kotlin/src/main/kotlin/day2/demo_input.txt"
var actual = false
const val part1 = "Part 1: "
const val part2 = "Part 2: "
val LIMIT = Cube(red = 12, green = 13, blue = 14)

data class Game(val id: Int, val cubes: List<Cube>)

data class Cube(var red: Int, var green: Int, var blue: Int)

private operator fun Cube.compareTo(that: Cube): Int {
    if (this.red <= that.red && this.green <= that.green && this.blue <= that.blue) return -1
    if (this.red > that.red || this.green > that.green || this.blue > that.blue)    return 1
    return 0
}

fun readAndParseInput(): List<Game> {
    val file = File(if (actual) inputFile else demoInputFile)
    val input = mutableListOf<Game>()
    file.forEachLine { line ->
        val (game, subsets) = line.split(": ")
        val gameId = game.split(" ")[1].toInt()
        val cubes = mutableListOf<Cube>()
        subsets.split("; ").forEach { subset ->
            val currCube = Cube(red = 0, green = 0, blue = 0)
            subset.split(", ").forEach { cube ->
                val (count, color) = cube.split(" ")
                when (color)    {
                    "red" -> currCube.red += count.toInt()
                    "green" -> currCube.green += count.toInt()
                    "blue" -> currCube.blue += count.toInt()
                }
            }
            cubes.add(currCube)
        }
        input.add(Game(gameId, cubes.toList()))
    }
    return input.toList()
}

fun solvePart1(input: List<Game>): Int  {
    var ans = 0
    input.forEach { (gameId, cubes) ->
        var flag = true
        cubes.forEach { cube ->
            if (cube > LIMIT)   flag = false
        }
        if (flag)   ans += gameId
    }
    return ans
}

fun solvePart2(input: List<Game>): Int  {
    var ans = 0
    input.forEach { (_, cubes) ->
        val maxCount = mutableListOf(0, 0, 0)
        cubes.forEach { cube ->
            maxCount[0] = max(maxCount[0], cube.red)
            maxCount[1] = max(maxCount[1], cube.green)
            maxCount[2] = max(maxCount[2], cube.blue)
        }
        ans += maxCount.fold(1) { acc, count -> acc * count }
    }
    return ans
}

fun main()  {
    actual = true
    val input = readAndParseInput()
    solvePart1(input).println(part1) // 2348
    solvePart2(input).println(part2) // 76008
}