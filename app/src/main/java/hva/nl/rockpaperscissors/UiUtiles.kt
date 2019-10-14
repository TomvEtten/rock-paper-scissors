package hva.nl.rockpaperscissors

class UiUtiles {
    companion object {
        fun getDrawableFromChoiche(result: String): Int {
            if (result == GameChoiches.SCISSOR.toString()) {
                return R.drawable.scissors
            }
            if (result == GameChoiches.PAPER.toString()) {
                return R.drawable.paper
            }
            return R.drawable.rock
        }
    }
}