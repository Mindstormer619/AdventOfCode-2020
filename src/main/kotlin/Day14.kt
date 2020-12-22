@file:Suppress("SpellCheckingInspection")

import java.io.File
import kotlin.math.pow

class Day14(filename: String) {
	private val maskRegex = Regex("""mask = ([X10]{36})""")
	private val memRegex = Regex("""mem\[([0-9]+)] = ([0-9]+)""")

	private val memoryMapV1: MutableMap<Int, Pair<Long, String>> = mutableMapOf()
	private val memoryMapV2: MutableMap<Pair<Long, String>, Long> = mutableMapOf()

	init {
		val lines = File(filename).readLines()
		var currentMask = ""
		for (line in lines) {
			if (maskRegex.matches(line)) {
				currentMask = maskRegex.matchEntire(line)!!.groupValues[1]
			}
			else {
				val memRegexGroups = memRegex.matchEntire(line)!!.groupValues
				memoryMapV1[Integer.parseInt(memRegexGroups[1])] = memRegexGroups[2].toLong() to currentMask
				memoryMapV2[memRegexGroups[1].toLong() to currentMask] = memRegexGroups[2].toLong()
			}
		}
	}


	fun findMemorySum(): Long {
		var sum = 0L
		for (entry in memoryMapV1.values) {
			val masked = entry.first mask entry.second
			sum += masked
		}

		return sum
	}

	private val allXMask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

	fun findMemorySumOnMemMask(): Long {
		val memoryMap = mutableMapOf<Long, Long>()

		for (entry in memoryMapV2) {
			val memory = entry.key
			val xPositions = memory.second.withIndex().filter { it.value == 'X' }.map { it.index }.toList()
			// mask with 1s
			val orTransform = memory.first orMask memory.second

			for (i in 0L until (2L pow xPositions.size)) {
				val maskBits = i.toString(2).padStart(xPositions.size, '0')
				val maskBuilder = StringBuilder(allXMask)
				for (pos in xPositions.indices) {
					maskBuilder.insert(xPositions[pos], maskBits[pos])
				}
				memoryMap[orTransform mask maskBuilder.substring(0 until 36)] = entry.value
			}
		}

		return memoryMap.values.sum()
	}

	companion object {
		infix fun Long.mask(mask: String): Long {
			val andMask = mask.replace('X', '1').toLong(radix = 2)
			val orMask = mask.replace('X', '0').toLong(radix = 2)
			return this and andMask or orMask
		}

		infix fun Long.orMask(mask: String) : Long {
			val orMask = mask.replace('X', '0').toLong(radix = 2)
			return this or orMask
		}

		infix fun Long.pow(exponent: Int): Long = toDouble().pow(exponent).toLong()
	}
}
