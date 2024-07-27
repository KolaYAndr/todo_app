package com.cleverpumpkin.settings.presentation.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.cleverpumpkin.core.presentation.theme.TodoAppTheme
import com.cleverpumpkin.settings.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithNavBack(onNavBack: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier.semantics { isTraversalGroup = true },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TodoAppTheme.colorScheme.backPrimary
        ),
        title = {},
        navigationIcon = {
            IconButton(
                modifier = Modifier.semantics { traversalIndex = -1f },
                onClick = { onNavBack() },
                colors = IconButtonDefaults.iconButtonColors(contentColor = TodoAppTheme.colorScheme.labelPrimary)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(
                        R.string.go_back
                    )
                )
            }
        }
    )
}

@PreviewLightDark
@Composable
fun PreviewTopBarWithNavBack() {
    TodoAppTheme {
        TopAppBarWithNavBack(
            onNavBack = {}
        )
    }
}