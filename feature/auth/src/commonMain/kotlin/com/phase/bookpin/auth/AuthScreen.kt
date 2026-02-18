package com.phase.bookpin.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookpin.auth.generated.resources.Res
import bookpin.auth.generated.resources.app_name
import bookpin.auth.generated.resources.apple_login
import bookpin.auth.generated.resources.cd_app_icon
import bookpin.auth.generated.resources.cd_apple_login
import bookpin.auth.generated.resources.cd_kakao_login
import bookpin.auth.generated.resources.kakao_login
import bookpin.auth.generated.resources.login_subtitle
import bookpin.auth.generated.resources.login_title
import bookpin.auth.generated.resources.tagline
import bookpin.designsystem.generated.resources.bookpin_icon
import com.phase.bookpin.common.Platform
import com.phase.bookpin.common.extensions.collectSideEffect
import com.phase.bookpin.common.platform
import com.phase.bookpin.common.snackbar.LocalSnackbarHost
import com.phase.bookpin.designsystem.BookPinTheme
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import bookpin.designsystem.generated.resources.Res as CommonRes

@Composable
fun AuthScreen(
    onNavigateToHome: () -> Unit = {},
    viewModel: AuthViewModel = koinViewModel(),
) {
    val currentPlatform = remember { platform() }
    val snackbarHost = LocalSnackbarHost.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectSideEffect {
        when (it) {
            AuthSideEffect.NavigateToHome -> {
                onNavigateToHome()
            }

            is AuthSideEffect.ShowSnackbar -> {
                snackbarHost.showSnackbar(getString(it.message))
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BookPinTheme.colors.bgCanvas)
            .padding(horizontal = 24.dp, vertical = 32.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(1f))

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

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(Res.string.tagline),
                style = BookPinTheme.typography.bodyMedium,
                color = BookPinTheme.colors.textSecondary,
            )

            Spacer(modifier = Modifier.weight(0.5f))

            Text(
                text = stringResource(Res.string.login_title),
                style = BookPinTheme.typography.bodyMedium,
                color = BookPinTheme.colors.textPrimary,
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(Res.string.login_subtitle),
                style = BookPinTheme.typography.bodySmall,
                color = BookPinTheme.colors.textSecondary,
            )

            Spacer(modifier = Modifier.height(40.dp))

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.login()
                    },
                painter = painterResource(Res.drawable.kakao_login),
                contentDescription = stringResource(Res.string.cd_kakao_login),
                contentScale = ContentScale.FillWidth,
            )

            if (currentPlatform == Platform.iOS) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    painter = painterResource(Res.drawable.apple_login),
                    contentDescription = stringResource(Res.string.cd_apple_login),
                    contentScale = ContentScale.FillWidth,
                )
            }
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = BookPinTheme.colors.textPrimary)
            }
        }
    }
}
