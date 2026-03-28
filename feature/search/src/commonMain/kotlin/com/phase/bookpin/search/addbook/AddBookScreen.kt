package com.phase.bookpin.search.addbook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookpin.search.generated.resources.*
import coil3.compose.AsyncImage
import com.phase.bookpin.common.extensions.collectSideEffect
import com.phase.bookpin.common.extensions.noRippleClickable
import com.phase.bookpin.common.snackbar.LocalSnackbarHost
import com.phase.bookpin.designsystem.BookPinTheme
import com.phase.bookpin.designsystem.component.BPTextField
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddBookScreen(
    title: String,
    author: String,
    totalPage: Int,
    imageUrl: String,
    isbn: String,
    viewModel: AddBookViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateClose: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHost = LocalSnackbarHost.current

    LaunchedEffect(Unit) {
        viewModel.initWithBookInfo(
            title = title,
            author = author,
            totalPage = totalPage,
            imageUrl = imageUrl,
            isbn = isbn,
        )
    }

    viewModel.sideEffect.collectSideEffect {
        when (it) {
            is AddBookSideEffect.NavigateBack -> onNavigateBack()
            is AddBookSideEffect.NavigateClose -> onNavigateClose()
            is AddBookSideEffect.NavigateToHome -> onNavigateToHome()
            is AddBookSideEffect.ShowSnackbar -> snackbarHost.showSnackbar(it.message)
        }
    }

    Scaffold(
        topBar = {
            AddBookTopBar(
                onBackClick = viewModel::onBackClick,
                onCloseClick = viewModel::onCloseClick,
            )
        },
        bottomBar = {
            AddBookBottomBar(
                isEnabled = state.isSubmitEnabled,
                onClick = viewModel::onSubmit,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            BookCoverPreview(imageUrl = state.imageUrl)

            Spacer(modifier = Modifier.height(32.dp))

            FormField(
                label = stringResource(Res.string.addbook_label_title),
                value = state.title,
                onValueChange = viewModel::onTitleChange,
                placeholder = stringResource(Res.string.addbook_placeholder_title),
            )

            Spacer(modifier = Modifier.height(24.dp))

            FormField(
                label = stringResource(Res.string.addbook_label_author),
                value = state.author,
                onValueChange = viewModel::onAuthorChange,
                placeholder = stringResource(Res.string.addbook_placeholder_author),
            )

            Spacer(modifier = Modifier.height(24.dp))

            FormField(
                label = stringResource(Res.string.addbook_label_total_page),
                value = state.totalPage.toString(),
                onValueChange = viewModel::onTotalPageChange,
                placeholder = stringResource(Res.string.addbook_placeholder_total_page),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun AddBookTopBar(
    onBackClick: () -> Unit,
    onCloseClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(40.dp),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_back),
                    contentDescription = stringResource(Res.string.cd_back),
                    modifier = Modifier.size(24.dp),
                    tint = BookPinTheme.colors.iconDefault,
                )
            }

            Text(
                text = stringResource(Res.string.addbook_title),
                style = BookPinTheme.typography.headlineMedium,
                color = BookPinTheme.colors.textPrimary,
            )
        }

        IconButton(
            onClick = onCloseClick,
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = BookPinTheme.colors.bgSurface,
                    shape = CircleShape,
                ),
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_close),
                contentDescription = stringResource(Res.string.cd_close),
                modifier = Modifier.size(24.dp),
                tint = BookPinTheme.colors.iconDefault,
            )
        }
    }
}

@Composable
private fun BookCoverPreview(imageUrl: String) {
    Box(
        modifier = Modifier
            .width(128.dp)
            .height(192.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
            ).background(
                color = BookPinTheme.colors.buttonPrimary,
                shape = RoundedCornerShape(16.dp),
            ),
        contentAlignment = Alignment.TopEnd,
    ) {
        if (imageUrl.isNotBlank()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }

        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp)
                .background(
                    color = BookPinTheme.colors.bgElevated.copy(alpha = 0.9f),
                    shape = CircleShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_edit),
                contentDescription = stringResource(Res.string.cd_edit),
                modifier = Modifier.size(16.dp),
                tint = BookPinTheme.colors.iconDefault,
            )
        }
    }
}

@Composable
private fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = label,
            style = BookPinTheme.typography.titleSmall,
            color = BookPinTheme.colors.textPrimary,
            modifier = Modifier.padding(start = 4.dp),
        )

        BPTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = placeholder,
            keyboardOptions = keyboardOptions,
        )
    }
}

@Composable
private fun AddBookBottomBar(
    isEnabled: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BookPinTheme.colors.bgCanvas)
            .padding(horizontal = 24.dp)
            .padding(top = 24.dp, bottom = 40.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .alpha(if (isEnabled) 1f else 0.5f)
                .background(
                    color = BookPinTheme.colors.buttonPrimary,
                    shape = RoundedCornerShape(16.dp),
                ).then(
                    if (isEnabled) {
                        Modifier.noRippleClickable(onClick = onClick)
                    } else {
                        Modifier
                    },
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(Res.string.addbook_submit),
                style = BookPinTheme.typography.headlineSmall,
                color = BookPinTheme.colors.textOnAccent,
            )
        }
    }
}
