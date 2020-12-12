fun main() {
	val lines = readFileAndProcess("src/main/resources/day04.input") {it}

	val solution = solve(lines, ::checkFieldsPresent)
	println(solution)

	val solution2 = solve(lines, ::checkFieldsAndRules)
	println(solution2)
}

private fun solve(lines: List<String>, checkPassport: (Map<String, String>) -> Boolean): Int {
	var validCount = 0

	var runningPassport = mutableMapOf<String, String>()

	for (line in lines) {
		if (line.isEmpty()) {
			val hasAllFields = checkPassport(runningPassport)
			if (hasAllFields) validCount++
			runningPassport = mutableMapOf()
		}
		else {
			val parts = line.split(' ')
			for (part in parts) {
				// eyr:1980
				val (key, value) = part.split(':')
				runningPassport[key] = value
			}
		}
	}

	return validCount
}

private fun checkFieldsPresent(
	passport: Map<String, String>
): Boolean {
	val compulsoryFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
	var hasAllFields = true
	for (field in compulsoryFields) {
		if (passport[field] == null) {
			hasAllFields = false
			break
		}
	}
	return hasAllFields
}

private fun checkFieldsAndRules(
	passport: Map<String, String>
): Boolean {
	val compulsoryFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
	var isValid = true
	for (fieldName in compulsoryFields) {
		val field = passport[fieldName]
		if (field == null) {
			isValid = false
			break
		}
		if (!validateRule(field, fieldName)) {
			isValid = false
			break
		}
	}
	return isValid
}

private fun validateRule(field: String, fieldName: String) : Boolean =
	try {
		when(fieldName) {
			"byr" -> Integer.parseInt(field) in 1920..2002
			"iyr" -> Integer.parseInt(field) in 2010..2020
			"eyr" -> Integer.parseInt(field) in 2020..2030
			"hgt" -> processHeightString(field)
			"hcl" -> hclRegex.matches(field)
			"ecl" -> "amb blu brn gry grn hzl oth".split(" ").contains(field)
			"pid" -> pidRegex.matches(field)
			else -> false
		}
	} catch (e: Exception) {
		false
	}

private val hgtRegex = Regex("""([0-9]+)(cm|in)""")
private val hclRegex = Regex("""#[0-9a-f]{6}""")
private val pidRegex = Regex("""[0-9]{9}""")

private fun processHeightString(hs: String): Boolean {
	val match = hgtRegex.matchEntire(hs) ?: return false
	val height = match.groupValues[1].toInt()
	val unit = match.groupValues[2]

	if (unit == "cm") return height in 150..193
	if (unit == "in") return height in 59..76
	return false
}
