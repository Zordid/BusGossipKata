import org.testng.Assert
import org.testng.annotations.Test

class DriverTest {

    @Test
    fun `a driver knows one inital gossip`() {
        val driver = Driver("Max", emptyList())
        Assert.assertEquals(driver.knowsAbout, setOf(Gossip("Max's gossip")))
    }

    @Test
    fun `hearing about a new gossip adds new gossip`() {
        val d = Driver("Max", emptyList())
        Assert.assertFalse(d.haveYouHeardAboutThis(listOf(Gossip("thisandthat"))))
        Assert.assertEquals(d.knowsAbout.size, 2)
    }

    @Test
    fun `hearing gossip twice is lame`() {
        val d = Driver("Max", emptyList())
        Assert.assertFalse(d.haveYouHeardAboutThis(listOf(Gossip("thisandthat"))))
        Assert.assertEquals(d.knowsAbout.size, 2)
        Assert.assertTrue(d.haveYouHeardAboutThis(listOf(Gossip("thisandthat"))))
        Assert.assertEquals(d.knowsAbout.size, 2)
    }

    @Test
    fun `hearing about old stuff is boring`() {
        val d = Driver("Max", emptyList())
        Assert.assertTrue(d.haveYouHeardAboutThis(listOf(Gossip("Max's gossip"))))
        Assert.assertEquals(d.knowsAbout.size, 1)
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