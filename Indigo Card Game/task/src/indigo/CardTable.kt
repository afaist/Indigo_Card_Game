package indigo


const val INIT_SIZE = 4

class CardTable(desk: CardsDesk) {
    private var cards: MutableList<Card> = desk.get(INIT_SIZE).toMutableList()
    fun size(): Int {
        return cards.size
    }

    fun getWinCards(): List<Card> {
        val cardsList = cards.toList()
        cards.clear()
        return cardsList
    }

    fun getTopCard() = cards.last()
    fun isSameTopCard(card: Card): Boolean {
        if (cards.isEmpty()) return false
        return card.same(getTopCard())
    }

    fun add(card: Card) {
        cards.add(card)
    }

    override fun toString(): String {
        return cards.joinToString(" ")
    }
}