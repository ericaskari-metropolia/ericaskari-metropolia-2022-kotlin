// Exercise 1
// Name:        Mohammad Askari
// Student id:  2113562

fun main(args: Array<String>) {
    val enteredFloatNumbers = mutableListOf<Float>()

    println("Statistics computer starting...")

    do {
        print("Yes? ")
        val input = readLine()

        val parsedInput: Float = input?.toFloatOrNull() ?: continue

        enteredFloatNumbers.add(parsedInput)
    } while (input != "quit")

    val count = enteredFloatNumbers.count()

    println(if (count == 0) "Thank you. You didn't enter any numbers." else "Thank you. You gave $count numbers.")

    if (count == 0) {
        printOutput(null, null, null)
        return
    }

    val smallest = enteredFloatNumbers.minOf { fl: Float -> fl }
    val largest = enteredFloatNumbers.maxOf { fl: Float -> fl }
    val sum = enteredFloatNumbers.reduce { rest: Float, fl: Float -> rest + fl }
    val average = sum / count
    printOutput(smallest, largest, average)
}

fun printOutput(min: Float?, max: Float?, average: Float?) {
    println("Their min is       ${min ?: "none"}")
    println("Their max is       ${max ?: "none"}")
    println("Their average is   ${average ?: "none"}")
}

/* Sample run 1
Statistics computer starting...
Yes? quit
Thank you. You didn't enter any numbers.
Their min is       none
Their max is       none
Their average is   none

Process finished with exit code
*/


/* Sample run 2
Statistics computer starting...
Yes? 12
Yes? 12
Yes? quit
Thank you. You gave 2 numbers.
Their min is       12.0
Their max is       12.0
Their average is   12.0

Process finished with exit code 0
*/


/* Sample run 3
Statistics computer starting...
Yes? 5
Yes? 10
Yes? quit
Thank you. You gave 2 numbers.
Their min is       5.0
Their max is       10.0
Their average is   7.5

Process finished with exit code 0
*/





/*

Write a program that reads numbers (integers or floating point numbers) from the console
until the user enters “quit”. Once the user has entered “quit” the program should print out
the smallest number, largest number, the count of numbers and the average of the numbers.

Use readLine() to read a line of input (completed with Enter) as a String. A nice way to implement
reading loop is to use do - while. Do not store the numbers in a collection, it is enough to maintain
average, count, min and max. If you want to, you can maintain sum also, but it is not necessary.
Assign appropriate initial values to these variables. (What is sum before the user has any input?
Perhaps null - so, use optionals).

Write your implementation into Kotlin main function ( fun main() { ... } ). Attach some sample executions
into comments box in your solution (in a way similar to what is shown below).

Hints:
- In Kotlin, you can use toDoubleOrNull() to convert a String to a Double. If the user enters "",
toDoubleOrNull returns null.
- Use the ? and ?: operators to handle the null case.
- If you have read 3 numbers and their average is 6, then the sum of numbers is 6 * 3 = 18
- Develop the program in steps
- You may find it useful to use Kotlin worksheet feature to try out parts of the program

Some sample runs:

Statistics computer starting...
Yes? quit
Thank you. You gave 0 numbers.
Their min is none
Their max is none
Their average is none

Statistics computer starting...
Yes? kukkuu
Yes? trallalaa
Yes? quit
Thank you. You gave 0 numbers.
Their min is none
Their max is none
Their average is none

Statistics computer starting...
Yes? 1
average is now 1.0
Yes? 2.0
average is now 1.5
Yes? 3
average is now 2.0
Yes? 4.000
average is now 2.5
Yes? quit
Thank you. You gave 4 numbers.
Their min is 1.0
Their max is 4.0
Their average is 2.5

*/

// Implement your solution in the main function. Fill in your name and student number below.
