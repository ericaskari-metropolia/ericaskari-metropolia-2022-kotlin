// Exercise 2
// Name:        Mohammad Askari
// Student id:  2113562


/*
Example runs (here computer guess (next exercise) is implemented also):
Give numbers separated by commas: 1,,2,3,4,5,6,7
Give numbers separated by commas: 1,2,3,4,5,6,7,8
Give numbers separated by commas: ,1,2,3,4,5,6,7
Give numbers separated by commas: 1,1,2,3,4,5,6
Give numbers separated by commas: 1,2,3,4,5,6,7
lotto numbers: [6, 7, 10, 11, 13, 25, 35], you got 2 correct
computer guess in 30 steps is [6, 7, 10, 11, 13, 25, 35]
More? (Y/N): Y
Give numbers separated by commas: 1,2,3,4,5,6,77
Give numbers separated by commas: 5,2,9,32,17,11,10
lotto numbers: [3, 6, 10, 12, 20, 36, 40], you got 1 correct
computer guess in 43 steps is [3, 6, 10, 12, 20, 36, 40]
More? (Y/N): N
*/

fun main() {
    do {
        playLotto()
    } while (askForPlayAgain())

    println("Running a performance test on findLotto function...")

    // A performance check
    val tests = 100000
    val tries = IntArray(tests) { findLotto(pickNDistinct(1, 40, 7).toList()).first }
    val highest: Int = tries.maxOf { i: Int -> i }
    val lowest: Int = tries.minOf { i: Int -> i }

    println("findLotto function best step count after $tests tests: $lowest")
    println("findLotto function worst step count after $tests tests: $highest")
}


/*
Write here code that generates lotto guesses and
uses only function lottoResult (see below) to check the guesses.
Do not use the content of variable lotto in any other way
either directly or indirectly.
Return the number of steps taken to find the correct lotto
numbers as well as the list of correct numbers as a Pair.
*/
fun findLotto(secretLotto: List<Int>, verbose: Boolean = false): Pair<Int, Set<Int>> {
    // total of times, it checked for correct numbers.
    var tryCount = 0

    //  Set of founded correct numbers.
    val correctList = mutableSetOf<Int>()

    fun debug(input: Any) {
        if (verbose) {
            println(input)
        }
    }

    // just a wrapper to keep count
    fun checkForCorrectNumbers(guess: List<Int>, lotto: List<Int>): Int? {
        tryCount += 1
        return lottoResult(guess, lotto)
    }

    // Finds index in the array which next seven items are incorrect numbers (lottoResult is zero)
    fun findFirstIncorrectSetIndex(distinctList: List<Int>): Int? {
        var lastLoopResult: Int? = null
        for (i in 0 until distinctList.count() - 7) {
            val testLotto = distinctList.subList(i, i + 7).toCollection(mutableListOf())
            val res = checkForCorrectNumbers(testLotto, secretLotto)

            if (lastLoopResult != null && lastLoopResult == 1 && res == 0) {
                correctList.add(distinctList[i - 1])
            }

            if (res == 0) {
                return i
            }
            lastLoopResult = res
        }
        return null
    }

    // Starting array 1-40 shuffled.
    var startingLotto: MutableList<Int>
    // Index if first 7 incorrect numbers
    var firstIncorrectSetIndex: Int?

    do {
        startingLotto = pickNDistinct(1, 40, 40).toMutableList()
        firstIncorrectSetIndex = findFirstIncorrectSetIndex(startingLotto)
        //  Since it's possible that correct numbers are placed everywhere, we can shuffle the array
        //  And keep looking.
    } while (firstIncorrectSetIndex == null)

    //  Debugging
    debug("secretLotto:   \n${secretLotto.joinToString("|")}")
    //  Debugging
    debug("startingLotto: \n${startingLotto.joinToString("|")}")

    // Cloning 7 incorrect numbers as a list.
    val startingSet =
        startingLotto.subList(firstIncorrectSetIndex, firstIncorrectSetIndex + 7).toCollection(mutableListOf())

    // Removing these 7 incorrect numbers from the main list.
    startingLotto.subList(firstIncorrectSetIndex, firstIncorrectSetIndex + 7).clear()

    debug("zeroSetLotto:\n${startingSet.joinToString("|")}")

    // Adding these 7 incorrect numbers to the beginning of the main list.
    startingLotto.addAll(0, startingSet)

    debug("sortedStartingLotto:\n${startingLotto.joinToString("|")}")


    //  Creating cloned sublist of 6 incorrect numbers
    val falseLotto = startingLotto.subList(0, 6).toCollection(mutableListOf())

    //  Creating cloned sublist of unknown numbers (index of seven is also incorrect.)
    val unknownLotto = startingLotto.subList(8, 40).toCollection(mutableListOf()).filter { !correctList.contains(it) }

    //  Looping the list of unknown numbers which holds 1-40 shuffled numbers.
    for (i in 1 until unknownLotto.count()) {

        //  Creating sublist of 1 unknown number which will loop through the list.
        val testLotto = unknownLotto.subList(i, i + 1).toCollection(mutableListOf())

        //  Mixing 6 incorrect numbers and one unknown number and Checking for correct number count.
        val res = checkForCorrectNumbers(falseLotto + testLotto, secretLotto) ?: throw Exception("Bad subList")

        //  If result is "1" then 7th number is a correct number.
        if (res == 1) {
            correctList.add(testLotto.last())
        }

        //  And if we have all the numbers then there is no need to loop till the end.
        if (correctList.count() == 7) {
            break
        }
    }

    if (correctList.count() == 7) {
        debug("Lottery founded:\n${correctList.joinToString("|")}")
    }

    debug("tryCount:\n$tryCount\n--------")

    return Pair(tryCount, correctList)
}

