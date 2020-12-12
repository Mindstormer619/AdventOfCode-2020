fun main() {
	val lines = readFileAndProcess("src/main/resources/day06.input") { it }

	val unionSum = sumOfAnyoneAnswers(lines)
	println(unionSum)

	val intersectSum = sumOfEveryoneAnswers(lines)
	println(intersectSum)
}

private fun sumOfAnyoneAnswers(lines: List<String>): Int {
	val questionSet = mutableSetOf<Char>()
	var sum = 0

	for (line in lines) {
		if (line.isEmpty()) {
			sum += questionSet.size
			questionSet.clear()
			continue
		}
		questionSet.addAll(line.asIterable())
	}
	return sum
}

private fun sumOfEveryoneAnswers(lines: List<String>): Int {
	var questionSet = setOf<Char>()
	var sum = 0
	var clearedGroup = true

	for (line in lines) {
		if (line.isEmpty()) {
			sum += questionSet.size
			questionSet = setOf()
			clearedGroup = true
			continue
		}
		if (clearedGroup) {
			questionSet = setOf(*line.toCharArray().toTypedArray())
			clearedGroup = false
		}
		else questionSet = (questionSet intersect line.asIterable())
	}
	return sum
}