/**
 * Assumption to have each Gossip as its own object.
 */
data class Gossip(val name: String)

/**
 * A bus driver in the game.
 */
data class Driver(val name: String,
                  private val route: List<Int>,
                  private val gossips: Set<Gossip> = setOf(Gossip("$name's gossip"))) {

    val knowsAbout = mutableSetOf<Gossip>()
    val whereAreYou: Int
        get() = route[index % route.size]

    private var index = 0

    init {
        knowsAbout.addAll(gossips)
    }

    fun haveYouHeardAboutThis(gossips: Collection<Gossip>): Boolean {
        if (knowsAbout.containsAll(gossips)) return true
        knowsAbout.addAll(gossips)
        return false
    }

    fun drive() {
        index++
    }

}

/**
 * The simulation of a gossipy day for the given drivers.
 */
class Simulation(private val drivers: Collection<Driver>) {

    companion object {
        fun createFromString(definition: String): Simulation {
            var count = 0
            val myDrivers = definition.split(Regex("\\n")).map {
                Driver("Driver ${(++count)}", it.trim().split(Regex("\\s+")).map { it.toInt() })
            }

            return Simulation(myDrivers)
        }
    }

    private val everyGossipInTheWorld: Set<Gossip> by lazy {
        val gossip = mutableSetOf<Gossip>()
        drivers.forEach { gossip.addAll(it.knowsAbout) }
        gossip
    }

    fun run(workingHours: Int = 8): Int {

        for (i in 0 until workingHours * 60) {

            println("--- The time is $i ---")

            // where is everybody
            val positions: MutableMap<Int, MutableSet<Driver>> = mutableMapOf()
            for (d in drivers) {
                if (!positions.containsKey(d.whereAreYou))
                    positions[d.whereAreYou] = mutableSetOf(d)
                else
                    positions[d.whereAreYou]?.add(d)
            }

            // exchange gossip
            for (meeting in positions.values.filter { it.size > 1 }) {
                println("${meeting.map { it.name }} meet...")
                val allGossip = mutableSetOf<Gossip>()
                for (d in meeting) allGossip.addAll(d.knowsAbout)

                for (d in meeting) {
                    d.haveYouHeardAboutThis(allGossip)
                }
            }


            // move on
            drivers.forEach {
                it.drive()
            }

            if (everybodyKnowsEverything()) {
                println("Everybody is informed at i=$i")
                return i+1
            }

        }
        println("Day over. Not everybody is informed well...")
        return -1
    }

    private fun everybodyKnowsEverything(): Boolean {
        for (d in drivers) {
            if (d.knowsAbout != everyGossipInTheWorld)
                return false
        }
        return true
    }

}

fun main(args: Array<String>) {

    val input = "  12 23 15 2 8 20 21 3 23 3 27 20 0\n" +
            "    21 14 8 20 10 0 23 3 24 23 0 19 14 12 10 9 12 12 11 6 27 5\n" +
            "    8 18 27 10 11 22 29 23 14\n" +
            "    13 7 14 1 9 14 16 12 0 10 13 19 16 17\n" +
            "    24 25 21 4 6 19 1 3 26 11 22 28 14 14 27 7 20 8 7 4 1 8 10 18 21\n" +
            "    13 20 26 22 6 5 6 23 26 2 21 16 26 24\n" +
            "    6 7 17 2 22 23 21\n" +
            "    23 14 22 28 10 23 7 21 3 20 24 23 8 8 21 13 15 6 9 17 27 17 13 14\n" +
            "    23 13 1 15 5 16 7 26 22 29 17 3 14 16 16 18 6 10 3 14 10 17 27 25\n" +
            "    25 28 5 21 8 10 27 21 23 28 7 20 6 6 9 29 27 26 24 3 12 10 21 10 12 17\n" +
            "    26 22 26 13 10 19 3 15 2 3 25 29 25 19 19 24 1 26 22 10 17 19 28 11 22 2 13\n" +
            "    8 4 25 15 20 9 11 3 19\n" +
            "    24 29 4 17 2 0 8 19 11 28 13 4 16 5 15 25 16 5 6 1 0 19 7 4 6\n" +
            "    16 25 15 17 20 27 1 11 1 18 14 23 27 25 26 17 1"

    val w = Simulation.createFromString(input)
    w.run()

}
