package com.phase.bookpin.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bookpin.home.generated.resources.Res
import bookpin.home.generated.resources.app_name
import bookpin.home.generated.resources.cd_settings
import bookpin.home.generated.resources.setting
import com.phase.bookpin.designsystem.BookPinTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun HomeTopBar(
    onSettingsClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(Res.string.app_name),
            style = BookPinTheme.typography.headlineLarge,
            color = BookPinTheme.colors.textPrimary,
        )

        IconButton(
            onClick = onSettingsClick,
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = BookPinTheme.colors.bgSurface,
                    shape = CircleShape,
                ),
        ) {
            Icon(
                painter = painterResource(Res.drawable.setting),
                contentDescription = stringResource(Res.string.cd_settings),
                modifier = Modifier.size(20.dp),
                tint = BookPinTheme.colors.iconDefault,
            )
        }
    }
}
