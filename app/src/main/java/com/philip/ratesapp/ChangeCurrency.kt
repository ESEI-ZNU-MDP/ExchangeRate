package com.philip.ratesapp

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class ChangeCurrency : AppCompatActivity() {
    var topCurrency: Currency? = null
    var bottomCurrency: Currency? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_change_currency)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.button_1).setOnClickListener {
            finish()
        }

        getExtrasFromIntent()
    }

    fun getExtrasFromIntent() {
        val topCurrencyId = intent.getStringExtra("TOP_CURRENCY_ID")
        val bottomCurrencyId = intent.getStringExtra("BOTTOM_CURRENCY_ID")
        if (topCurrencyId != null) {
            topCurrency = CurrencyProvider.getCurrencyById(topCurrencyId)
        }
        if (bottomCurrencyId != null) {
            bottomCurrency = CurrencyProvider.getCurrencyById(bottomCurrencyId)
        }
    }
}