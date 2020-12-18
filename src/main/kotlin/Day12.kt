import kotlin.math.abs

infix fun Int.mod(other: Int) = Math.floorMod(this, other)

class Day12(inputFile: String) {
	private val turtles: List<Turtle> = readFileAndProcess(inputFile) {
		Turtle(it[0], Integer.parseInt(it.substring(1)))
	}

	private val directions = listOf('E', 'S', 'W', 'N')

	fun getManhattanDistance(): Int {
		val directions = directions
		var forwardIndex = 0

		var coords = 0 to 0

		for (turtle in turtles) {
			when (turtle.direction) {
				'L' -> forwardIndex = (forwardIndex - (turtle.value/90)) mod 4
				'R' -> forwardIndex = (forwardIndex + (turtle.value/90)) mod 4
				'F' -> coords = Move.valueOf(directions[forwardIndex].toString()).transform(turtle.value, coords)
				else -> coords = Move.valueOf(turtle.direction.toString()).transform(turtle.value, coords)
			}
		}

		println(coords)
		return abs(coords.first) + abs(coords.second)
	}

	fun getManhattanDistanceWithWayPoint(): Long {
		var waypoint = 10 to 1
		var coords = 0L to 0L

		for (turtle in turtles) {
			when (turtle.direction) {
				'L' -> waypoint = waypoint multiplyI (turtle.value/90)
				'R' -> waypoint = waypoint multiplyI (-turtle.value/90)
				'F' -> {
					coords =
						coords.first + (waypoint.first * turtle.value.toLong()) to
						coords.second + (waypoint.second * turtle.value.toLong())
				}
				else -> waypoint = Move.valueOf(turtle.direction.toString()).transform(turtle.value, waypoint)
			}
		}

		println(coords)
		return abs(coords.first) + abs(coords.second)
	}
}

// * ki

typealias Complex = Pair<Int, Int>

/*
i * i = -1
k * i = ki
(a + bi) * i = -b + ai
 */
infix fun Complex.multiplyI(power: Int): Complex {
	val rotates = power mod 4
	var result = this
	for (i in 1..rotates) {
		result = -result.second to result.first
	}
	return result
}

enum class Move(val transform: (Int, Pair<Int, Int>) -> Pair<Int, Int>) {
	N({value: Int, c: Pair<Int, Int> -> c.first to c.second+value}),
	S({value: Int, c: Pair<Int, Int> -> c.first to c.second-value}),
	E({value: Int, c: Pair<Int, Int> -> c.first+value to c.second}),
	W({value: Int, c: Pair<Int, Int> -> c.first-value to c.second})
}

data class Turtle(
	val direction: Char,
	val value: Int
)