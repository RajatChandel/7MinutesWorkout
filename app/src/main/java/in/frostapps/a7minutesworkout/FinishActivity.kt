package `in`.frostapps.a7minutesworkout

import `in`.frostapps.a7minutesworkout.data.HistoryEntity
import `in`.frostapps.a7minutesworkout.databinding.ActivityFinishBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.*

class FinishActivity : AppCompatActivity() {

    private var binding : ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.tbFinish)

        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.tbFinish?.setNavigationOnClickListener {
            onBackPressed()
        }

        setupUI()
        val dao = (application as WorkoutApp).db.historyDao()
        addToDatabase(dao)
    }

    private fun setupUI() {
        binding?.flFinish?.setOnClickListener {
            finish()
        }
    }

    private fun addToDatabase(historyDao : HistoryDao) {

        val cal = Calendar.getInstance()
        val dateTime = cal.time

        val sdf = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(binding != null) {
            binding = null
        }
    }
}