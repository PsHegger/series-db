package io.github.pshegger.seriesdb.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import io.github.pshegger.seriesdb.R
import io.github.pshegger.seriesdb.ui.base.BaseFragment

fun Fragment.navigateTo(direction: NavDirections) = findNavController().navigate(direction)

val AppCompatActivity.currentFragment: BaseFragment?
    get() = supportFragmentManager.findFragmentById(R.id.navHostFragment)
        ?.childFragmentManager
        ?.fragments
        ?.get(0) as? BaseFragment
