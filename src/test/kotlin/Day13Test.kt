import org.junit.Ignore
import org.junit.Test
import kotlin.test.assertEquals

class Day13Test {
	private val filename = "day13.input"

	lateinit var day13: Day13

	@Test
	fun `find earliest bus - test input`() {
		day13 = Day13(TestResources.test + filename)
		assertEquals(295, day13.solveEarliestBus())
	}

	@Test
	fun `find earliest bus - actual input`() {
		day13 = Day13(TestResources.main + filename)
		println(day13.solveEarliestBus())
	}

	@Test
	fun `find earliest timestamp for sequence - test input`() {
		day13 = Day13(TestResources.test + filename)
		assertEquals(1068781, day13.solveBusSequence())
	}

	@Test @Ignore
	fun `find earliest timestamp for sequence - actual input`() {
		day13 = Day13(TestResources.main + filename)
		println(day13.solveBusSequence())
	}
}