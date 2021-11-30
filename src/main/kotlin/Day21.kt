import java.util.*

class Day21(filename: String) {
	val input: List<FoodItem>
	init {
		val foodPattern = Regex("""(.*) \(contains (.*)\)""")
		input = readFileAndProcess(filename) {
			val (_, ingredientList, allergenList) = foodPattern.matchEntire(it)!!.groupValues
			FoodItem(
				ingredientList.split(' ').toSet(),
				allergenList.split(", ").toSet()
			)
		}
	}

	/*
	each allergen:
		if there are already mapped ingredients
			take intersection
			if only one ingredient is left, remove the ingredient from the other allergen lists
		else: add in the ingredients to a set of possible ingredients containing this
	 */
	fun getOccurrencesOfSafeIngredients(): Int {
		val possibleIngredients = getMapOfAllergenToPossibleIngredients()

		val unsafeIngredients = possibleIngredients.values.reduce { ingredients, currentIngredients ->
			ingredients.union(currentIngredients)
		}

		var safeCount = 0
		for (ingredients in input.map { it.ingredients }) {
			for (ingredient in ingredients) {
				if (ingredient !in unsafeIngredients) safeCount++
			}
		}
		return safeCount
	}

	private fun getMapOfAllergenToPossibleIngredients(): MutableMap<String, Set<String>> {
		val possibleIngredients = mutableMapOf<String, Set<String>>()

		for (foodItem in input) {
			for (allergen in foodItem.allergens) {
				if (possibleIngredients[allergen] != null) {
					possibleIngredients[allergen] = possibleIngredients[allergen]!!.intersect(foodItem.ingredients)
					val ingredients = possibleIngredients[allergen]!!
					// check if this is the only ingredient left
					if (ingredients.size == 1) {
						possibleIngredients.forEach { (otherAllergen, _ingredients) ->
							if (otherAllergen != allergen)
								possibleIngredients[otherAllergen] = _ingredients - ingredients
						}
					}
				} else {
					possibleIngredients[allergen] = foodItem.ingredients
				}
			}
		}
		return possibleIngredients
	}

	fun getCanonicalDangerousList(): String {
		val allergenToPossibleIngredients = getMapOfAllergenToPossibleIngredients()
		val lockedPairs = mutableMapOf<String, String>()

		startOver@ while (true) {
			for ((allergen, ingredientSet) in allergenToPossibleIngredients) {
				if (ingredientSet.size == 1) {
					allergenToPossibleIngredients.removeIngredient(allergen, ingredientSet.first(), lockedPairs)
					continue@startOver
				}
			}
			break
		}

		return lockedPairs.toSortedMap().values.joinToString(separator = ",")
	}
}

private fun MutableMap<String, Set<String>>.removeIngredient(
	allergen: String,
	ingredient: String,
	lockedPairs: MutableMap<String, String>
) {
	this.remove(allergen)
	for ((a, iSet) in this) {
		if (ingredient in iSet) this[a] = iSet - ingredient
	}
	lockedPairs[allergen] = ingredient
}

class FoodItem(
	val ingredients: Set<String>,
	val allergens: Set<String>
)