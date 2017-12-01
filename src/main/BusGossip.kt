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
                return i
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