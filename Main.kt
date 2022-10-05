package tasklist

import kotlinx.datetime.*
data class Task(val priority: String, val date: String, val time: String, val task: String) {
    override fun toString(): String {
        return "$date $time ${priority.uppercase()}\n" + task
    }
}

fun main() {
    val list = mutableListOf<Task>()

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

fun add(list: MutableList<Task>) {
    val pr = listOf("C", "H", "N", "L")
    var priority = ""
    while (priority.uppercase() !in pr) {
        println("Input the task priority (C, H, N, L):")
        priority = readln()
    }

    val date = date()

    val time = time()

    println("Input a new task (enter a blank line to end):")
    val str1 = readln().trim()
    var task  = ""
    if (str1.isEmpty()) {
        println("The task is blank")
        return
    } else task += "   $str1"
    while (true) {
        val str = readln().trim()
        if (str.isEmpty()) break
        else task += "\n   $str"
    }
    list += Task(priority, date, time, task)
}

fun time(): String {
    var time = ""
    while (time.isEmpty()) {
        println("Input the time (hh:mm):")
        val input = readln()
        if (!Regex("\\d{1,2}:\\d{1,2}").matches(input)) {
            println("The input time is invalid")
            continue
        }
        val list = input.split(":")
        if (list[0].toInt() !in 0..23 || list[1].toInt() !in 0..59) {
            println("The input time is invalid")
            continue
        }
        val hh = if (list[0].length == 1) "0${list[0]}" else list[0]
        val mm = if (list[1].length == 1) "0${list[1]}" else list[1]
        time = "$hh:$mm"
    }
    return time
}

fun date(): String {
    var date = ""
    while (date.isEmpty()) {
        println("Input the date (yyyy-mm-dd):")
        val input = readln()
        if (!Regex("\\d{4}-\\d{1,2}-\\d{1,2}").matches(input)) {
            println("The input date is invalid")
            continue
        }
        val list = input.split("-")
        val yyyy = list[0]
        val mm = if (list[1].length == 1) "0${list[1]}" else list[1]
        val dd = if (list[2].length == 1) "0${list[2]}" else list[2]
        val dl: LocalDate
        try {
            dl = "$yyyy-$mm-$dd".toLocalDate()
        } catch (e: IllegalArgumentException) {
            println("The input date is invalid")
            continue
        }
        date = dl.toString()
    }
    return date
}

fun printList(list: List<Task>) {
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


