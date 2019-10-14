package hva.nl.rockpaperscissors

import kotlin.random.Random

class RandomEnum {
    companion object {
        private val SEED = Math.random().toInt()
        private val RANDOM = Random(SEED)
        fun randomEnum(): GameChoiches {
            return GameChoiches.values()[RANDOM.nextInt(GameChoiches.values().size)]
        }
    }

}