package ru.nikanorovsa.rate.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.EntryPoint
import ru.nikanorovsa.rate.R
import ru.nikanorovsa.rate.databinding.ActivityMainBinding
import ru.nikanorovsa.rate.ui.adapters.RateAdapter
import ru.nikanorovsa.rate.viewmodels.MainActivityViewModel
import ru.nikanorovsa.rate.viewmodels.Status

@EntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel : MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.initRateList()
        val rateAdapter = RateAdapter()
        binding.apply {
            mainActivityRecycler.apply {
                adapter = rateAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
            mainActivityInsertMoney.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (mainActivityInsertMoney.text.toString() != "") {
                        rateAdapter.changeMultiplier( mainActivityInsertMoney.text.toString().toDouble())
                    } else {
                        rateAdapter.changeMultiplier(0.0)
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
            mainActivitySwipeRefreshLayout.setOnRefreshListener {
                viewModel.initRateList()
                mainActivitySwipeRefreshLayout.isRefreshing = false
            }
        }
        viewModel.status.observe(this) {
            when (it!!) {
                Status.ERROR -> {
                    binding.mainActivityRecycler.visibility = VISIBLE
                    binding.mainActivityProgressBar.visibility = GONE
                    Toast.makeText(this, it.error.toString(), Toast.LENGTH_LONG).show()
                }
                Status.EMPTY -> {
                    binding.mainActivityRecycler.visibility = VISIBLE
                    binding.mainActivityProgressBar.visibility = GONE
                    Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    binding.mainActivityProgressBar.visibility = VISIBLE
                    binding.mainActivityRecycler.visibility = GONE
                }
                Status.SUCCESS -> {
                    binding.mainActivityRecycler.visibility = VISIBLE
                    binding.mainActivityProgressBar.visibility = GONE
                }
            }
        }
        viewModel.rate.observe(this) {
            if (it.isNullOrEmpty()) viewModel.initRateList()
            else {
                rateAdapter.submitList(it)
            }
        }
    }
}


