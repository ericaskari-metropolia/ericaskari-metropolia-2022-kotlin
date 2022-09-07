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
        set(value) {
            if (value != field) {
                field = value
            }
        }

    var coffeeAmount = 0
        private set(value) {
            if (value >= 0) {
                field = value
            }
        }

    var waterAmount = 0
        private set(value) {
            if (value >= 0) {
                field = value
            }
        }

    constructor() {
        this.coffeeCapacity = 50
        this.waterCapacity = 150
    }

    constructor(coffeeCapacity: Int, waterCapacity: Int) {
        this.coffeeCapacity = if (coffeeCapacity > 0) coffeeCapacity else 0
        this.waterCapacity = if (waterCapacity > 0) waterCapacity else 0
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

    override fun toString(): String {
        return "coffeemaker is ${if (this.isOn) "on" else "off"} with ${this.coffeeAmount} coffee and ${this.waterAmount} water"
    }
}