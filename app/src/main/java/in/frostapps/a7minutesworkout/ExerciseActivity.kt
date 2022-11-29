package `in`.frostapps.a7minutesworkout

import `in`.frostapps.a7minutesworkout.adapter.ExerciseNumberAdapter
import `in`.frostapps.a7minutesworkout.databinding.ActivityExerciseBinding
import `in`.frostapps.a7minutesworkout.databinding.BackDialogBinding
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import java.util.*

class ExerciseActivity : AppCompatActivity(), OnInitListener {

    private var binding: ActivityExerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercise = 0
    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null
    private var adapter: ExerciseNumberAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        tts = TextToSpeech(this, this)
        binding?.toolbarExercise?.setNavigationOnClickListener {
            customBackDialog()
        }

        exerciseList = Constants.defaultExerciseList()
        adapter = ExerciseNumberAdapter(exerciseList!!)
        binding?.rvExerciseNumber?.adapter = adapter
        binding?.rvExerciseNumber?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        setupRestView()
    }

    override fun onBackPressed() {
        customBackDialog()
    }

    fun customBackDialog() {
        val dialog = Dialog(this@ExerciseActivity)
        val dialogBinding = BackDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(false)

        dialogBinding.btnYes.setOnClickListener {
            this@ExerciseActivity.finish()
            dialog.dismiss()
        }

        dialogBinding.btnNo.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun setRestProgressBar() {

//        try{
//            val soundURI = Uri.parse("android.resource://in.frostapps.a7minutesworkout/" )
//        } catch (e : java.lang.Exception) {
//            e.printStackTrace()
//        }

        binding?.progressBar?.progress = restProgress
        binding?.tvTitle?.text = "Get Ready"
        binding?.tvUpcoming?.text = "Next exercise : ${exerciseList!![currentExercise].getName()}"
        binding?.progressBar?.max = 10
        binding?.ivImage?.setImageResource(R.drawable.running_photo)
        speakOut("Rest now")
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                exerciseList!![currentExercise].setIsSelected(true)
                adapter!!.notifyDataSetChanged()
                startExerciseProgress()
            }
        }.start()
    }

    private fun startExerciseProgress() {
        binding?.tvTitle?.text = exerciseList!![currentExercise].getName()
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvUpcoming?.text = ""
        binding?.ivImage?.setImageResource(exerciseList!![currentExercise].getImage())
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        speakOut(exerciseList!![currentExercise].getName())

        binding?.progressBar?.progress = restProgress
        binding?.progressBar?.max = 30

        restTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = 30 - restProgress
                binding?.tvTimer?.text = (30 - restProgress).toString()
            }

            override fun onFinish() {
                exerciseList!![currentExercise].setIsCompleted(true)
                exerciseList!![currentExercise].setIsSelected(false)

                adapter!!.notifyDataSetChanged()
                currentExercise++
                if (currentExercise < exerciseList?.size!!) {
                    setupRestView()
                } else {
                    launchFinishActivity(this@ExerciseActivity)
                }
            }
        }.start()
    }

    private fun setupRestView() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        setRestProgressBar()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        binding = null
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.UK)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language is not supported")
            }
        } else {
            Log.e("TTS", "Initialization Failed")
        }
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun launchFinishActivity(context: ExerciseActivity) {
        var intent = Intent(context, FinishActivity::class.java)
        startActivity(intent)
        finish()
    }
}