package hva.nl.rockpaperscissors

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import hva.nl.rockpaperscissors.UiUtiles.Companion.getDrawableFromChoiche
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.*
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var resultRepository: ResultRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        resultRepository = ResultRepository(this)
        initViews()
    }

    private fun initViews() {
        tvQuerys.text = getResults()
        tvWinLoseMessage.text = ""
        btChoichePaper.setOnClickListener{ executeGame(GameChoiches.PAPER) }
        btChoicheRock.setOnClickListener{ executeGame(GameChoiches.ROCK) }
        btChoicheScissors.setOnClickListener{ executeGame(GameChoiches.SCISSOR) }
    }

    private fun getResults(): String {
        var  resultString = ""
        runBlocking(Dispatchers.IO) {
            val wins = async {
                resultRepository.getAmountOfWins("You win!").toString()
            }
            val loses = async {
                resultRepository.getAmountOfWins("Computer wins!").toString()
            }
            val draws = async {
                resultRepository.getAmountOfWins("DRAW").toString()
            }
            wins.await() + loses.await() + draws.await()
            resultString = "Wins : " + wins.getCompleted() +" Loses : " + loses.getCompleted() + " Draws : " + draws.getCompleted()
        }
        return resultString
    }

    private fun startResultsPage() {
        val intent = Intent(this@MainActivity, ResultsActivity::class.java)
        startActivity(intent)
    }

    private fun executeGame(playerChoiche: GameChoiches) {
        val choicheComputer = RandomEnum.randomEnum()
        val result = processMatch(playerChoiche , choicheComputer)
        updateUI(result)
        writeResultToDatabase(result)
    }

    private fun writeResultToDatabase(result: Result) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                resultRepository.insertResult(result)
            }
        }
    }

    private fun updateUI(result: Result) {
       ivPlayerChoiche.setImageResource(getDrawableFromChoiche(result.playerChoice))
       ivComputerChoiche.setImageResource(getDrawableFromChoiche(result.computerChoice))
       tvWinLoseMessage.text = result.winner
        tvQuerys.text = getResults()
    }




    private fun processMatch(playerChoiche: GameChoiches, computerChoiche: GameChoiches): Result {
        val winner = determineWinner(playerChoiche, computerChoiche)
        return Result(
            playerChoiche.toString(),
            computerChoiche.toString(),
            winner,
            Calendar.getInstance().time.toString()
        )
    }

    private fun determineWinner(playerChoiche: GameChoiches, computerChoiche: GameChoiches): String {
        if (playerChoiche.toString() == computerChoiche.toString()) {
            return "DRAW"
        }
        if (playerChoiche == GameChoiches.PAPER && computerChoiche == GameChoiches.ROCK ||
            playerChoiche == GameChoiches.ROCK && computerChoiche == GameChoiches.SCISSOR ||
            playerChoiche == GameChoiches.SCISSOR && computerChoiche == GameChoiches.PAPER
        ) {
            return "You win!"
        }
        return "Computer wins!"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_go_to_history -> {
                startResultsPage()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
