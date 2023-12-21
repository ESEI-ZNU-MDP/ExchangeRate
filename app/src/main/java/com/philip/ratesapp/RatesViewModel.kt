package com.philip.ratesapp

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "RatesViewModel"

class RatesViewModel(state : SavedStateHandle) : ViewModel() {
    companion object {
        private val TOP_CUR_KEY = "TOP_CUR_KEY"
        private val TOP_VAL_KEY = "TOP_VAL_KEY"
        private val BOTTOM_CUR_KEY = "BOTTOM_CUR_KEY"
        private val BOTTOM_VAL_KEY = "BOTTOM_VAL_KEY"
    }

    init {
        Log.d(TAG, "ViewModel instance created")
    }

    private val savedStateHandle = state

    fun saveTopCurrency(id: String) {
        savedStateHandle.set(TOP_CUR_KEY, id)
    }

    fun getTopCurrency(): String {
        return savedStateHandle.get(TOP_CUR_KEY)?: "USD"
    }

    fun saveBottomCurrency(id: String) {
        savedStateHandle.set(BOTTOM_CUR_KEY, id)
    }

    fun getBottomCurrency(): String {
        return savedStateHandle.get(BOTTOM_CUR_KEY)?: "UAH"
    }

    fun saveTopValue(id: String) {
        savedStateHandle.set(TOP_VAL_KEY, id)
    }

    fun getTopValue(): String {
        return savedStateHandle.get(TOP_VAL_KEY)?: ""
    }

    fun saveBottomValue(id: String) {
        savedStateHandle.set(BOTTOM_VAL_KEY, id)
    }

    fun getBottomValue(): String {
        return savedStateHandle.get(BOTTOM_VAL_KEY)?: ""
    }
}