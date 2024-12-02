package aocutils

fun String.extractAllIntegers(): List<Long> {
    val regex = Regex("-?\\b\\d+\\b")
    return regex
        .findAll(this)
        .map { it.value.toLong() }
        .toList()
}