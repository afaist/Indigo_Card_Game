package indigo

data class Card(val rank: String, val suit: Char) {
    private val rankWithPoint = listOf("A", "10", "J", "Q", "K")
    fun getScore() = if (rank in rankWithPoint) 1 else 0
    override fun toString(): String {
        return rank + suit
    }

    fun same(other: Card) = other.rank == rank || other.suit == suit
    fun sameSuit(other: Card) = other.suit == suit
    fun sameRank(other: Card) = other.rank == rank
}