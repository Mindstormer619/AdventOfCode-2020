private val ryan = Regex("""(\d+)-(\d+) ([a-z]): ([a-z]+)""")

data class Components (
	val min: Int,
	val max: Int,
	val letter: Char,
	val password: String
)

private fun getComponents(input: String): Components {
	val groups = ryan.matchEntire(input)!!.groupValues

	return Components(
		groups[1].toInt(),
		groups[2].toInt(),
		groups[3][0],
		groups[4]
	)
}

fun main() {
	val input = readFileAndProcess("src/main/resources/day02.input") {getComponents(it)}

	val solution = findValidPasswords(input)
	println(solution)

	val solution2 = findValidPasswords2(input)
	println(solution2)
}

fun findValidPasswords(input: List<Components>): Int {
	var numOfValid = 0
	for (c in input) {
		val count = c.password.count { it == c.letter }
		if (count >= c.min && count <= c.max) numOfValid++
	}
	return numOfValid
}

fun findValidPasswords2(input: List<Components>): Int {
	var numOfValid = 0
	for (c in input) {
		val char1 = c.password[c.min - 1]
		val char2 = c.password[c.max - 1]

		if ((char1 == c.letter && char2 != c.letter) || (char1 != c.letter && char2 == c.letter))
			numOfValid++
	}

	return numOfValid
}
