const val preamble = 25

fun day09() {
	val numbers = readFileAndProcess("src/main/resources/day09.input") { it.toLong() }

	val brokenNumber = findBrokenNumber(numbers)
	println(brokenNumber)

	val smallPlusLarge = findEncryptionWeakness(numbers, brokenNumber)
	println(smallPlusLarge)
}

fun findEncryptionWeakness(numbers: List<Long>, brokenNumber: Long): Long {
	var start = 0
	var end = 1
	var runningTotal = numbers[start] + numbers[end]

	while (end < numbers.size) {
		when {
			runningTotal == brokenNumber -> {
				val contiguousList = numbers.subList(start, end+1)
				return contiguousList.maxOf { it } + contiguousList.minOf { it }
			}
			runningTotal > brokenNumber -> {
				runningTotal -= numbers[start]
				start++
			}
			runningTotal < brokenNumber -> {
				end++
				runningTotal += numbers[end]
			}
		}
	}
	throw IllegalStateException()
}

fun findBrokenNumber(numbers: List<Long>): Long {
	var compareSet: Set<Long>

	nextNumber@for (i in preamble until numbers.size) {
		val num = numbers[i]
		compareSet = numbers.subList(i - preamble, i).toSet()
		for (e in compareSet) {
			val complement = num - e
			if (complement in compareSet && complement != e) continue@nextNumber
		}
		return num
	}
	throw IllegalStateException()
}
