package aocutils

import kotlin.math.abs

operator fun Int.not() = this == 0

fun Long.gcd(that: Long): Long  {
    var num1 = this
    var num2 = that
    while (num2 != 0L) {
        val temp = num2
        num2 = num1 % num2
        num1 = temp
    }
    return num1
}

fun Long.lcm(that: Long): Long = abs(this * that) / this.gcd(that)