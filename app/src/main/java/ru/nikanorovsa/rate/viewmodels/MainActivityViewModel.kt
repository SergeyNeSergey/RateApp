package ru.nikanorovsa.rate.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import ru.nikanorovsa.rate.data.retrofit.RateController
import ru.nikanorovsa.rate.data.room.RateDao

class MainActivityViewModel @ViewModelInject constructor(
    private val rateDao: RateDao,
    private val repository: RateController
) : ViewModel() {

    val status = MutableLiveData(Status.LOADING)
    val rate = rateDao.getAll().asLiveData()

    fun initRateList() {
        viewModelScope.launch(createDefaultExceptionHandler()) {
            rateDao.updateData(repository.getRateAsync().valute.values.toList())
            status.value = Status.SUCCESS
        }
    }

    private fun createDefaultExceptionHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { context, throwable ->
            Status.ERROR.error = throwable.message
           status.value = Status.ERROR
        }
    }
}

enum class Status {
    SUCCESS {
        override var error: String? = null
    },
    ERROR {
        override var error: String? = null
    },
    LOADING {
        override var error: String? = null
    },
    EMPTY {
        override var error: String? = null
    };
    abstract var error: String?
}