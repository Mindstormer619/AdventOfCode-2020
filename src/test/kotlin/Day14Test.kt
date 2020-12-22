import Day14.Companion.mask
import org.junit.Test
import kotlin.test.assertEquals

class Day14Test {
	lateinit var day14: Day14

	private val filename = "day14.input"

	@Suppress("SpellCheckingInspection")
	private val testMask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"

	@Test
	fun `find memory sum - test input`() {
		day14 = Day14(TestResources.test + filename)
		assertEquals(51L, day14.findMemorySum())
	}

	@Test
	fun `when testMask is applied on 11, we get 73`() {
		assertEquals(73L, 11L mask testMask)
	}

	@Test
	fun `when testMask is applied on 101, we get 101`() {
		assertEquals(101L, 101L mask testMask)
	}

	@Test
	fun `when testMask is applied on 0, we get 64`() {
		assertEquals(64L, 0L mask testMask)
	}

	@Test
	fun `find memory sum - actual input`() {
		day14 = Day14(TestResources.main + filename)
		println(day14.findMemorySum())
	}

	@Test
	fun `find memory sum on memMask - testInput`() {
		day14 = Day14(TestResources.test + filename)
		assertEquals(208L, day14.findMemorySumOnMemMask())
	}

	@Test
	fun `find memory sum on memMask - actual input`() {
		day14 = Day14(TestResources.main + filename)
		println(day14.findMemorySumOnMemMask())
	}
}