import java.io.File

class Day16(filename: String) {

	private val fieldRegex = Regex("""([a-z ]+): ([0-9]+)-([0-9]+) or ([0-9]+)-([0-9]+)""")
	private val ticketRegex = Regex("""^[0-9].*""")

	private val fieldMap = mutableMapOf<String, FieldRule>()
	private val tickets = mutableListOf<Ticket>()

	init {
		val lines = File(filename).readLines()

		for (line in lines) {
			if (fieldRegex.matches(line)) {
				val groups = fieldRegex.matchEntire(line)!!.groupValues
				fieldMap[groups[1]] = groups[2].toInt()..groups[3].toInt() to groups[4].toInt()..groups[5].toInt()
			}
			else if (ticketRegex.matches(line)) {
				tickets.add(line.split(',').map { it.toInt() })
			}
		}
	}

	fun getTicketErrorRate(): Int {
		var errorRate = 0

		for (ticket in tickets.subList(1, tickets.size)) {
			nextField@for (field in ticket) {
				for (rangePair in fieldMap.values) {
					if (rangePair.isValid(field)) continue@nextField
				}
				errorRate += field
			}
		}

		tickets.listIterator()

		return errorRate
	}

	fun findTicketFields(): Map<String, Int> {
		tickets.removeInvalid()

		val candidateFields = List(fieldMap.size) { mutableSetOf<String>() }
		val lockedFields = mutableMapOf<String, Int>()

		var i = 0
		while (lockedFields.size < fieldMap.size) {
			if (i in lockedFields.values) {
				i = i+1 mod fieldMap.size
				continue
			}
			nextField@for (field in fieldMap) {
				for (ticket in tickets) {
					// trial of fire
					if (!field.value.isValid(ticket[i])) {
						candidateFields[i].remove(field.key)
						continue@nextField
					}
				}
				if (field.key !in lockedFields) candidateFields[i].add(field.key)
			}
			if (candidateFields[i].size == 1) {
				val fieldName = candidateFields[i].first()
				lockedFields[fieldName] = i
				for (candidate in candidateFields) candidate.remove(fieldName)
			}
			i = i+1 mod fieldMap.size
		}

		return lockedFields.mapValues { tickets[0][it.value] }
	}

	fun getTicketDeparture(ticket: Map<String, Int>): Long =
		ticket.filter { it.key.startsWith("departure") }.map { it.value.toLong() }.reduce(Long::times)


	private fun MutableList<Ticket>.removeInvalid() {
		val iterator = this.listIterator()

		nextTicket@while (iterator.hasNext()) {
			val ticket = iterator.next()
			nextField@for (field in ticket) {
				for (rangePair in fieldMap.values) {
					if (rangePair.isValid(field)) continue@nextField
				}
				iterator.remove()
				continue@nextTicket
			}
		}
	}

	private fun FieldRule.isValid(field: Int) = field in first || field in second
}

private typealias FieldRule = Pair<IntRange, IntRange>
private typealias Ticket = List<Int>
