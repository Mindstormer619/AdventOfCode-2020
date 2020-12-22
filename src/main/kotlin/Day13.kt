import java.io.File
import java.lang.IllegalStateException

class Day13(filename: String) {
	private val arrivalTime: Int
	private val busList: List<IndexedValue<Int>>

	init {
		val lines = File(filename).readLines()
		arrivalTime = Integer.parseInt(lines[0])
		busList = lines[1]
			.split(',')
			.withIndex()
			.filter { it.value != "x" }
			.map { IndexedValue(it.index, it.value.toInt()) }
			.sortedBy { it.value }
			.reversed()

		println(busList)
	}

	fun solveEarliestBus(): Int {
		var earliestTime = Int.MAX_VALUE
		var earliestBus = 0
		for (busWithIndex in busList) {
			val bus = busWithIndex.value
			if (arrivalTime % bus == 0) return 0
			val busTime = (arrivalTime / bus + 1) * bus
			if (busTime < earliestTime) {
				earliestTime = busTime
				earliestBus = bus
			}
		}

		return (earliestTime - arrivalTime) * earliestBus
	}

	fun solveBusSequence(): Long {
		val (indexOfMax, max) = busList.maxByOrNull { it.value }!!

		nextTime@for (i in 1..Long.MAX_VALUE) {
			val t = max * i - indexOfMax
			for (b in busList) {
				val bus = b.value
				if ((t+b.index) % bus != 0L) continue@nextTime
			}
			return t
		}
		throw IllegalStateException()
	}

}
