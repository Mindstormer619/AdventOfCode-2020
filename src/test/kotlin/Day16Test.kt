import org.junit.Test
import kotlin.test.assertEquals

class Day16Test {
	private val filename = "day16.input"
	lateinit var day16: Day16

	@Test
	fun `check ticket scanning error rate - test input`() {
		day16 = Day16(TestResources.test + filename)
		assertEquals(71, day16.getTicketErrorRate())
	}

	@Test
	fun `run ticket scanning error rate - actual input`() {
		day16 = Day16(TestResources.main + filename)
		println(day16.getTicketErrorRate())
	}

	@Test
	fun `check field finder - test input 2`() {
		day16 = Day16(TestResources.test + "day16_2.input")
		assertEquals(
			mapOf("class" to 12, "row" to 11, "seat" to 13),
			day16.findTicketFields()
		)
	}

	@Test
	fun `check field finder - test input`() {
		day16 = Day16(TestResources.test + filename)
		assertEquals(
			mapOf("row" to 7, "class" to 1, "seat" to 14),
			day16.findTicketFields()
		)
	}

	@Test
	fun `run field finder - actual input`() {
		day16 = Day16(TestResources.main + filename)
		println(day16.findTicketFields())
		println(day16.getTicketDeparture(day16.findTicketFields()))
	}
}