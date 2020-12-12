fun main() {
	val seats = readFileAndProcess("src/main/resources/day05.input") { inputRow ->
		inputRow
			.replace('F', '0').replace('B', '1')
			.replace('L', '0').replace('R', '1')
			.let { Pair(it.substring(0..6), it.substring(7..9)) }
			.let { Seat(Integer.parseInt(it.first, 2), Integer.parseInt(it.second, 2)) }
	}

	val biggestSeat = findBiggestSeat(seats)
	println("Seat $biggestSeat has seat ID ${biggestSeat?.seatId}")

	val mySeatId = findMySeat(seats)
	println(mySeatId)
}

private fun findMySeat(seats: List<Seat>): Int {
	val sortedSeatIds = seats.map { it.seatId }.sorted()
	for (i in 1..sortedSeatIds.size - 2) {
		val seatId = sortedSeatIds[i]
		val nextSeatId = sortedSeatIds[i+1]
		if (nextSeatId != seatId + 1) {
			return seatId + 1
		}
	}
	return -1
}

private fun findBiggestSeat(seats: List<Seat>) =
	seats.maxByOrNull { it.seatId }

data class Seat (
	val row: Int,
	val column: Int,
) {
	val seatId: Int
		get() = row * 8 + column
}