package io.github.pshegger.seriesdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import io.github.pshegger.seriesdb.databinding.ActivityMainBinding
import io.github.pshegger.seriesdb.utils.currentFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        val handled = currentFragment?.onBackPressed() ?: false
        if (!handled) {
            super.onBackPressed()
        }
    }
}
