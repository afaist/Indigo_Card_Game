package indigo

class CardsDesk {
    private val ranks = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
    private val suits = listOf('♠', '♥', '♦', '♣')
    private val cards = Array(ranks.size * suits.size) {
        Card(ranks[it % ranks.size], suits[it / ranks.size])
    }
    private var desk = cards.toMutableList()
    fun get(n: Int): List<Card> {
        if (n > desk.size) {
            return emptyList()
        }
        val sets = desk.slice(0 until n)
        desk = if (desk.size > n) {
            desk.drop(n) as MutableList<Card>
        } else {
            emptyList<Card>().toMutableList()
        }
        return sets
    }

    fun shuffle() {
        desk.shuffle()
    }

    fun reset() {
        desk = cards.toMutableList()
    }

    override fun toString(): String {
        return desk.joinToString(" ")
    }

    fun size(): Int {
        return desk.size
    }
}