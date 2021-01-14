import org.junit.Test
import kotlin.test.assertEquals

class Day20Test {
	private val filename = "day20.input"
	lateinit var day20: Day20

	@Test
	fun `test corner product - test input`() {
		day20 = Day20(TestResources.test + filename)
		assertEquals(20899048083289, day20.getCornerProduct())
	}

	@Test
	fun `get corner product - main input`() {
		day20 = Day20(TestResources.main + filename)
		println(day20.getCornerProduct())
	}
}