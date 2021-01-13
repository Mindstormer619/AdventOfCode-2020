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

	@Test
	fun `test matching messages with recursion - test input`() {
		day19 = Day19(TestResources.test + "day19_2.input", true)
		assertEquals(12, day19.countMatchingMessagesWithReplacement())
	}

	@Test
	fun `get matching messages with recursion - actual input`() {
		day19 = Day19(TestResources.main + filename, true)
		println(day19.countMatchingMessagesWithReplacement())
	}
}