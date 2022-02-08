package indigo

fun main() {
    println("Indigo Card Game")
    val desk = CardsDesk()
    desk.shuffle()
    val cardTable = CardTable(desk)
    var first: Constants = Constants.COMPUTER
    var second: Constants = Constants.COMPUTER
    while (true) {
        println("Play first?")
        when (readLine()!!.lowercase()) {
            "yes" -> {
                first = Constants.MAN
                break
            }
            "no" -> {
                second = Constants.MAN
                break
            }
        }
    }
    println("Initial cards on the table: $cardTable")
    val firstPlayer = Player(desk, first)
    val secondPlayer = Player(desk, second)
    var lastWinPlayer = firstPlayer
    var player = firstPlayer
    loop@ while (true) {
        println()
        var cardOnTable: Card? = null
        if (cardTable.size() == 0) {
            println("No cards on the table")
        } else {
            print("${cardTable.size()} cards on the table, ")
            cardOnTable = cardTable.getTopCard()
            println("and the top card is $cardOnTable")

        }
        if (player.getCardsOnHand() == 0 && desk.size() == 0) {
            // Game Over
            if (cardTable.size() > 0) {
                lastWinPlayer.add(cardTable.getWinCards())
            }
            if (player.getWinsSize() >= secondPlayer.getWinsSize()) {
                player.extraPoints += 3
            } else {
                secondPlayer.extraPoints += 3
            }
            // always first player and second player
            printScore(player, secondPlayer)
            break
        }
        if (player.getCardsOnHand() == 0) {
            player.takeCards()
        }
        var nCard = 0
        if (player.type == Constants.MAN) {
            val numCards = player.getCardsOnHand()
            println("Cards in hand: ${player.cardsToString()}")
            while (true) {
                println("Choose a card to play (1-$numCards):")
                val input = readLine()!!
                if (input.lowercase() == "exit") {
                    break@loop
                } else {
                    val reg = Regex("[1-$numCards]")
                    if (input.matches(reg)) {
                        nCard = input.toInt()
                        break
                    }
                }
            }
        }
        val userCard = player.getCard(nCard, cardOnTable)
        if (player.type == Constants.COMPUTER) {
            println("Computer plays $userCard")
        }

        if (cardTable.isSameTopCard(userCard)) {
            cardTable.add(userCard)
            player.add(cardTable.getWinCards())
            lastWinPlayer = player
            val opponent = if (player == firstPlayer) secondPlayer else firstPlayer
            printWin(player)
            printScore(player, opponent)
        } else {
            cardTable.add(userCard)
        }
        player = if (player == firstPlayer) secondPlayer else firstPlayer
    }
    println("Game Over")
}

fun printWin(player: Player) {
    println("${player.getName()} wins cards")
}

fun printScore(firstPlayer: Player, secondPlayer: Player) {
    val player = if (firstPlayer.type == Constants.MAN) firstPlayer else secondPlayer
    val computer = if (player == firstPlayer) secondPlayer else firstPlayer
    println("Score: Player ${player.getScore()} - Computer ${computer.getScore()}")
    println("Cards: Player ${player.getWinsSize()} - Computer ${computer.getWinsSize()}")
}