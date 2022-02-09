package com.deluxe.chessclock.presentation.util

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

fun navigateSafely(view: View, direction: NavDirections, id: Int) {
    if (Navigation.findNavController(view).currentDestination?.id == id)
        Navigation.findNavController(view).navigate(direction)
}