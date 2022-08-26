// Exercise 2
// Name:        Mohammad Askari
// Student id:  2113562

fun main() {
    val secretLotto = pickNDistinct(1, 40, 7).toList()

    val tries = IntArray(100000){ findLotto(secretLotto)}

    val highest: Int = tries.maxOf { i: Int -> i }
    val lowest: Int = tries.minOf { i: Int -> i }

    println("highest:      $highest")
    println("lowest:       $lowest")

//    playLotto()
}

fun findLotto(secretLotto: List<Int>): Int {
    var tryCount = 0

    fun checkForCorrectNumbers(guess: List<Int>, lotto: List<Int>): Int? {
        tryCount += 1
        return lottoResult(guess, lotto)
    }

    fun findFirstIncorrectSetIndex(distinctList: List<Int>): Int? {
        //  T|T|T|T|T|T|T|X|X|X|X|X...
        //  X|T|T|T|T|T|T|T|X|X|X|X...
        //  X|X|T|T|T|T|T|T|T|X|X|X...
        for (i in 0 until distinctList.count() - 7) {
            val testLotto = distinctList.subList(i, i + 7).toCollection(mutableListOf())
            val res = checkForCorrectNumbers(testLotto, secretLotto)
            if (res == 0) {
                return i
            }
        }
        return null
    }

    var startingLotto: MutableList<Int>
    var firstIncorrectSetIndex: Int?
    do {
        startingLotto = pickNDistinct(1, 40, 40).toMutableList()
        firstIncorrectSetIndex = findFirstIncorrectSetIndex(startingLotto)
    } while (firstIncorrectSetIndex == null)

    println("secretLotto:   \n${secretLotto.joinToString("|")}")
    println("startingLotto: \n${startingLotto.joinToString("|")}")


    val startingSet =
        startingLotto.subList(firstIncorrectSetIndex, firstIncorrectSetIndex + 7).toCollection(mutableListOf())

    startingLotto.subList(firstIncorrectSetIndex, firstIncorrectSetIndex + 7).clear()

    println("zeroSetLotto:\n${startingSet.joinToString("|")}")
    startingLotto.addAll(0, startingSet)
    println("sortedStartingLotto:\n${startingLotto.joinToString("|")}")

    val correctList = mutableListOf<Int>()

    for (i in 1 until startingLotto.count() - 6) {
        val falseLotto = startingLotto.subList(0, 6).toCollection(mutableListOf())
        val testLotto = startingLotto.subList(i + 6, i + 7).toCollection(mutableListOf())
        val res = checkForCorrectNumbers(falseLotto + testLotto, secretLotto) ?: throw Exception("Bad subList")

        if (res == 1) {
            correctList.add(testLotto.last())
        }
    }

    if (correctList.count() == 7) {
        println("Lottery founded:\n${correctList.joinToString("|")}")
    }

    println("tryCount:\n$tryCount\n--------")
    return tryCount

}

    /*
    Write here code that generates lotto guesses and
    uses only function lottoResult (see below) to check the guesses.
    Do not use the content of variable lotto in any other way
    either directly or indirectly.
    Return the number of steps taken to find the correct lotto
    numbers as well as the list of correct numbers as a Pair.
    */
    //    return Pair(0, listOf()) // comment this out when implementing the function

fun pickNextGuess(guessed: Set<Int>, incorrectGuesses: Set<Int>): Int? {
    val options = pickNDistinct(1, 40, 40)
    val leftGuesses = options.filter { !guessed.contains(it) && !incorrectGuesses.contains(it) }

//    println("pickNextGuess options:     ${options.count()}")
//    println("pickNextGuess guessed:     ${guessed.count()}")
//    println("pickNextGuess leftGuesses: ${leftGuesses.count()}")

    return if (leftGuesses.isNotEmpty()) leftGuesses.random() else null
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
        println("Please enter $distinctCount distinct numbers seperated by comma between $low and $high. : ")

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
fun playLotto() {
    println("Lotto game started!")
    val lowest = 1
    val highest = 40
    val distinctCount = 7

    do {
        val generatedSecret = pickNDistinct(lowest, highest, distinctCount)
        println("DEBUG: generatedSecret: ${generatedSecret.joinToString(",")}")

        val guessedSecret = readNDistinct(lowest, highest, distinctCount)
        println("DEBUG: guessedSecret:   ${guessedSecret.joinToString(",")}")

        val correctGuesses = numCommon(generatedSecret.toList(), guessedSecret)
        println("DEBUG: correctGuesses:  $correctGuesses")

        val printLines = "-------------------"
        println(
            when (correctGuesses) {
                7 -> "$printLines\nYOU WON THE LOTTERY, CONGRATULATIONS.\n$printLines"
                6 -> "$printLines\nOh no. So close. you missed only one number, better luck next time.\n$printLines"
                else -> "$printLines\nOh no, you missed only ${distinctCount - correctGuesses} number, better luck next time.\n$printLines"
            }
        )
        println("lotto numbers were: [${generatedSecret.joinToString(", ")}]")

    } while (askForPlayAgain())
}

fun askForPlayAgain(message: String = "Do you want to play again? y/n "): Boolean {
    println(message)

    val input: String = readLine() ?: "n"

    return input == "y"
}

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
