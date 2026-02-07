package com.phase.bookpin.common.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> Flow<T>.collectSideEffect(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    event: suspend (sideEffect: T) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(this, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            this@collectSideEffect.collect { sideEffect ->
                event(sideEffect)
            }
        }
    }
}
