
fun day10() {
	val jolts = readFileAndProcess("src/main/resources/day10.input", Integer::parseInt).sorted().toMutableList()
	jolts += jolts.last() + 3
	println(jolts)

	val result = computeJolts(jolts)
	println(result)

	val numOfArrangements = jolts.arrangements()
	println(numOfArrangements)
}

private fun computeJolts(sortedJolts: List<Int>): Long {
	val diffCounts = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
	for (i in sortedJolts.indices) {
		val joltDiff = sortedJolts[i] - (if (i > 0) sortedJolts[i - 1] else 0)
		diffCounts.merge(joltDiff, 1) { c, _ -> c+1}
	}
	println(diffCounts)
	return diffCounts[1]!!.toLong() * diffCounts[3]!!
}

fun List<Int>.arrangements(i: Int = -1, memo: LongArray = LongArray(size){-1L}): Long {
	if (i > -1 && memo[i] != -1L) return memo[i]

	val current = getOrNull(i) ?: 0
	if (current == last()) {
		if (i > -1) memo[i] = 1
		return memo[i]
	}

	val finalIndex = if (i+3 > lastIndex) lastIndex else i+3
	val elementsToCheck = subList(i + 1, finalIndex + 1)
	var arrangements = 0L
	if (current+1 in elementsToCheck) {
		arrangements += arrangements(indexOf(current+1), memo)
	}
	if (current+2 in elementsToCheck) {
		arrangements += arrangements(indexOf(current+2), memo)
	}
	if (current+3 in elementsToCheck) {
		arrangements += arrangements(indexOf(current+3), memo)
	}

	if (i > -1) memo[i] = arrangements
	return arrangements
}