fun lottoResult(guess: List<Int>, lotto: List<Int>) =
    if (numDistinct(guess) == 7 && numDistinct(lotto) == 7 && (guess + lotto).all { it in (1..40) }) {
        numCommon(guess, lotto)
    } else {
        null
    }


/*
Write implementations of the functions into a Kotlin file. You may find
it useful to try out the functions in a Kotlin worksheet.
*/

/*
Write function pickNumber() that has two Int parameters: low and high
and returns a random number between low and high (inclusive). You may
assume low <= high. Default value for low and high should be 1 and 40.
Hint: consider making a range and picking a random number from it.
Example runs:
println(pickNumber()) // a number in 1..40
println(pickNumber(0,1)) // a number in 0..1
println(pickNumber(1,6)) // a number in 1..6
*/

fun pickNumber(low: Int = 1, high: Int = 40): Int {
    val optionsArray = IntArray((high - low) + 1) { i -> i + low }
    return optionsArray.random()
}

/*
Write function pickNDistinct(low: Int, high: Int, n: Int) that
returns n distinct random numbers between low and high (inclusive).
You may assume low <= high and n <= number of distinct values.
Return the values as List of Int values sorted from smallest to greatest.
Example runs:
println(pickNDistinct(1,40,7)) // for example [3, 6, 11, 17, 19, 21, 34]
println(pickNDistinct(1,6,3)) // for example [1, 3, 6]
println(pickNDistinct(1,6,6)) // [1, 2, 3, 4, 5, 6]
*/
fun pickNDistinct(low: Int, high: Int, distinctCount: Int, shuffle: Boolean = true): Set<Int> {
    val includeHighest: Boolean = true

    if (high <= low) {
        throw IllegalArgumentException("high argument should be higher than low.")
    }

    val optionsSize = if (includeHighest) (high - low) + 1 else (high - low)
    val optionsArray = IntArray(optionsSize) { i -> i + low }

    if (optionsSize < distinctCount) {
        throw IllegalArgumentException("Cannot find $distinctCount distinct numbers in $optionsSize options")
    }

    if (shuffle) {
        optionsArray.shuffle()
    }

    return optionsArray.slice(0 until distinctCount).toSet()
}


