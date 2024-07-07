package com.example.tipcalculatortestapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tipcalculatortestapp.ui.theme.TipCalculatorTestAppTheme

private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
    private lateinit var etBaseInput: EditText
    private lateinit var SeekBarTip: SeekBar
    private lateinit var tvTipPercent: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.mainactivity)
        etBaseInput = findViewById(R.id.etBaseInput)
        SeekBarTip = findViewById(R.id.SeekBarTip)
        tvTipPercent = findViewById(R.id.tvTipPercent)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)

        SeekBarTip.progress = 0
        tvTipPercent.text = "0%"

        SeekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG, "onProgressChanged $p1")
                tvTipPercent.text = "$p1%"
                CalculateTipAndTotal()
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
        var BaseAmount: Double = 0.00
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
}