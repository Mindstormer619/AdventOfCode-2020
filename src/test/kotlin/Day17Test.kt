import org.junit.Assert.assertEquals
import org.junit.Test

class Day17Test {
	private val filename = "day17.input"
	lateinit var day17: Day17


	@Test
	fun `find active state after 6 cycles - test input`() {
		day17 = Day17(TestResources.test + filename)
		assertEquals(112, day17.countActive())
	}

	@Test
	fun `find active state after 6 cycles - actual input`() {
		day17 = Day17(TestResources.main + filename)
		println(day17.countActive())
	}

	@Test
	fun `find 4d active state after 6 cycles - test input`() {
		day17 = Day17(TestResources.test + filename)
		assertEquals(848, day17.countActive(fourD = true))
	}

	@Test
	fun `find 4d active state after 6 cycles - actual input`() {
		day17 = Day17(TestResources.main + filename)
		println(day17.countActive(fourD = true))
	}
}