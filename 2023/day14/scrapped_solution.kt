package day14

//import aocutils.Point
//import aocutils.println
//import java.io.File
//
//const val inputFile = "Kotlin/src/main/kotlin/day14/input.txt"
//const val demoInputFile = "Kotlin/src/main/kotlin/day14/demo_input.txt"
//var actual = false
//const val part1 = "Part 1: "
//const val part2 = "Part 2: "
//val directionMap = mapOf('N' to (0 to -1), 'S' to (0 to 1), 'E' to (1 to 0), 'W' to (-1 to 0))
//val rockPositions = mutableListOf<Point>()
//
//fun List<String>.extractRockPositions() {
//    for (row in this.indices)   {
//        for (col in this[0].indices)    {
//            if (this[row][col] == 'O') rockPositions.add(Point(x = col, y = row))
//        }
//    }
//}
//
//fun List<String>.tilt(direction: Char): List<String>    {
//    val (dx, dy) = directionMap[direction]!!
//    val positions = this.map { line -> line.map { it }.toMutableList() }.toMutableList()
//    rockPositions.forEach { rock ->
//        var x = rock.x
//        var y = rock.y
//        while (x + dx in positions[0].indices && y + dy in positions.indices)   {
//            if (positions[y + dy][x + dx] != '.')   break
//            x += dx
//            y += dy
//        }
//        if (x != rock.x || y != rock.y) {
//            positions[y][x] = 'O'
//            positions[rock.y][rock.x] = '.'
//        }
//    }
//    return positions.map { it.joinToString(separator = "") }
//}
//
//fun readAndParseInput(): List<String> =
//    File(if (actual) inputFile else demoInputFile)
//        .readLines()
//
//fun solvePart1(platform: List<String>): Long    {
//    val tiltedPlatform = platform.tilt(direction = 'N')
//    var load = 0L
//    for (row in tiltedPlatform.withIndex())    {
//        load += row.value.count { it == 'O' } * (tiltedPlatform.size - row.index)
//    }
//    return load
//}
//
//fun solvePart2(platform: List<String>): Long    {
//    var tiltedPlatform = platform
//    val set = setOf(tiltedPlatform)
//    for (i in 0 until 1_00_00_00_000)   {
//        tiltedPlatform = tiltedPlatform.tilt(direction = 'N')
//        if (tiltedPlatform in set)  break
//        tiltedPlatform = tiltedPlatform.tilt(direction = 'W')
//        if (tiltedPlatform in set)  break
//        tiltedPlatform = tiltedPlatform.tilt(direction = 'S')
//        if (tiltedPlatform in set)  break
//        tiltedPlatform = tiltedPlatform.tilt(direction = 'E')
//        if (tiltedPlatform in set)  break
//    }
//    var load = 0L
//    for (row in tiltedPlatform.withIndex())    {
//        load += row.value.count { it == 'O' } * (tiltedPlatform.size - row.index)
//    }
//    return load
//}
//
//fun main()  {
//    actual = false
//    val platform = readAndParseInput()
//    platform.extractRockPositions()
//    solvePart1(platform = platform).println(part1)
//    solvePart2(platform = platform).println(part2)
//}