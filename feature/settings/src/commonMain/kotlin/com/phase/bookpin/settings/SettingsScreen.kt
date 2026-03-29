package com.phase.bookpin.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookpin.settings.generated.resources.*
import coil3.compose.AsyncImage
import com.phase.bookpin.common.extensions.collectSideEffect
import com.phase.bookpin.common.extensions.toFormattedDate
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
            SettingsSideEffect.NavigateToAllBookmarks -> {
                // TODO: Navigate to all bookmarks screen
            }
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
                profileName = state.profileName
                    ?: stringResource(Res.string.default_profile_name),
                profileImageUrl = state.profileImageUrl,
            )

            state.latestBookmark?.let { bookmark ->
                LatestBookmarkSection(
                    bookmark = bookmark,
                    onViewAllClick = viewModel::onViewAllBookmarksClick,
                )
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
    profileImageUrl: String?,
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
            if (profileImageUrl != null) {
                AsyncImage(
                    model = profileImageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
            } else {
                Icon(
                    painter = painterResource(Res.drawable.ic_person),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    tint = BookPinTheme.colors.iconDefault,
                )
            }
        }

        Text(
            text = profileName,
            style = BookPinTheme.typography.titleMedium,
            color = BookPinTheme.colors.textPrimary,
        )
    }
}

@Composable
private fun LatestBookmarkSection(
    bookmark: LatestBookmark,
    onViewAllClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = BookPinTheme.colors.bgSurface,
                shape = RoundedCornerShape(16.dp),
            ).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.achievements_title),
                style = BookPinTheme.typography.bodyMedium,
                color = BookPinTheme.colors.textSecondary,
            )

            Row(
                modifier = Modifier.clickable(onClick = onViewAllClick),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = stringResource(Res.string.view_all),
                    style = BookPinTheme.typography.bodySmall,
                    color = BookPinTheme.colors.textAccentMuted,
                )
                Icon(
                    painter = painterResource(Res.drawable.ic_chevron_right),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = BookPinTheme.colors.textAccentMuted,
                )
            }
        }

        LatestBookmarkCard(bookmark = bookmark)
    }
}

@Composable
private fun LatestBookmarkCard(
    bookmark: LatestBookmark,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
            ).background(
                color = BookPinTheme.colors.bgCanvas,
                shape = RoundedCornerShape(16.dp),
            ).border(
                width = 0.6.dp,
                color = BookPinTheme.colors.borderSubtle,
                shape = RoundedCornerShape(16.dp),
            ).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = bookmark.extractedText,
                style = BookPinTheme.typography.bodyLarge,
                color = BookPinTheme.colors.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            val subtitle = buildString {
                if (bookmark.bookTitle.isNotEmpty()) {
                    append(bookmark.bookTitle)
                    append(" \u00B7 ")
                }
                append(bookmark.createdAt.toFormattedDate())
            }
            Text(
                text = subtitle,
                style = BookPinTheme.typography.bodySmall,
                color = BookPinTheme.colors.textSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Icon(
            painter = painterResource(Res.drawable.ic_chevron_right),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = BookPinTheme.colors.iconDefault,
        )
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
