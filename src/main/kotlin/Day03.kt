fun day03() {
	val lines = readFileAndProcess("src/main/resources/day03.input") {it}

	val solution = solve(lines)
	println(solution)

	val r1d1 = solve(lines, 1, 1)
	val r3d1 = solve(lines, 3, 1)
	val r5d1 = solve(lines, 5, 1)
	val r7d1 = solve(lines, 7, 1)
	val r1d2 = solve(lines, 1, 2)

	assert(r3d1 == 262)

	val solution2 = r1d1.toLong() * r3d1 * r5d1 * r7d1 * r1d2
	println(solution2)
}

private fun solve(input: List<String>, right: Int = 3, down: Int = 1): Int {
	/*
	WIDTH: 0 -> WIDTH-1
	horizontalPosition: Int
		horizontalPosition += 3
		horizontalPosition %= WIDTH
	 */

	val width = input[0].length
	var treesEncountered = 0

	var horizontalPosition = 0

	for (row in input.indices step down) {
		val treeLine = input[row]
		if (treeLine[horizontalPosition] == '#') treesEncountered++
		horizontalPosition += right
		horizontalPosition %= width
	}

	return treesEncountered
}