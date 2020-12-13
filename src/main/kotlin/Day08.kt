enum class InstructionType {
	NOP, JMP, ACC
}

data class Instruction(
	var instructionType: InstructionType,
	val value: Int
)

fun main() {
	val instructionList = readFileAndProcess("src/main/resources/day08.input") {
		Instruction(
			InstructionType.valueOf(it.substring(0..2).toUpperCase()),
			Integer.parseInt(it.substring(4))
		)
	}

	println(getAccumulatedValue(instructionList))

	println(getAccumulatedValueAfterChange(instructionList))
}

private fun getAccumulatedValue(instructionList: List<Instruction>): Int {
	var acc = 0
	var progCounter = 0

	val visitedInstructions = mutableListOf<Int>()

	while (true) {
		if (progCounter in visitedInstructions) break
		visitedInstructions.add(progCounter)
		val instruction = instructionList[progCounter]
		when (instruction.instructionType) {
			InstructionType.NOP -> {
				progCounter++
			}
			InstructionType.JMP -> {
				progCounter += instruction.value
			}
			InstructionType.ACC -> {
				acc += instruction.value
				progCounter++
			}
		}
	}

	return acc
}

private fun getAccumulatedValueAfterChange(instructionList: List<Instruction>): Int {
	try {
		instructionList.processInstruction(
			visitedInstructions = mutableSetOf(),
			progCounter = 0,
			acc = 0,
			converted = false
		)
	} catch (e: EndOfInstructions) {
		return e.accVal
	}
	return 0
}

@Throws(EndOfInstructions::class)
fun List<Instruction>.processInstruction(
	visitedInstructions: Set<Int>,
	progCounter: Int,
	acc: Int,
	converted: Boolean = true
): Boolean {
	if (progCounter in visitedInstructions) return true
	val updatedInstructions = visitedInstructions + progCounter
	val instruction = try {this[progCounter]} catch (e: Exception) {throw EndOfInstructions(acc)}
	val value = instruction.value
	return when (instruction.instructionType) {
		InstructionType.NOP -> {
			if (converted) processInstruction(updatedInstructions, progCounter+1, acc, true)
			else {
				processInstruction(updatedInstructions, progCounter+value, acc, true)
				processInstruction(updatedInstructions, progCounter+1, acc, false)
			}
		}
		InstructionType.JMP -> {
			if (converted) processInstruction(updatedInstructions, progCounter+value, acc, true)
			else {
				processInstruction(updatedInstructions, progCounter+1, acc, true)
				processInstruction(updatedInstructions, progCounter+value, acc, false)
			}
		}
		InstructionType.ACC -> {
			processInstruction(updatedInstructions, progCounter+1, acc + value, converted)
		}
	}
}

class EndOfInstructions(val accVal: Int): Throwable()
