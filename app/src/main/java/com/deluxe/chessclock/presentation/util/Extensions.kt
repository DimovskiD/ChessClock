package com.deluxe.chessclock.presentation.util

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment

fun Fragment.navigateSafely(direction: NavDirections, id: Int) {
    val navController = NavHostFragment.findNavController(this)
    if (navController.currentDestination?.id == id)
        navController.navigate(direction)
}

fun Context.showToast(stringId: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, stringId, length).show()
}