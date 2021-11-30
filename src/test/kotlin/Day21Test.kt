import org.junit.Test
import kotlin.test.assertEquals

class Day21Test {
	lateinit var solver: Day21

	private val filename = "day21.input"

	@Test
	fun `basic food list from example has 5 safe ingredients`() {
		solver = Day21(TestResources.test + filename)

		assertEquals(5, solver.getOccurrencesOfSafeIngredients())
	}

	@Test
	fun `given a test file, we get the ingredients in a data structure`() {
		solver = Day21(TestResources.test + filename)

		assertEquals(4, solver.input.size)
		assert("fvjkl" in solver.input[2].ingredients)
		assert("fish" in solver.input.last().allergens)
	}

	@Test
	fun `solve part 1`() {
		solver = Day21(TestResources.main + filename)
		println(solver.getOccurrencesOfSafeIngredients())
	}

	// --------- Part 2 ---------

	@Test
	fun `canonical list for example file`() {
		solver = Day21(TestResources.test + filename)

		assertEquals("mxmxvkd,sqjhc,fvjkl", solver.getCanonicalDangerousList())
	}

	@Test
	fun `canonical list for actual file`() {
		solver = Day21(TestResources.main + filename)

		println(solver.getCanonicalDangerousList())
	}
}