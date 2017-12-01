/**
 * Assumption to have each Gossip as its own object
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