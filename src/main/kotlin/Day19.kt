import java.io.File

class Day19(filename: String) {
	private val ruleMap: Map<Int, Rule>
	private val messages: List<String>

	sealed class Rule {
		data class RuleChain(val ruleOptions: List<RuleList>): Rule()
		data class TerminalRule(val char: Char): Rule()
	}

	init {
		val lines = File(filename).readLines()

		val rules = mutableMapOf<Int, Rule>()
		val messages = mutableListOf<String>()
		nextLine@ for (line in lines) {
			when {
				line.isEmpty() -> { }
				line[0].isDigit() -> {
					val (idx, ruleString) = line.split(':')
					val rule: Rule = if (ruleString.endsWith('"')) {
						// process TerminalRule
						Rule.TerminalRule(ruleString[2])
					} else {
						val ruleLists = ruleString.split('|')
						Rule.RuleChain(ruleLists.map { ruleList -> ruleList.trim().split(' ').map { it.toInt() } })
					}

					rules[idx.toInt()] = rule
				}
				else -> {
					messages.add(line)
				}
			}
		}

		this.ruleMap = rules
		this.messages = messages
	}

	fun countMatchingMessages(): Int {
		var matchingMessages = 0
		for (message in messages) {
			if (message.matchesRule()) {
				matchingMessages++
			}
		}
		return matchingMessages
	}

	private fun String.matchesRule(): Boolean {
		val (result, parsedLength) = Parser(this, ruleMap).parse()
		return result && parsedLength == this.length
	}


	private class Parser(val message: String, val ruleMap: Map<Int, Rule>) {
		fun parse(ruleId: Int = 0, ptr: Int = 0): Pair<Boolean, Int> {
			if (ptr >= message.length) return false to ptr

			when (val rule = ruleMap[ruleId]!!) {
				is Rule.RuleChain -> {
					nextOption@ for (ruleList in rule.ruleOptions) {
						var p = ptr
						for (ruleIndex in ruleList) {
							val (result, newP) = parse(ruleIndex, p)
							if (result) p = newP
							else continue@nextOption
						}
						return true to p
					}
					return false to ptr
				}
				is Rule.TerminalRule -> {
					return if (message[ptr] == rule.char) true to ptr+1
					else false to ptr
				}
			}
		}
	}
}


private typealias RuleList = List<Int>