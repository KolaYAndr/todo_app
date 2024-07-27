package com.cleverpumpkin.settings.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cleverpumpkin.core.domain.AppTheme
import com.cleverpumpkin.core.presentation.theme.TodoAppTheme
import com.cleverpumpkin.settings.R
import com.cleverpumpkin.settings.presentation.composables.TopAppBarWithNavBack

@Composable
fun SettingsScreen(
    onChangeTheme: (AppTheme) -> Unit,
    onNavigation: () -> Unit,
    onNavBack: () -> Unit,
    themeString: String,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.background(TodoAppTheme.colorScheme.backPrimary),
        topBar = {
            TopAppBarWithNavBack(onNavBack = { onNavBack() })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(TodoAppTheme.colorScheme.backPrimary)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val themes = remember {
                listOf(AppTheme.System, AppTheme.Light, AppTheme.Dark)
            }
            var selectedOption by remember { mutableStateOf(themes.first { it.type == themeString }) }
            val context = LocalContext.current
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .background(TodoAppTheme.colorScheme.backSecondary)
            ) {
                Text(
                    text = stringResource(R.string.chosen_theme),
                    style = TodoAppTheme.typography.title,
                    modifier = Modifier.padding(10.dp),
                    color = TodoAppTheme.colorScheme.labelPrimary
                )
                Column(Modifier.selectableGroup()) {
                    themes.forEach { theme ->
                        val isSelected = theme == selectedOption
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = isSelected,
                                    onClick = {
                                        onChangeTheme(theme)
                                        selectedOption = theme
                                        Toast
                                            .makeText(
                                                context,
                                                R.string.restart_changes,
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    }
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = if (isSelected)
                                        R.drawable.radio_button_checked
                                    else R.drawable.radio_button_unchecked
                                ),
                                contentDescription = null,
                                modifier = Modifier.minimumInteractiveComponentSize(),
                                tint = if (isSelected)
                                    TodoAppTheme.colorScheme.blue
                                else TodoAppTheme.colorScheme.supportOverlay
                            )
                            Text(
                                text = theme.type,
                                style = TodoAppTheme.typography.body,
                                color = TodoAppTheme.colorScheme.labelPrimary
                            )
                        }
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(16.dp),
                color = TodoAppTheme.colorScheme.supportSeparator
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(TodoAppTheme.colorScheme.backSecondary)
                    .clickable { onNavigation() },
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.minimumInteractiveComponentSize(),
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = TodoAppTheme.colorScheme.blue
                )
                Text(
                    text = stringResource(R.string.about_app),
                    color = TodoAppTheme.colorScheme.labelPrimary,
                    style = TodoAppTheme.typography.body
                )
            }
        }
    }
}