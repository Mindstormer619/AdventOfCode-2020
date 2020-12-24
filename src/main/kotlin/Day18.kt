import Day18.ParseState.*
import java.util.*

private const val ZERO = '0'.toLong()

class Day18(filename: String) {
	private val homework = readFileAndProcess(filename) {it}

	fun evaluate(expression: String, iter: CharIterator = expression.iterator()): Long {
		var acc = 0L
		var state = INIT
		nextChar@ while (iter.hasNext()) {
			val currentSymbol = iter.nextChar()
			when (currentSymbol) {
				' ' -> continue@nextChar
				'*' -> {
					state = MUL
					continue@nextChar
				}
				'+' -> {
					state = ADD
					continue@nextChar
				}
				')' -> {
					return acc
				}
			}

			val zeroAscii = '0'.toLong()
			when (state) {
				INIT -> {
					when {
						currentSymbol.isDigit() -> acc = currentSymbol.toLong() - zeroAscii
						currentSymbol == '(' -> acc = evaluate(expression, iter)
					}
				}
				MUL -> {
					when {
						currentSymbol.isDigit() -> acc *= currentSymbol.toLong() - zeroAscii
						currentSymbol == '(' -> acc *= evaluate(expression, iter)
					}
				}
				ADD -> {
					when {
						currentSymbol.isDigit() -> acc += currentSymbol.toLong() - zeroAscii
						currentSymbol == '(' -> acc += evaluate(expression, iter)
					}
				}
			}
		}
		return acc
	}

	fun sumOfHomework(): Long = homework.map { evaluate(it) }.reduce { acc, l -> acc + l }

	fun evaluateAdvanced(
		expression: String
	): Long {
		val s: Stack<Any> = Stack()
		s.push('(')
		val iter = expression.iterator()

		while (iter.hasNext()) {
			val current = iter.nextChar()
			if (current == ' ') continue
			when {
				current.isDigit() -> {
					if (s.peek() == '+') {
						s.pop()
						val other = s.pop() as Long
						s.push(other + current.toDigit())
					}
					else {
						s.push(current.toDigit())
					}
				}
				current == ')' -> {
					reduce(s)
				}
				else -> s.push(current)
			}
		}

		reduce(s)
		return s.pop() as Long
	}

	private fun reduce(s: Stack<Any>) {
		var acc: Long = 0
		while (true) {
			val top = s.pop()
			if (top is Char) {
				if (top == '(') break
				if (top == '*') {
					val other = s.pop() as Long
					s.push(acc * other)
				}
			} else if (top is Long) {
				acc = top
			}
		}
		if (!s.empty() && s.peek() == '+') {
			s.pop()
			s.push(acc + s.pop() as Long)
		} else {
			s.push(acc)
		}
	}

	private fun Char.toDigit() = this.toLong() - ZERO

	fun sumOfHomeworkAdvanced(): Long = homework.map { evaluateAdvanced(it) }.reduce { acc, l -> acc + l }


	enum class ParseState {INIT, MUL, ADD}
}