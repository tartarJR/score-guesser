package com.tatar.scoreguesser.feature

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tatar.presentation.feature.main.MainViewModel
import com.tatar.presentation.viewmodel.factory.ViewModelFactory
import com.tatar.scoreguesser.ScoreGuesserApplication
import com.tatar.scoreguesser.base.FragmentActivityActions
import com.tatar.scoreguesser.databinding.ActivityMainBinding
import com.tatar.scoreguesser.util.currentNavigationFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity(), FragmentActivityActions {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        provideDependencies()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun provideDependencies() {
        DaggerMainComponent.factory()
            .create((application as ScoreGuesserApplication).appComponent)
            .inject(this)
    }

    override fun onRequestFinish() {
        finish()
    }

    override fun onPause() {
        super.onPause()

        val currentFragment = supportFragmentManager.currentNavigationFragment

        if (currentFragment != null) {
            viewModel.onViewPaused(currentFragment::class.simpleName)
        }
    }
}
