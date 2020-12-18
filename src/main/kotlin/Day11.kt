fun day11() {
	val grid = readGrid("src/main/resources/day11.input")

	val occupied = countSeatsOnceStatic(grid.deepClone())
	println(occupied)

	val occupied2 = countSeatsOnceStaticWithSightline(grid.deepClone())
	println(occupied2)
}

private typealias Grid = Array<CharArray>

@Suppress("SameParameterValue")
private fun readGrid(filePath: String): Grid {
	var columns = 0
	var rows = 0
	readFileAndProcess(filePath) {
		columns = it.length
		rows += 1
	}
	val grid = Array(rows) { CharArray(columns) }
	rows = 0
	readFileAndProcess(filePath) {
		grid[rows] = it.toCharArray()
		rows++
	}
	return grid
}

fun Grid.deepClone() = Array(this.size) { i -> this[i].clone()}

private fun countSeatsOnceStatic(grid: Grid): Int {
	var pastState: Grid

	var isChanged: Boolean
	do {
		pastState = grid.deepClone()
		isChanged = false
		for (r in grid.indices) {
			for (c in grid[r].indices) {
				val seat = pastState[r][c]
				if (seat == 'L' && pastState.countNeighbors('#', r, c) == 0) {
					grid[r][c] = '#'
					isChanged = true
				}
				else if (seat == '#' && pastState.countNeighbors('#', r, c) >= 4) {
					grid[r][c] = 'L'
					isChanged = true
				}
			}
		}
	} while (isChanged)

	return grid.countOccupied()
}

private fun countSeatsOnceStaticWithSightline(grid: Grid): Int {
	var pastState: Grid

	var isChanged: Boolean
	do {
		pastState = grid.deepClone()
		isChanged = false
		for (r in grid.indices) {
			for (c in grid[r].indices) {
				val seat = pastState[r][c]
				if (seat == 'L' && pastState.countSightline('#', r, c) == 0) {
					grid[r][c] = '#'
					isChanged = true
				}
				else if (seat == '#' && pastState.countSightline('#', r, c) >= 5) {
					grid[r][c] = 'L'
					isChanged = true
				}
			}
		}
	} while (isChanged)

	return grid.countOccupied()
}

private fun Grid.countNeighbors(state: Char, r: Int, c: Int): Int {
	val neighborIndices = listOf(
		r-1 to c-1, r-1 to c, r-1 to c+1,
		r   to c-1,           r   to c+1,
		r+1 to c-1, r+1 to c, r+1 to c+1
	)
	var count = 0
	for ((nR, nC) in neighborIndices) {
		try {
			if (this[nR][nC] == state) count++
		} catch (e: Exception) {
			continue
		}
	}
	return count
}

private fun Grid.countSightline(state: Char, row: Int, col: Int): Int {
	var count = 0

	val axes = mapOf(
		'→' to {coords: Pair<Int, Int> -> coords.first to coords.second+1},
		'←' to {coords: Pair<Int, Int> -> coords.first to coords.second-1},
		'↓' to {coords: Pair<Int, Int> -> coords.first+1 to coords.second},
		'↑' to {coords: Pair<Int, Int> -> coords.first-1 to coords.second},
		'↗' to {coords: Pair<Int, Int> -> coords.first-1 to coords.second+1},
		'↖' to {coords: Pair<Int, Int> -> coords.first-1 to coords.second-1},
		'↘' to {coords: Pair<Int, Int> -> coords.first+1 to coords.second+1},
		'↙' to {coords: Pair<Int, Int> -> coords.first+1 to coords.second-1}
	)

	val rowIndices = indices
	val colIndices = this[0].indices

	for (axis in axes.values) {
		var coords = row to col
		nextCell@while(true) {
			coords = axis(coords)
			if (coords.first in rowIndices && coords.second in colIndices) {
				if (this[coords.first][coords.second] == state) {
					count++
					break
				}
				else if (this[coords.first][coords.second] == '.') {
					continue@nextCell
				}
			}
			break
		}
	}

	return count
}

private fun Grid.countOccupied(): Int {
	var occCount = 0
	for (r in indices) {
		for (c in this[r].indices) {
			if (this[r][c] == '#') occCount++
		}
	}
	return occCount
}

@Suppress("unused")
fun Grid.getGrid(): String {
	val sB = StringBuilder()
	for (r in this) {
		sB.append(r)
		sB.append('\n')
	}
	return sB.toString()
}