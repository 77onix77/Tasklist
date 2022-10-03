package tasklist

fun main() {
    val list = mutableListOf<String>()
    println("Input the tasks (enter a blank line to end):")
    while(true) {
        val str = readln().trim()
        if (str.isEmpty()) break
        else list += str
    }

    if (list.isEmpty()) println("No tasks have been input")

    if (list.size < 10) {
        for (i in 1..list.size) {
            println("$i  ${list[i - 1]}")
        }
    } else {
        for (i in 1..9) {
            println("$i  ${list[i - 1]}")
        }
        for (i in 10..list.size) {
            println("$i ${list[i - 1]}")
        }
    }
}


