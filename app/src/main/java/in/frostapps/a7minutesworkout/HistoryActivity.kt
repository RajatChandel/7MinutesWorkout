package `in`.frostapps.a7minutesworkout

import `in`.frostapps.a7minutesworkout.adapter.HistoryAdapter
import `in`.frostapps.a7minutesworkout.databinding.ActivityHistoryBinding
import `in`.frostapps.a7minutesworkout.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    var binding : ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistory)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarHistory?.setNavigationOnClickListener {
            onBackPressed()
        }
        val dao = (application as WorkoutApp).db.historyDao()
        getAllCompletedWorkouts(dao)
    }

    private fun getAllCompletedWorkouts(dao: HistoryDao) {
        lifecycleScope.launch {
        dao.fetchAllHistory().collect { allCompletedWorkouts ->
            if(allCompletedWorkouts.isNotEmpty()) {
                binding?.noHistoryView?.visibility = View.INVISIBLE
                binding?.rvHistory?.visibility = View.VISIBLE

                val adapter = HistoryAdapter(ArrayList(allCompletedWorkouts))
                binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity, LinearLayoutManager.VERTICAL,false )
                binding?.rvHistory?.adapter = adapter
            } else {
                binding?.noHistoryView?.visibility = View.VISIBLE
                binding?.rvHistory?.visibility = View.INVISIBLE
            }
        }
    }
    }


    override fun onDestroy() {
        super.onDestroy()
        if(binding != null) {
            binding = null
        }
    }
}