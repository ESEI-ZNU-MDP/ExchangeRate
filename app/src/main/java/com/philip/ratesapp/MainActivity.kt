package com.philip.ratesapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    var topCurrency = CurrencyProvider.usd
    var bottomCurrency = CurrencyProvider.uah

    var ignoreNextTextInput = false

    private val viewModel: RatesViewModel by lazy {
        ViewModelProvider(this,defaultViewModelProviderFactory)[RatesViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getSavedData()
        setListeners()
        updateInputs()
    }

    private fun getSavedData() {
        val savedTopCurrencyId = viewModel.getTopCurrency()
        bottomCurrency = CurrencyProvider.getCurrencyById(savedTopCurrencyId) ?: CurrencyProvider.usd
        val savedBottomCurrencyId = viewModel.getBottomCurrency()
        bottomCurrency = CurrencyProvider.getCurrencyById(savedBottomCurrencyId) ?: CurrencyProvider.uah

        val topInput = findViewById<TextView>(R.id.topTextEdit)
        topInput.text = viewModel.getTopValue()
        val bottomInput = findViewById<TextView>(R.id.bottomTextEdit)
        bottomInput.text = viewModel.getBottomValue()
    }

    private fun saveInputData(topValue: String, bottomValue: String) {
        viewModel.saveTopCurrency(topValue)
        viewModel.saveBottomValue(bottomValue)
    }

    private fun updateInputs() {
        val topSymbol = findViewById<TextView>(R.id.topSymbol)
        val topName = findViewById<TextView>(R.id.topName)
        setCurrency(topCurrency, topSymbol, topName)

        val bottomSymbol = findViewById<TextView>(R.id.bottomSymbol)
        val bottomName = findViewById<TextView>(R.id.bottomName)
        setCurrency(bottomCurrency, bottomSymbol, bottomName)
    }

    private fun setCurrency(currency: Currency, toSymbolTextView: TextView, toNameTextView: TextView) {
        toSymbolTextView.text = currency.symbol
        toNameTextView.text = currency.displayName
    }

    private fun setListeners() {
        val changeCurrencyButton = findViewById<ImageView>(R.id.currencyButton)

        changeCurrencyButton.setOnClickListener {
            val intent = Intent(this, ChangeCurrency::class.java)
            intent.putExtra("TOP_CURRENCY_ID", topCurrency.id)
            intent.putExtra("BOTTOM_CURRENCY_ID", bottomCurrency.id)
            startActivity(intent)
        }

        val topInput = findViewById<TextView>(R.id.topTextEdit)
        val bottomInput = findViewById<TextView>(R.id.bottomTextEdit)

        topInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (ignoreNextTextInput) {
                    ignoreNextTextInput = false
                    return
                }
                var topValue = s.toString().toDoubleOrNull()
                if (s.toString().isEmpty()) {
                    topValue = 0.0
                }
                if (topValue == null) {
                    Toast.makeText(this@MainActivity, "Entered number is invalid",Toast.LENGTH_LONG).show()
                    return
                }
                ignoreNextTextInput = true

                var bottomValue = String.format("%.2f", CurrencyService.convertRate(topValue, topCurrency, bottomCurrency))

                if (bottomValue == "0.00") {
                    bottomValue = ""
                }
                bottomInput.text = bottomValue
                saveInputData(topValue.toString(), bottomValue)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        bottomInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (ignoreNextTextInput) {
                    ignoreNextTextInput = false
                    return
                }
                var bottomValue = s.toString().toDoubleOrNull()
                if (s.toString().isEmpty()) {
                    bottomValue = 0.0
                }
                if (bottomValue == null) {
                    Toast.makeText(this@MainActivity, "Entered number is invalid",Toast.LENGTH_LONG).show()
                    return
                }

                ignoreNextTextInput = true
                var topValue = String.format("%.2f", CurrencyService.convertRate(bottomValue!!, bottomCurrency, topCurrency))

                if (topValue == "0.00") {
                    topValue = ""
                }
                topInput.text = topValue
                saveInputData(topValue, bottomValue.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}