/*
Write function numDistinct(list: List<Int>) that returns the
number of distinct elements in list.
Hint: consider conversion to set.
Example runs:
println(numDistinct(listOf(1,1,1,2,3,4,5,6,6,6))) // 6
println(numDistinct(pickNDistinct(1,40,7))) // 7
*/
fun numDistinct(list: List<Int>): Int {
    return list.toSet().count()
}


/*
Write function numCommon(list1: List<Int>, list2: List<Int>) that returns
the number of elements in both list1 and list2. Multiple occurences should
be counted only once.
Example runs:
println(numCommon(listOf(1,2,3,4), listOf(2,4))) // 2
println(numCommon(listOf(1, 1, 1, 2, 3), listOf(1, 2))) // 2
println(numCommon(listOf(4, 3, 2), listOf(1, 4, 1, 1, 2, 3))) // 3
*/
fun numCommon(list1: List<Int>, list2: List<Int>): Int {
    return list1.toSet().intersect(list2.toSet()).count()
}


/*
Write function readNDistinct(low: Int, high: Int, n: Int): List<Int> that reads from console a line
that contains n distinct integer numbers ranging from low and high (inclusive), separated by commas.
You may assume low <= high and n <= number of distinct values.
Hints: use readLine(), .split(), check .toIntOrNull(), .filterNotNull() and .all { ... }
*/
fun readNDistinct(low: Int, high: Int, distinctCount: Int): List<Int> {
    do {
        print("Please enter $distinctCount distinct numbers seperated by comma between $low and $high : ")

        val input: String = readLine() ?: ""

        val inputItems = input.split(",").map { it.trim() }

        val parsedInput: List<Int?> = inputItems.map { it.toIntOrNull() }

        val isAllNumbers = parsedInput.all { it != null }

        if (!isAllNumbers) {
            continue
        }

        if (parsedInput.filterNotNull().count() != distinctCount) {
            println("There should be only $distinctCount numbers.")
            continue
        }

        val parsedFilteredInput: List<Int> = parsedInput.filterNotNull().toSet().toList()

        if (parsedFilteredInput.minOf { i: Int -> i } < low) {
            println("Numbers cannot be smaller than $low")
            continue
        }

        if (parsedFilteredInput.maxOf { i: Int -> i } > high) {
            println("Numbers cannot be higher than $high")
            continue
        }

        if (parsedFilteredInput.count() < distinctCount) {
            println("Numbers should be unique.")
            continue
        }

        return parsedFilteredInput
    } while (true)
}


/*
Write function playLotto() that
- generates (secret) lotto numbers (7 distinct Ints in range from 1 to 40 (inclusive)).
- reads from the console user guess using readNDistinct() function
- prints the number of correctly guessed numbers (use numCommon() for this)
- lets user either continue with another round or end
- call your playLotto implementation from main function
*/
fun playLotto(verbose: Boolean = false) {
    val lowest = 1
    val highest = 40
    val distinctCount = 7

    fun debug(input: Any) {
        if (verbose) {
            println(input)
        }
    }

    println("Lotto game started!")

    val generatedSecret = pickNDistinct(lowest, highest, distinctCount)
    debug("DEBUG: generatedSecret: ${generatedSecret.joinToString(",")}")

    val guessedSecret = readNDistinct(lowest, highest, distinctCount)
    debug("DEBUG: guessedSecret:   ${guessedSecret.joinToString(",")}")

    val correctGuesses = numCommon(generatedSecret.toList(), guessedSecret)
    debug("DEBUG: correctGuesses:  $correctGuesses")

    println("lotto numbers were: [${generatedSecret.joinToString(", ")}]you got $correctGuesses numbers correct.")

    val result = findLotto(generatedSecret.toList())

    println("computer guess in ${result.first} steps is [${result.second.joinToString(", ")}]")
}

fun askForPlayAgain(message: String = "Do you want to play again? y/n "): Boolean {
    println(message)

    val input: String = readLine() ?: "n"

    return input == "y"
}

