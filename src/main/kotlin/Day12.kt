fun main() {
	val directions = readFileAndProcess("src/main/resources/day12.input") {
		Turtle(it[0], Integer.parseInt(it.substring(1)))
	}

	TODO()
}

data class Turtle (
	val direction: Char,
	val units: Int
)