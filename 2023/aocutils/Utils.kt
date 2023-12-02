package aocutils

fun Any?.println(extra: String="") = kotlin.io.println("$extra $this")