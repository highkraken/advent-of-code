package day

import java.io.File

const val inputFile = "src/main/kotlin/input.txt"
const val demoInputFile = "src/main/kotlin/demo_input.txt"
var actual = false

fun readFileInput() {
    val file = File(if (actual) inputFile else demoInputFile)

    file.forEachLine {

    }
}

fun main()  {
    actual = false
    val input = readFileInput()

}