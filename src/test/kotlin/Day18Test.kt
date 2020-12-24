import org.junit.Assert.*
import org.junit.Test

class Day18Test {
	private val filename = "day18.input"
	lateinit var day18: Day18

	@Test
	fun `test line evaluation`() {
		day18 = Day18(TestResources.test + filename)
		assertEquals(12240, day18.evaluate("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"))
	}

	@Test
	fun `check sum of homework lines - test input`() {
		day18 = Day18(TestResources.test + filename)
		assertEquals(
			26L + 437 + 12240 + 13632,
			day18.sumOfHomework()
		)
	}

	@Test
	fun `find sum of homework lines - actual input`() {
		day18 = Day18(TestResources.main + filename)
		println(day18.sumOfHomework())
	}

	@Test
	fun `test eval advanced`() {
		day18 = Day18(TestResources.test + filename)

		val checks = listOf(
			46L to "2 * 3 + (4 * 5)",
			23340L to "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2",
			90L to "2 * 3 * 4 + 5 + 6",
			120L to "2 * 2 * 3 + 1 + 4 + 2 * 3",
			40L to "(2 * 3 * 5 + 1) + 4"
		)

		for (c in checks) {
			assertEquals(c.first, day18.evaluateAdvanced(c.second))
		}
	}

	@Test
	fun `check sum of advanced homework - test input`() {
		day18 = Day18(TestResources.test + filename)
		assertEquals(
			46L + 1445 + 669060 + 23340,
			day18.sumOfHomeworkAdvanced()
		)
	}

	@Test
	fun `find sum of advanced homework - actual input`() {
		day18 = Day18(TestResources.main + filename)
		println(day18.sumOfHomeworkAdvanced())
	}
}