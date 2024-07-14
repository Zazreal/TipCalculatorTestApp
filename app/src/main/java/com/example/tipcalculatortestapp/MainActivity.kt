package com.example.tipcalculatortestapp

import android.animation.ArgbEvaluator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
    private lateinit var etBaseInput: EditText
    private lateinit var SeekBarTip: SeekBar
    private lateinit var tvTipPercent: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvTipOpinion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.mainactivity)
        etBaseInput = findViewById(R.id.etBaseInput)
        SeekBarTip = findViewById(R.id.SeekBarTip)
        tvTipPercent = findViewById(R.id.tvTipPercent)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        tvTipOpinion = findViewById(R.id.tvTipOpinion)

        SeekBarTip.progress = 0
        tvTipPercent.text = "0%"
        UpdateTipOpinion(0)

        SeekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG, "onProgressChanged $p1")
                tvTipPercent.text = "$p1%"
                CalculateTipAndTotal()
                UpdateTipOpinion(p1)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
        etBaseInput.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int){}
            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")
                CalculateTipAndTotal()
            }
        })

    }


    private fun CalculateTipAndTotal()
    {
        //Gets values from view
        var BaseAmount: Double
        if(etBaseInput.text.isEmpty()){
            tvTipAmount.text = ""
            tvTotalAmount.text = ""
            return
        }
        BaseAmount = etBaseInput.text.toString().toDouble()
        val TipPercent = SeekBarTip.progress.toDouble()
        Log.i(TAG, "TipPercent $TipPercent")
        //calculates tip and total
        val TipVal = BaseAmount * (TipPercent/100)
        val TotalVal = BaseAmount + TipVal
        Log.i(TAG, "TipVal $TipVal")

        //updates view
        tvTipAmount.text = "%.2f".format(TipVal)
        tvTotalAmount.text = "%.2f".format(TotalVal)
    }

    private fun UpdateTipOpinion(TipPercent: Int)
    {
        //Selects text
        val TipOpinion: String = when(TipPercent) {
            in 0..9->"poor"
            in 10..19 -> "ok"
            in 20..29 -> "great"
            in 30..30 -> "Amazing"
            else -> "Error"
        }

        //Calculates the color
        val color = ArgbEvaluator().evaluate(
            TipPercent.toFloat()/SeekBarTip.max,
            ContextCompat.getColor(this,R.color.Wort_Tip),
            ContextCompat.getColor(this,R.color.Best_Tip)
        ) as Int

        //Sets text and color
        tvTipOpinion.text = TipOpinion
        tvTipOpinion.setTextColor(color)
    }
}