package com.cleverpumpkin.auth.auth_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cleverpumpkin.auth.R
import com.cleverpumpkin.core.presentation.theme.TodoAppTheme

@Composable
fun AuthScreen(
    state: State<AuthScreenUiState>,
    onYandexAuth: () -> Unit,
    onBasicAuth: () -> Unit,
    onNavigateForward: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state.value) {
                AuthScreenUiState.Loading -> {
                    CircularProgressIndicator()
                }

                AuthScreenUiState.Logged -> onNavigateForward()
                AuthScreenUiState.NotLogged -> {
                    Button(
                        onClick = { onYandexAuth() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TodoAppTheme.colorScheme.red,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.yandex_login),
                            style = TodoAppTheme.typography.button
                        )
                    }

                    TextButton(onClick = { onBasicAuth() }) {
                        Text(
                            text = stringResource(R.string.api_key_login),
                            style = TodoAppTheme.typography.button,
                            color = TodoAppTheme.colorScheme.blue
                        )
                    }
                }
            }

        }
    }
}
