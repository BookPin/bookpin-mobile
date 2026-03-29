package com.phase.bookpin.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookpin.settings.generated.resources.*
import com.phase.bookpin.common.extensions.collectSideEffect
import com.phase.bookpin.common.snackbar.LocalSnackbarHost
import com.phase.bookpin.designsystem.BookPinTheme
import com.phase.bookpin.designsystem.component.BPConfirmDialog
import com.phase.bookpin.model.book.LatestBookmark
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit = {},
    onLogout: () -> Unit = {},
) {
    val viewModel: SettingsViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHost = LocalSnackbarHost.current

    viewModel.sideEffect.collectSideEffect {
        when (it) {
            is SettingsSideEffect.ShowSnackbar -> snackbarHost.showSnackbar(it.message)
            SettingsSideEffect.NavigateBack -> onNavigateBack()
            SettingsSideEffect.Logout -> onLogout()
            SettingsSideEffect.DeleteAccount -> onLogout()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BookPinTheme.colors.bgCanvas),
    ) {
        SettingsTopBar(onBackClick = viewModel::onBackClick)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            ProfileCard(
                profileName = state.profileName,
                accountType = state.accountType,
            )

            state.latestBookmark?.let { bookmark ->
                LatestBookmarkSection(bookmark = bookmark)
            }

            SettingsSection(
                onContactClick = viewModel::onContactClick,
                onLogoutClick = viewModel::onLogoutClick,
            )

            DeleteAccountItem(onClick = viewModel::onDeleteAccountClick)

            Text(
                text = stringResource(Res.string.app_version),
                style = BookPinTheme.typography.bodySmall,
                color = BookPinTheme.colors.textAccentMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (state.showLogoutDialog) {
        BPConfirmDialog(
            title = stringResource(Res.string.logout_title),
            description = stringResource(Res.string.logout_description),
            cancelText = stringResource(Res.string.cancel),
            confirmText = stringResource(Res.string.confirm_logout),
            confirmButtonColor = BookPinTheme.colors.buttonPrimary,
            onDismiss = viewModel::onLogoutDismiss,
            onConfirm = viewModel::onLogoutConfirm,
        )
    }

    if (state.showDeleteAccountDialog) {
        BPConfirmDialog(
            title = stringResource(Res.string.delete_account_title),
            description = stringResource(Res.string.delete_account_description),
            cancelText = stringResource(Res.string.cancel),
            confirmText = stringResource(Res.string.confirm_delete),
            confirmButtonColor = BookPinTheme.colors.error,
            onDismiss = viewModel::onDeleteAccountDismiss,
            onConfirm = viewModel::onDeleteAccountConfirm,
        )
    }
}

@Composable
private fun SettingsTopBar(
    onBackClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(BookPinTheme.colors.bgCanvas)
            .height(68.dp)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(Res.string.settings_title),
            style = BookPinTheme.typography.headlineSmall,
            color = BookPinTheme.colors.textPrimary,
        )

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(36.dp),
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_arrow_back),
                contentDescription = stringResource(Res.string.cd_back),
                modifier = Modifier.size(20.dp),
                tint = BookPinTheme.colors.textPrimary,
            )
        }
    }
}

@Composable
private fun ProfileCard(
    profileName: String,
    accountType: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = BookPinTheme.colors.bgSurface,
                shape = RoundedCornerShape(16.dp),
            ).padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(
                    color = BookPinTheme.colors.bgMuted,
                    shape = CircleShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_person),
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = BookPinTheme.colors.iconDefault,
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = profileName,
                style = BookPinTheme.typography.titleMedium,
                color = BookPinTheme.colors.textPrimary,
            )
            Text(
                text = accountType,
                style = BookPinTheme.typography.bodySmall,
                color = BookPinTheme.colors.textSecondary,
            )
        }
    }
}

@Composable
private fun LatestBookmarkSection(
    bookmark: LatestBookmark,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = stringResource(Res.string.achievements_title),
            style = BookPinTheme.typography.bodyMedium,
            color = BookPinTheme.colors.textSecondary,
            modifier = Modifier.padding(start = 4.dp),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = BookPinTheme.colors.bgSurface,
                    shape = RoundedCornerShape(16.dp),
                ).padding(16.dp),
        ) {
            LatestBookmarkItem(bookmark = bookmark)
        }
    }
}

@Composable
private fun LatestBookmarkItem(
    bookmark: LatestBookmark,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = "\uD83D\uDCD6",
            fontSize = 24.sp,
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = bookmark.extractedText,
                style = BookPinTheme.typography.bodyMedium,
                color = BookPinTheme.colors.textPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = bookmark.note,
                style = BookPinTheme.typography.bodySmall,
                color = BookPinTheme.colors.textSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "${bookmark.pageNumber}p",
                style = BookPinTheme.typography.bodySmall,
                color = BookPinTheme.colors.textSecondary,
            )
            Text(
                text = bookmark.createdAt,
                style = BookPinTheme.typography.bodySmall,
                color = BookPinTheme.colors.textAccentMuted,
            )
        }
    }
}

@Composable
private fun SettingsSection(
    onContactClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = stringResource(Res.string.settings_section_title),
            style = BookPinTheme.typography.bodyMedium,
            color = BookPinTheme.colors.textSecondary,
            modifier = Modifier.padding(start = 4.dp),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = BookPinTheme.colors.bgSurface,
                    shape = RoundedCornerShape(16.dp),
                ),
        ) {
            SettingsItem(
                iconRes = Res.drawable.ic_mail,
                text = stringResource(Res.string.contact),
                onClick = onContactClick,
            )

            HorizontalDivider(
                color = BookPinTheme.colors.bgMuted,
                thickness = 0.6.dp,
            )

            SettingsItem(
                iconRes = Res.drawable.ic_logout,
                text = stringResource(Res.string.logout),
                onClick = onLogoutClick,
            )
        }
    }
}

@Composable
private fun SettingsItem(
    iconRes: org.jetbrains.compose.resources.DrawableResource,
    text: String,
    onClick: () -> Unit,
    textColor: androidx.compose.ui.graphics.Color = BookPinTheme.colors.textPrimary,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = textColor,
        )

        Text(
            text = text,
            style = BookPinTheme.typography.titleMedium,
            color = textColor,
            modifier = Modifier.weight(1f),
        )

        Icon(
            painter = painterResource(Res.drawable.ic_chevron_right),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = textColor,
        )
    }
}

@Composable
private fun DeleteAccountItem(
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_delete_account),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = BookPinTheme.colors.textSecondary,
        )

        Text(
            text = stringResource(Res.string.delete_account),
            style = BookPinTheme.typography.bodyMedium,
            color = BookPinTheme.colors.textSecondary,
            modifier = Modifier.weight(1f),
        )

        Icon(
            painter = painterResource(Res.drawable.ic_chevron_right),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = BookPinTheme.colors.textSecondary,
        )
    }
}
