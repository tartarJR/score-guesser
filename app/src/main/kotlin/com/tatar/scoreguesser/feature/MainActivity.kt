package com.tatar.scoreguesser.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tatar.scoreguesser.base.FragmentActivityActions
import com.tatar.scoreguesser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FragmentActivityActions {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onRequestFinish() {
        finish()
    }
}
