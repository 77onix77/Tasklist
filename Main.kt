package tasklist

fun main() {
    val list = mutableListOf<String>()

    while (true) {
        println("Input an action (add, print, end):")
        when (readln()) {
            "add" -> add(list)
            "print" -> printList(list)
            "end" -> {
                println("Tasklist exiting!")
                return
            }
            else -> println("The input action is invalid")
        }
    }
}

fun add(list: MutableList<String>) {
    println("Input a new task (enter a blank line to end):")
    val str1 = readln().trim()
    if (str1.isEmpty()) {
        println("The task is blank")
        return
    } else list += str1
    while (true) {
        val str = readln().trim()
        if (str.isEmpty()) break
        else list[list.lastIndex] += "\n   $str"
    }
}

fun printList(list: List<String>) {
    if (list.isEmpty()) println("No tasks have been input")

    if (list.size < 10) {
        for (i in 1..list.size) {
            println("$i  ${list[i - 1]}")
            println()
        }
    } else {
        for (i in 1..9) {
            println("$i  ${list[i - 1]}")
            println()
        }
        for (i in 10..list.size) {
            println("$i ${list[i - 1]}")
            println()
        }
    }
}


