import org.junit.Assert.assertEquals
import org.junit.Test

class Day12Test {
	private val filename = "day12.input"

	lateinit var day12: Day12

	@Test
	fun `get Manhattan distance on test input`() {
		day12 = Day12(TestResources.test + filename)
		assertEquals(25, day12.getManhattanDistance())
	}

	@Test
	fun `get Manhattan distance on actual input`() {
		day12 = Day12(TestResources.main + filename)
		println(day12.getManhattanDistance())
	}

	@Test
	fun `get Manhattan distance for waypoint - test`() {
		day12 = Day12(TestResources.test + filename)
		assertEquals(286, day12.getManhattanDistanceWithWayPoint())
	}

	@Test
	fun `calculate Manhattan distance for waypoint - actual input`() {
		day12 = Day12(TestResources.main + filename)
		println(day12.getManhattanDistanceWithWayPoint())
	}

	@Test
	fun `test complex multiplication`() {
		val c: Complex = 15 to 5
		assertEquals(5 to -15, c.multiplyI(-1))
	}
}