package machine

class CoffeeMachine(
    private var water: Int = 400,
    private var milk: Int = 540,
    private var coffeeBeans: Int = 120,
    private var disposableCups: Int = 9,
    private var money: Int = 550
) {
    private var state: State = State.IDLE
        set(value) {
            if (value == State.IDLE) {
                println()
                println("Write action (buy, fill, take, remaining, exit): ")
            }
            field = value
        }
    enum class State {
        IDLE,
        CHOOSING_COFFEE,
        FILLING,
        FILLING_WATER,
        FILLING_MILK,
        FILLING_COFFEE_BEANS
    }

    init {
        println("Write action (buy, fill, take, remaining, exit): ")
    }

    fun processInput(input: String) {
        println()
        when (state) {
            State.IDLE -> handleIdleState(input)
            State.CHOOSING_COFFEE -> handleChoosingCoffeeState(input)
            State.FILLING, State.FILLING_WATER, State.FILLING_MILK, State.FILLING_COFFEE_BEANS -> handleFillingState(input)
        }
    }

    private fun handleIdleState(input: String) {
        when (input) {
            "buy" -> {
                state = State.CHOOSING_COFFEE
                println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
            }
            "fill" -> {
                state = State.FILLING
                println("Write how many ml of water you want to add:")
            }
            "take" -> {
                println("I gave you $$money")
                money = 0
                state = State.IDLE
            }
            "remaining" -> {
                printStatus()
                state = State.IDLE
            }
            "exit" -> return
        }
    }

    private fun handleChoosingCoffeeState(input: String) {
        when (input) {
            "1" -> buyCoffee(250, 0, 16, 4)
            "2" -> buyCoffee(350, 75, 20, 7)
            "3" -> buyCoffee(200, 100, 12, 6)
            "back" -> state = State.IDLE
        }
    }

    private fun handleFillingState(input: String) {
        when (state) {
            State.FILLING -> {
                water += input.toInt()
                state = State.FILLING_WATER
                println("Write how many ml of milk you want to add:")
            }
            State.FILLING_WATER -> {
                milk += input.toInt()
                state = State.FILLING_MILK
                println("Write how many grams of coffee beans you want to add:")
            }
            State.FILLING_MILK -> {
                coffeeBeans += input.toInt()
                state = State.FILLING_COFFEE_BEANS
                println("Write how many disposable cups you want to add:")
            }
            State.FILLING_COFFEE_BEANS -> {
                disposableCups += input.toInt()
                state = State.IDLE
            }
        }
    }

    private fun buyCoffee(waterNeeded: Int, milkNeeded: Int, coffeeBeansNeeded: Int, cost: Int) {
        if (water >= waterNeeded && milk >= milkNeeded && coffeeBeans >= coffeeBeansNeeded && disposableCups >= 1) {
            println("I have enough resources, making you a coffee!")
            water -= waterNeeded
            milk -= milkNeeded
            coffeeBeans -= coffeeBeansNeeded
            disposableCups--
            money += cost
        } else {
            println("Sorry, not enough resources!")
        }
        state = State.IDLE
    }

    private fun printStatus() {
        println("The coffee machine has:")
        println("$water ml of water")
        println("$milk ml of milk")
        println("$coffeeBeans g of coffee beans")
        println("$disposableCups disposable cups")
        println("$$money of money")
    }
}
    

fun main() {
    val coffeeMachine = CoffeeMachine()

    while (true) {
        print("> ")
        val input = readln()
        if (input == "exit") break
        coffeeMachine.processInput(input)
    }
}
