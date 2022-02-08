package indigo

const val SIZE_OF_CARDS = 6

open class Player(private val cardsDesk: CardsDesk, _type: Constants) {
    private var cards = mutableListOf<Card>()
    private var cardsWin = mutableListOf<Card>()
    var extraPoints = 0
    val type = _type
    fun getName() = if (type == Constants.MAN) "Player" else "Computer"
    fun takeCards(n: Int = SIZE_OF_CARDS) {
        cards = cardsDesk.get(n).toMutableList()
    }


    fun getCardsOnHand(): Int {
        if (cards.isEmpty()) {
            cards = cardsDesk.get(SIZE_OF_CARDS).toMutableList()
        }
        return cards.size
    }

    fun getCard(n_card: Int, cardOnTable: Card? = null): Card {
        val n = if (n_card == 0) {
            println(cards.joinToString(" "))
            getCardSmart(cardOnTable) + 1
        } else n_card
        val card = cards[n - 1]
        cards.removeAt(n - 1)
        return card
    }

    /**
     * Smart card selection
     *
     * @param cardOnTable
     * @return Card number
     */
    private fun getCardSmart(cardOnTable: Card?): Int {
        if (cards.size == 1) {
            return 0
        }
        if (cardOnTable == null || cards.count { cardOnTable.same(it) } == 0) {
            val suitMap: Map<Char, List<Card>> = cards.groupBy { it.suit }.filter { it.value.size > 1 }
            return if (suitMap.isNotEmpty()) {
                val cardSelect: Card = suitMap.values.first()[0]
                cards.indexOf(cardSelect)
            } else {
                val rankMap: Map<String, List<Card>> = cards.groupBy { it.rank }.filter { it.value.size > 1 }
                if (rankMap.isNotEmpty()) {
                    val cardSelect: Card = rankMap.values.first()[0]
                    cards.indexOf(cardSelect)
                } else {
                    (cards.indices).random()
                }
            }
        } else {
            val cardsSameSuit = cards.filter { cardOnTable.sameSuit(it) }
            val cardsSameRank = cards.filter { cardOnTable.sameRank(it) }
            val card = if (cardsSameSuit.size > 1){
                 cardsSameSuit.random()
            } else if(cardsSameRank.size > 1) {
                cardsSameRank.random()
            } else {
                if (cardsSameSuit.size > cardsSameRank.size){
                    cardsSameSuit.random()
                } else {
                    cardsSameRank.random()
                }
            }
            return cards.indexOf(card)
        }
    }

    fun add(cardsList: List<Card>) {
        cardsWin.addAll(cardsList)
    }

    fun getWinsSize() = cardsWin.size

    fun getScore(): Int {
        return cardsWin.sumOf { it.getScore() } + extraPoints
    }

    fun cardsToString(): String {
        val str = StringBuilder()
        for (i in cards.indices) {
            if (str.isNotEmpty()) {
                str.append(" ")
            }
            str.append("${i + 1})${cards[i]}")
        }
        return str.toString()
    }
}