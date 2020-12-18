fun day01() {
	println(System.getProperty("user.dir"))
	val numList = readFileAndProcess("src/main/resources/day01.input") {Integer.parseInt(it)}

	val solution = solve2Nums(numList)
	println(solution)

	val solution2 = solve3Nums(numList)
	println(solution2)
}

private fun solve2Nums(numList: List<Int>): Long {
	/*
	List of numbers
	for each number x, the number we need is gonna be (2020 - x)

	MutableSet of seen numbers: alreadySeen
	 */

	val alreadySeen = mutableSetOf<Int>()

	for (num in numList) {
		val complement = 2020 - num
		if (complement in alreadySeen) {
			return num.toLong() * complement
		}
		else {
			alreadySeen.add(num)
		}
	}

	return 0
}

private fun solve3Nums(numList: List<Int>): Long {
	/*
	for every pair of numbers:
		sum them up -> sumOf2
		complement = 2020 - sumOf2
		check alreadySeen, if you see the complement, you're done
		else add BOTH numbers to alreadySeen
	 */

	val alreadySeen = mutableSetOf<Int>()

	for (i in numList.indices) {
		for (j in i+1 until numList.size) {
			val sumOf2 = numList[i] + numList[j]
			val complement = 2020 - sumOf2
			if (complement in alreadySeen) {
				return numList[i] * numList[j] * complement.toLong()
			}
			else {
				alreadySeen.add(numList[i])
				alreadySeen.add(numList[j])
			}
		}
	}

	TODO()
}
