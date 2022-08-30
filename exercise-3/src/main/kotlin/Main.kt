// Exercise 3
// Name:        Mohammad Askari
// Student id:  2113562


/*
Create a CoffeeMaker class.

[Done] The class has properties coffeeCapacity and waterCapacity which can be given values when constructing an instance of CoffeeMaker.
[Done] These properties never change during the existence of a CoffeeMaker instance.
[Done] If no values are given, default values 50 and 150 should be used.
[Done] CoffeeMaker has also three properties whose values may change during the lifetime of the CoffeeMaker instance: coffeeAmount, waterAmount, and isOn.
[Done] It should be possible to read and assign the value of isOn outside the class but coffeeAmount and waterAmount can only be read from outside.
[Done] CoffeeMaker should have three methods: makeDoubleEspresso which uses 16 units of coffee and 30 units of water,
[Done] makeRegular which uses 10 units of coffee and 120 units of water,
[Done] and fillAll that tops up the coffee and water amounts to their maximums.
[Done] If the amount of coffee and water is not enough for an espresso or regular coffee, the make-methods should not change the state of the coffee machine at all.
[Done] Consider using a private method to avoid duplicating code and remember to take into account whether the maker is on or not.
Also, override toString method and make it output the CoffeeMaker state like in the example below.
Try to use a Kotlinish way of implementing the coffee maker.
Should you get stuck with that, it is ok to write in more Java style.
Example main function:fun main() {
val cm = CoffeeMaker()
println(cm)    cm.
makeRegular()
println(cm)    cm.
isOn = true
println(cm)    cm.
makeRegular()
println(cm)    cm.
fillAll()
println(cm)    cm.
makeRegular()
println(cm)    cm.
makeDoubleEspresso()
println(cm)    cm.
makeDoubleEspresso()
println(cm)}
Produces this output:
coffeemaker is off with 0 coffee and 0 water
coffeemaker is off with 0 coffee and 0 water
coffeemaker is on with 0 coffee and 0 water
coffeemaker is on with 0 coffee and 0 water
coffeemaker is on with 50 coffee and 150 water made coffee with 10 - 120
coffeemaker is on with 40 coffee and 30 water made coffee with 16 - 30
coffeemaker is on with 24 coffee and 0 water coffeemaker is on with 24 coffee and 0 water
*/

fun main() {
    val coffeeMaker = CoffeeMaker()
    println(coffeeMaker)
    coffeeMaker.makeRegular()
    println(coffeeMaker)
    coffeeMaker.isOn = true
    println(coffeeMaker)
    coffeeMaker.makeRegular()
    println(coffeeMaker)
    coffeeMaker.fillAll()
    println(coffeeMaker)
    coffeeMaker.makeRegular()
    println(coffeeMaker)
    coffeeMaker.makeDoubleEspresso()
    println(coffeeMaker)
    coffeeMaker.makeDoubleEspresso()
    println(coffeeMaker)
}

class CoffeeMaker {
    private val coffeeCapacity: Int
    private val waterCapacity: Int

    var isOn = false
        get() {
            return field
        }
        public set(value: Boolean) {
            if (value != field) {
                field = value
            }
        }

    var coffeeAmount = 0
        get() {
            return field
        }
        private set(value: Int) {
            if (value >= 0) {
                field = value
            }
        }

    var waterAmount = 0
        get() {
            return field
        }
        private set(value: Int) {
            if (value >= 0) {
                field = value
            }
        }

    constructor() {
        this.coffeeCapacity = 50
        this.waterCapacity = 150
    }

    constructor(coffeeCapacity: Int, waterCapacity: Int) {
        this.coffeeCapacity = coffeeCapacity
        this.waterCapacity = waterCapacity
    }

    fun fillAll() {
        this.waterAmount = this.waterCapacity
        this.coffeeAmount = this.coffeeCapacity
    }

    fun makeDoubleEspresso() {
        this.makeCoffee(16, 30)
    }

    fun makeRegular() {
        this.makeCoffee(10, 120)
    }

    private fun makeCoffee(coffeeUsage: Int, waterUsage: Int): Boolean {
        if (!this.isOn) {
            return false
        }
        if (this.waterAmount < waterUsage || this.coffeeAmount < coffeeUsage) {
            return false
        }
        println("made coffee with $coffeeUsage - $waterUsage")

        this.waterAmount -= waterUsage
        this.coffeeAmount -= coffeeUsage
        return true
    }

    public override fun toString(): String {
        return "coffeemaker is ${if (this.isOn) "on" else "off"} with ${this.coffeeAmount} coffee and ${this.waterAmount} water"
    }
}