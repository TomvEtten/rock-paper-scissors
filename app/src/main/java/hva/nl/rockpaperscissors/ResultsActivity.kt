package hva.nl.rockpaperscissors

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_results.*
import kotlinx.android.synthetic.main.content_results.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.AndroidException

class ResultsActivity : AppCompatActivity() {
    private val resultsList = arrayListOf<Result>()
    private lateinit var resultRepository: ResultRepository
    private val resultsAdapter = ResultsAdapter(resultsList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_black_24dp)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        resultRepository = ResultRepository(this)
        initViews()
    }

    private fun initViews() {
        rvResults.adapter = resultsAdapter
        rvResults.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvResults.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        getResultsFromDatabase()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_results, menu)
        return true
    }

    private fun backToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all_results -> {
                deleteAllResults()
                true
            }
            android.R.id.home -> {
                backToMain()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getResultsFromDatabase() {
        this@ResultsActivity.resultsList.clear()
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
               resultsList.addAll( resultRepository.getAllResults())
            }
        }.invokeOnCompletion {
            this@ResultsActivity.resultsAdapter.notifyDataSetChanged()
        }


    }

    private fun deleteAllResults() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                resultRepository.deleteAllResults()
            }
            getResultsFromDatabase()
        }
    }

}
