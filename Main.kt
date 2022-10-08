package tasklist

import kotlinx.datetime.*

data class Task(var priority: String, var date: String, var time: String, var task: String) {

    fun tag(): String {
        val tag = Clock.System.now().toLocalDateTime(TimeZone.of("UTC+0")).date.daysUntil(date.toLocalDate())
        return when {
            tag == 0 -> "\u001B[103m \u001B[0m"
            tag > 0 -> "\u001B[102m \u001B[0m"
            else -> "\u001B[101m \u001B[0m"
        }
    }

    fun prior(): String {
        return when (priority) {
            "C" -> "\u001B[101m \u001B[0m"
            "H" -> "\u001B[103m \u001B[0m"
            "N" -> "\u001B[102m \u001B[0m"
            "L" -> "\u001B[104m \u001B[0m"
            else -> ""
        }
    }
}

fun main() {
    val list = mutableListOf<Task>()

    while (true) {
        println("Input an action (add, print, edit, delete, end):")
        when (readln()) {
            "add" -> add(list)
            "print" -> printList(list)
            "edit" -> edit(list)
            "delete" -> delete(list)
            "end" -> {
                println("Tasklist exiting!")
                return
            }
            else -> println("The input action is invalid")
        }
    }
}

fun delete(list: MutableList<Task>) {
    if (list.isEmpty()) {
        println("No tasks have been input")
        return
    }
    printList(list)
    while (true){
        println("Input the task number (1-${list.size}):")
        val num = readln()
        if (Regex("\\d+").matches(num) && num.toInt() in 1..list.size) {
            list.removeAt(num.toInt() - 1)
            println("The task is deleted")
            break
        } else println("Invalid task number")
    }
}

fun edit(list: MutableList<Task>) {
    if (list.isEmpty()) {
        println("No tasks have been input")
        return
    }
    printList(list)
    val number: Int
    while (true){
        println("Input the task number (1-${list.size}):")
        val num = readln()
        if (Regex("\\d+").matches(num) && num.toInt() in 1..list.size) {
            number = num.toInt()
            break
        } else println("Invalid task number")
    }

    while (true) {
        println("Input a field to edit (priority, date, time, task):")
        when(readln()) {
            "priority" -> {
                list[number - 1].priority = priority()
                println("The task is changed")
                break
            }
            "date" -> {
                list[number - 1].date = date()
                println("The task is changed")
                break
            }
            "time" -> {
                list[number - 1].time = time()
                println("The task is changed")
                break
            }
            "task" -> {
                val task = task()
                if (task.isEmpty()) {
                    println("The task is blank")
                    return
                }
                list[number - 1].task = task
                println("The task is changed")
                break
            }
            else -> println("Invalid field")
        }
    }

}

fun task(): String {
    println("Input a new task (enter a blank line to end):")
    val str1 = readln().trim()
    var task  = ""
    if (str1.isEmpty()) return ""
    else task += str1
    while (true) {
        val str = readln().trim()
        if (str.isEmpty()) break
        else task += "\n$str"
    }
    return task
}

fun add(list: MutableList<Task>) {
    val priority = priority()
    val date = date()
    val time = time()
    val task  = task()
    if (task.isEmpty()) {
        println("The task is blank")
        return
    }
    list += Task(priority, date, time, task)
}

fun priority(): String {
    val pr = listOf("C", "H", "N", "L")
    var priority = ""
    while (priority.uppercase() !in pr) {
        println("Input the task priority (C, H, N, L):")
        priority = readln()
    }
    return priority.uppercase()
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

fun taskToLust(string: String): List<String> {
    val listTask = mutableListOf<String>()
    var i = 0
    var str = ""
    for (ch in string){
        if (ch == '\n') {
            listTask += str
            i = 0
            str = ""
        } else if (i == 44) {
            listTask += str
            i = 1
            str = ch.toString()
        } else {
            str += ch
            i++
        }
    }
    listTask += str
    if (listTask.size > 1) {
        for (j in 1..listTask.lastIndex) {
            listTask[j] = "|    |            |       |   |   |${listTask[j]}${" ".repeat(44 - listTask[j].length)}|"
        }
    }
    return listTask
}

fun printList(list: List<Task>) {
    if (list.isEmpty()) {
        println("No tasks have been input")
        return
    }



    println("+----+------------+-------+---+---+--------------------------------------------+\n" +
            "| N  |    Date    | Time  | P | D |                   Task                     |\n" +
            "+----+------------+-------+---+---+--------------------------------------------+")

    for (i in 1..list.size) {
        val listTask = taskToLust(list[i-1].task)
        println("| $i  | ${list[i - 1].date} | ${list[i - 1].time} | ${list[i - 1].prior()} | ${list[i - 1].tag()} |${listTask[0]}${" ".repeat(44 - listTask[0].length)}|")
        if (listTask.size > 1) {
            for (j in 1..listTask.lastIndex) {
                println(listTask[j])
            }
        }
        println("+----+------------+-------+---+---+--------------------------------------------+")
    }
}