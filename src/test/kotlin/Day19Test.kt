import org.junit.Assert.*
import org.junit.Test

class Day19Test {
	private val filename = "day19.input"
	private lateinit var day19: Day19

	@Test
	fun `test matching messages - test input`() {
		day19 = Day19(TestResources.test + filename)
		assertEquals(2, day19.countMatchingMessages())
	}

	@Test
	fun `get matching messages - actual input`() {
		day19 = Day19(TestResources.main + filename)
		println(day19.countMatchingMessages())
	}
}