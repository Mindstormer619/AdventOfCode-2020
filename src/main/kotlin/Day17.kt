data class Cube (val x: Int, val y: Int, val z: Int, val w: Int = 0) {
	fun generateNeighbors(fourD: Boolean): Set<Cube> {
		val neighbors = mutableSetOf<Cube>()
		for (i in -1..1) {
			for (j in -1..1) {
				for (k in -1..1) {
					if (fourD) {
						for (l in -1..1) {
							if (i != 0 || j != 0 || k != 0 || l != 0) neighbors.add(Cube(x+i, y+j, z+k, w+l))
						}
					}
					else {
						if (i != 0 || j != 0 || k != 0) neighbors.add(Cube(x+i, y+j, z+k))
					}
				}
			}
		}
		return neighbors
	}
}

class Day17(filename: String) {
	private val activeSet = mutableSetOf<Cube>()

	init {
		var rowNum = 0
		readFileAndProcess(filename) {
			for (i in it.withIndex()) {
				if (i.value == '#') activeSet.add(Cube(x = rowNum, y = i.index, z = 0))
			}
			rowNum++
		}
		println(activeSet)
	}

	fun countActive(fourD: Boolean = false): Int {
		for (round in 1..6) {
			val savedState = activeSet.toSet()
			val candidateCubes = savedState
				.map { it.generateNeighbors(fourD) }
				.reduce { acc, set -> acc.union(set) }
				.union(savedState)
			for (cube in candidateCubes) {
				val neighbors = cube.generateNeighbors(fourD)
				val activeNeighborCount = (neighbors intersect savedState).size
				if (cube in savedState) {
					// cube is active
					if (activeNeighborCount != 2 && activeNeighborCount != 3) activeSet.remove(cube)
				}
				else {
					// cube is inactive
					if (activeNeighborCount == 3) activeSet.add(cube)
				}
			}
		}

		return activeSet.size
	}
}