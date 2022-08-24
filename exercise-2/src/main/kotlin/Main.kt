// Exercise 2
// Name:        Mohammad Askari
// Student id:  2113562

fun main() {
    val secretLotto = pickNDistinct(1, 40, 7).toList()
    println("DEBUG: secretLotto:   ${secretLotto.joinToString(" | ")}")

    findLotto(secretLotto)

//    playLotto()
}

fun findLotto(lotto: List<Int>): Pair<Int, List<Int>> {
    //  7 Distinct number
    //  Each can be between 1 and 40

    //  Have to generate guesses to see if they match lotto
    val startingGuess = pickNDistinct(1, 40, 7).toList()

    // Indexes can be between 0-6 and values can be between 1-40
    val guesses: List<MutableList<Int>> = listOf(
        mutableListOf<Int>(startingGuess[0]),
        mutableListOf<Int>(startingGuess[1]),
        mutableListOf<Int>(startingGuess[2]),
        mutableListOf<Int>(startingGuess[3]),
        mutableListOf<Int>(startingGuess[4]),
        mutableListOf<Int>(startingGuess[5]),
        mutableListOf<Int>(startingGuess[6])
    )

    val correctGuessIndexes = mutableListOf<Int?>(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
    )

    fun getAllGuessedNumbers(): List<Int> {
        return guesses.reduce { acc: List<Int>, ints: List<Int> -> acc + ints }
    }

    fun getLottoGuessSample(): List<Int> {
        return correctGuessIndexes.mapIndexed { index: Int, i: Int? -> if (i == null) guesses[index].last() else guesses[index][i] }
    }

    fun correctLottoNumberCount(): Int {
        return lottoResult(getLottoGuessSample(), lotto) ?: throw Exception("Should not be possible.")
    }

    fun printGuessTable() {
        guesses.forEachIndexed { index, guess ->
            val correctGuessIndex = correctGuessIndexes[index]
            val value: Int? = if (correctGuessIndex != null) guess[correctGuessIndex] else null
            val printLabel = if (value != null) if (value >= 10) value else " $value" else "  "
            val list = guess.map { if (it >= 10) it else " $it" }.joinToString(" | ")

            println(
                "Col $index [$printLabel]:   $list"
            )
        }
    }

    //  Amount of guesses.
    var guessCount = 1

    println("getLottoGuessSample: ${getLottoGuessSample().joinToString(" ")}")
    var correctGuesses = correctLottoNumberCount()

    var indexToFindValueFor = 0

    while (correctGuesses != 7) {

        //  Next possible option by considering other values and also guessed values.

        val guessedNumber: Int = pickNextGuess(getAllGuessedNumbers().toSet())
            ?: throw Exception("Should not be possible to have guessed all the numbers.")

        val correctGuessesBeforeAdding = correctLottoNumberCount()
        guesses[indexToFindValueFor].add(guessedNumber)
        val correctGuessesAfterAdding = correctLottoNumberCount()

        println("indexToFindValueFor:        $indexToFindValueFor")
        println("correctGuessesBeforeAdding: $correctGuessesBeforeAdding")
        println("correctGuessesAfterAdding:  $correctGuessesAfterAdding")
        println("guessedNumber:              $guessedNumber")

        printGuessTable()

        val isPreviousGuessCorrect = correctGuessesAfterAdding < correctGuessesBeforeAdding
        val isCurrentGuessCorrect = correctGuessesAfterAdding > correctGuessesBeforeAdding

        //  Previous guessed value was correct one.
        if (isPreviousGuessCorrect) {
            println("Value at column with index of $indexToFindValueFor was already correct.")
            // Last element
            correctGuessIndexes[indexToFindValueFor] = guesses[indexToFindValueFor].count() - 2
            indexToFindValueFor += 1

        } else if (isCurrentGuessCorrect) {
            println("Added $guessedNumber to column with index of $indexToFindValueFor")
            // Last element
            correctGuessIndexes[indexToFindValueFor] = guesses[indexToFindValueFor].count() - 1

            //  When loop value is the correct one.
            indexToFindValueFor += 1
            correctGuesses = correctGuessesAfterAdding
        }

        guessCount = guessCount.inc()


        if (indexToFindValueFor >= guesses.count() && correctGuesses != 7) {
            throw Exception("Should have founded all numbers by now.")
        }

        println("\n------------\n")
    }
    println("guessCount:            $guessCount")

    /*
    Write here code that generates lotto guesses and
    uses only function lottoResult (see below) to check the guesses.
    Do not use the content of variable lotto in any other way
    either directly or indirectly.
    Return the number of steps taken to find the correct lotto
    numbers as well as the list of correct numbers as a Pair.
    */
    return Pair(0, listOf()) // comment this out when implementing the function
}

fun pickNextGuess(guessed: Set<Int>): Int? {
    val options = pickNDistinct(1, 40, 40)
    val leftGuesses = options.filter { !guessed.contains(it) }

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
fun pickNDistinct(low: Int, high: Int, distinctCount: Int): Set<Int> {
    val includeHighest: Boolean = true

    if (high <= low) {
        throw IllegalArgumentException("high argument should be higher than low.")
    }

    val optionsSize = if (includeHighest) (high - low) + 1 else (high - low)
    val optionsArray = IntArray(optionsSize) { i -> i + low }

    if (optionsSize < distinctCount) {
        throw IllegalArgumentException("Cannot find $distinctCount distinct numbers in $optionsSize options")
    }

    optionsArray.shuffle()

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
