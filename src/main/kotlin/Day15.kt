import java.io.File

class Day15(filename: String) {
	private val lastSpokenMap: MutableMap<Long, Long> = mutableMapOf()
	private val startingValue: Long

	init {
		val initialValues = File(filename).readLines()[0].split(',').map { it.toLong() }
		initialValues.withIndex().map { it.value to (it.index+1).toLong() }.toMap(lastSpokenMap)
		println(lastSpokenMap)
		startingValue = initialValues.last()
	}


	fun playMemoryGame(finalTurn: Long = 2020L): Long {
		var lastSpoken = startingValue
		for (turn in lastSpokenMap.size+1..finalTurn) {
			val justSeen = lastSpoken
			lastSpoken = if (lastSpoken in lastSpokenMap) {
				turn-1 - lastSpokenMap[lastSpoken]!!
			} else {
				0
			}
			lastSpokenMap[justSeen] = turn-1
		}
		return lastSpoken
	}
}