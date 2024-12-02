package aocutils

import kotlin.math.abs

data class Point(var x: Int, var y: Int)    {
    infix fun manhattanDistanceTo(that: Point): Int =
        abs(this.x - that.x) + abs(this.y - that.y)
}
