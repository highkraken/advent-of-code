package day19

import aocutils.println
import java.io.File
import java.math.BigInteger
import kotlin.math.max
import kotlin.math.min

private const val INPUT = "Kotlin/src/main/kotlin/day19/input.txt"
private const val DEMO_INPUT = "Kotlin/src/main/kotlin/day19/demo_input.txt"
private var actual = false
private const val PART1 = "Part 1: "
private const val PART2 = "Part 2: "

private data class XMAS(val x: Int, val m: Int, val a: Int, val s: Int)

private data class Condition(val part: Char, val comparator: Char, val comparand: Int, val ifSatisfied: Int)    {
    fun isBeingSatisfied(gearPart: XMAS): Boolean   {
        val part = when (this.part) {
            'x' -> gearPart.x
            'm' -> gearPart.m
            'a' -> gearPart.a
            's' -> gearPart.s
            else -> -1
        }
        return if (comparator == '<') part < comparand else part > comparand
    }

    fun alter(): Condition  {
        val comparator = if (this.comparator == '<') '>' else '<'
        val comparand = this.comparand + if (this.comparator == '<') -1 else 1
        return Condition(part = part, comparator = comparator, comparand = comparand, ifSatisfied = ifSatisfied)
    }
}

private data class Workflow(val conditions: List<Condition>, val endPoint: Int)

private fun readAndParseInput(): Pair<List<Workflow>, List<XMAS>> {
    val file = File(if (actual) INPUT else DEMO_INPUT)
    val (workflowsStr, partsStr) = file.readText().split("\n\n")
    val wfRegex = Regex("""(\w+)\{(.*)}""")
    val ptRegex = Regex("""\{x=(\d+),m=(\d+),a=(\d+),s=(\d+)}""")
    val parts = mutableListOf<XMAS>()
    partsStr.split("\n").forEach { part ->
        val (_, x, m, a, s) = ptRegex.find(part)!!.groupValues.map { it.toIntOrNull() ?: 0 }
        parts.add(XMAS(x = x, m = m, a = a, s = s))
    }
    val names = mutableListOf<String>()
    val cons = mutableListOf<String>()
    workflowsStr.split("\n").forEach { workflow ->
        val (_, name, condition) = wfRegex.find(workflow)!!.groupValues
        names.add(name)
        cons.add(condition)
    }
    val nameToInd = (names.mapIndexed { index, s -> s to index } + listOf(("A" to -1), ("R" to -2))).toMap()
    val workflows = mutableListOf<Workflow>()
    cons.forEach { con ->
        val list = con.split(",").toMutableList()
        val endPoint = nameToInd[list.removeLast()]!!
        val conditions = mutableListOf<Condition>()
        list.forEach { item ->
            val (condition, ifTrue) = item.split(":")
            val part = condition[0]
            val comparator = condition[1]
            val comparand = condition.substring(2).toInt()
            conditions.add(
                Condition(
                    part = part,
                    comparator = comparator,
                    comparand = comparand,
                    ifSatisfied = nameToInd[ifTrue]!!
                )
            )
        }
        workflows.add(Workflow(conditions = conditions, endPoint = endPoint))
    }
    return workflows.toList() to parts.toList()
}

private fun getAcceptedPaths(currPath: MutableList<Condition>, acceptedPaths: MutableList<List<Condition>>, workflows: List<Workflow>, wfInd: Int, cdInd: Int)  {
    if (wfInd < 0)   {
        if (wfInd == -1) {
            acceptedPaths.add(currPath.toList())
        }
        return
    }
    val curr = currPath.map { it }.toMutableList()
    for (ind in cdInd..workflows[wfInd].conditions.lastIndex)   {
        val condition = workflows[wfInd].conditions[ind]
        curr.add(condition)
        getAcceptedPaths(curr, acceptedPaths, workflows, condition.ifSatisfied, 0)
        curr.removeLast()
        curr.add(condition.alter())
    }
    getAcceptedPaths(curr, acceptedPaths, workflows, workflows[wfInd].endPoint, 0)
}

private fun solvePart1(workflows: List<Workflow>, gearParts: List<XMAS>): Long  {
    var ratings = 0L
    gearParts.forEach { part ->
        var endPoint = 0
        outer@ while (endPoint >= 0)    {
            for (condition in workflows[endPoint].conditions)   {
                if (condition.isBeingSatisfied(gearPart = part))    {
                    endPoint = condition.ifSatisfied
                    continue@outer
                }
            }
            endPoint = workflows[endPoint].endPoint
        }
        if (endPoint == -1) ratings += part.x + part.m + part.a + part.s
    }
    return ratings
}

private fun solvePart2(workflows: List<Workflow>): BigInteger {
    var ratings = BigInteger("0")
    val acceptedPaths = mutableListOf<List<Condition>>()
    getAcceptedPaths(
        currPath = mutableListOf(),
        acceptedPaths = acceptedPaths,
        workflows = workflows,
        wfInd = 0,
        cdInd = 0
    )
    val diff: (Pair<Int, Int>)-> BigInteger = { (first, second) ->
        (second - first).toBigInteger()
    }
    acceptedPaths.forEach { conditions ->
        val x = mutableListOf(1 to 4001)
        val m = mutableListOf(1 to 4001)
        val a = mutableListOf(1 to 4001)
        val s = mutableListOf(1 to 4001)
        conditions.forEach { (char, comp, cmd, _) ->
            val part = when (char) {
                'x' -> x
                'm' -> m
                'a' -> a
                's' -> s
                else -> mutableListOf()
            }
            when (comp) {
                '<' -> part[0] = part[0].copy(second = min(part[0].second, cmd))
                '>' -> part[0] = part[0].copy(first = max(part[0].first, cmd + 1))
            }
        }
        ratings += diff(x[0]) * diff(m[0]) * diff(a[0]) * diff(s[0])
    }
    return ratings
}

private fun main()  {
    actual = true
    val (workflows, parts) = readAndParseInput()
    solvePart1(workflows = workflows, gearParts = parts).println(PART1) // 391132
    solvePart2(workflows = workflows).println(PART2) // 128163929109524
}