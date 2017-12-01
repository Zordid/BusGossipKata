import org.testng.Assert
import org.testng.annotations.Test

class SimulationTest {

    @Test
    fun `example 1`() {
        val w = Simulation(listOf(
                Driver("Alfons", listOf(3, 1, 2, 3)),
                Driver("Bernd", listOf(3, 2, 3, 1)),
                Driver("Chris", listOf(4, 2, 3, 4, 5))
        ))

        Assert.assertEquals(w.run(), 4)
    }

    @Test
    fun `example 2`() {
        val w = Simulation(listOf(
                Driver("Arne", listOf(2, 1, 2)),
                Driver("Bruno", listOf(5, 2, 8))
        ))

        Assert.assertEquals(w.run(), -1)
    }

}