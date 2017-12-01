import org.testng.Assert
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class DriverTest {

    lateinit var driver1: Driver
    lateinit var driver2: Driver

    @BeforeMethod
    fun init() {
        driver1 = Driver("Max", listOf(1, 2, 3))
        driver2 = Driver("Moritz", listOf(2, 1, 3))
    }

    @Test
    fun `a driver knows one inital gossip`() {
        Assert.assertEquals(driver1.knowsGossipsFrom, setOf(driver1))
    }

    @Test
    fun `hearing about a new gossip adds new gossip`() {
        Assert.assertFalse(driver1.haveYouHeardAboutGossipsFrom(listOf(driver2)))
        Assert.assertEquals(driver1.knowsGossipsFrom.size, 2)
    }

    @Test
    fun `hearing gossip twice is lame`() {
        Assert.assertFalse(driver1.haveYouHeardAboutGossipsFrom(listOf(driver2)))
        Assert.assertEquals(driver1.knowsGossipsFrom.size, 2)
        Assert.assertTrue(driver1.haveYouHeardAboutGossipsFrom(listOf(driver2)))
        Assert.assertEquals(driver1.knowsGossipsFrom.size, 2)
    }

    @Test
    fun `hearing about old stuff is boring`() {
        Assert.assertTrue(driver1.haveYouHeardAboutGossipsFrom(listOf(driver1)))
        Assert.assertEquals(driver1.knowsGossipsFrom.size, 1)
    }

    @Test
    fun `route works`() {
        val d = Driver("Kreis", listOf(1, 2, 3))
        Assert.assertEquals(d.whereAreYou, 1)
        d.drive()
        Assert.assertEquals(d.whereAreYou, 2)
        d.drive()
        Assert.assertEquals(d.whereAreYou, 3)
        d.drive()
        Assert.assertEquals(d.whereAreYou, 1)
        d.drive()
        Assert.assertEquals(d.whereAreYou, 2)
        d.drive()
        Assert.assertEquals(d.whereAreYou, 3)

    }

}