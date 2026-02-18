package com.phase.bookpin.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bookpin.designsystem.generated.resources.bookpin_icon
import bookpin.splash.generated.resources.Res
import bookpin.splash.generated.resources.app_name
import bookpin.splash.generated.resources.cd_app_icon
import bookpin.splash.generated.resources.splash_subtitle
import bookpin.splash.generated.resources.splash_title
import com.phase.bookpin.common.extensions.collectSideEffect
import com.phase.bookpin.common.snackbar.LocalSnackbarHost
import com.phase.bookpin.designsystem.BookPinTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import bookpin.designsystem.generated.resources.Res as CommonRes

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit = {},
    viewModel: SplashViewModel = koinViewModel(),
) {
    val snackbarHost = LocalSnackbarHost.current

    viewModel.sideEffect.collectSideEffect {
        when (it) {
            SplashSideEffect.NavigateToHome -> {
                onNavigateToHome()
            }

            is SplashSideEffect.ShowSnackbar -> {
                snackbarHost.showSnackbar(it.message)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BookPinTheme.colors.bgCanvas),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource(CommonRes.drawable.bookpin_icon),
                contentDescription = stringResource(Res.string.cd_app_icon),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(Res.string.app_name),
                style = BookPinTheme.typography.headlineLarge,
                color = BookPinTheme.colors.textPrimary,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(Res.string.splash_title),
                style = BookPinTheme.typography.bodyMedium,
                color = BookPinTheme.colors.textPrimary,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(Res.string.splash_subtitle),
                style = BookPinTheme.typography.bodySmall,
                color = BookPinTheme.colors.textSecondary,
            )
        }
    }
}
