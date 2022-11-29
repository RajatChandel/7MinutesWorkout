package `in`.frostapps.a7minutesworkout

import `in`.frostapps.a7minutesworkout.databinding.ActivityBmiactivityBinding
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import java.math.RoundingMode
import java.text.DecimalFormat

class BMIActivity : AppCompatActivity() {

    private var binding: ActivityBmiactivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmi)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarBmi?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculate?.setOnClickListener {
            if(validate()) {
                calculate()
            }
        }
    }

    private fun calculate() {
        var weight = binding?.etWeight?.text.toString().toFloat()
        var height = binding?.etHeight?.text.toString().toFloat() / 100

        val bmi = weight / (height * height)
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING

        binding?.tvResult?.text = df.format(bmi).toString()
        binding?.tvResultString?.text = getResultString(bmi)
        binding?.resultView?.visibility = View.VISIBLE
    }

    private fun getResultString(bmi: Float): String {

        return when {
            bmi < 18.5 -> {
                "Underweight"
            }
            bmi >= 18.5 && bmi < 25.0 -> {
                "Normal"
            }
            bmi >= 25.0 && bmi < 30 -> {
                "Overweight"
            }
            else -> {
                "Obese"
            }
        }
    }

    private fun validate(): Boolean {
        var isValid = true

        if (binding?.etWeight?.text.toString().isEmpty()) {
            isValid = false
            binding?.tilWeight?.error =  this@BMIActivity.getString(R.string.error_weight)
            binding?.tilWeight?.isErrorEnabled = true
            binding?.tilHeight?.isErrorEnabled = false
        } else if (binding?.etHeight?.text.toString().isEmpty()) {
            isValid = false
            binding?.tilHeight?.error = this@BMIActivity.getString(R.string.error_height)
            binding?.tilHeight?.isErrorEnabled = true
            binding?.tilWeight?.isErrorEnabled = false
        } else {
            binding?.tilHeight?.isErrorEnabled = false
            binding?.tilWeight?.isErrorEnabled = false
        }

        return isValid
    }

    override fun onDestroy() {
        super.onDestroy()

        if (binding != null) {
            binding = null
        }
    }
}