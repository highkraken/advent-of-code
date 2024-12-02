package aocutils

fun Any?.println(extra: String="") = kotlin.io.println("$extra$this")

val directionToPair = mapOf(
    'N' to (0 to -1),
    'S' to (0 to 1),
    'E' to (1 to 0),
    'W' to (-1 to 0)
)

val pairToDirection = mapOf(
    (0 to -1) to 'N',
    (0 to 1) to 'S',
    (1 to 0) to 'E',
    (-1 to 0) to 'W'
)