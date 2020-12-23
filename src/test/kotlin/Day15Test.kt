import org.junit.Test
import kotlin.test.assertEquals

class Day15Test {
	lateinit var day15: Day15

	private val filename = "day15.input"
	@Test
	fun `test 2020th number in memory game - test input`() {
		day15 = Day15(TestResources.test + filename)
		assertEquals(436, day15.playMemoryGame())
	}

	@Test
	fun `test 2020th number in memory game - test input 2`() {
		day15 = Day15(TestResources.test + "day15_2.input")
		assertEquals(1836, day15.playMemoryGame())
	}

	@Test
	fun `run 2020th number in memory game - actual input`() {
		day15 = Day15(TestResources.main + filename)
		println(day15.playMemoryGame())
	}

	@Test
	fun `test 30000000th number - test input`() {
		day15 = Day15(TestResources.test + filename)
		assertEquals(175594, day15.playMemoryGame(30000000))
	}

	@Test
	fun `run 30000000th number - actual input`() {
		day15 = Day15(TestResources.main + filename)
		println(day15.playMemoryGame(30000000))
	}
}