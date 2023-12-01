package aocutils

fun String.extractAllIntegers(): List<Int> {
    val regex = Regex("-?\\b\\d+\\b")
    return regex
        .findAll(this)
        .map { it.value.toInt() }
        .toList()
}