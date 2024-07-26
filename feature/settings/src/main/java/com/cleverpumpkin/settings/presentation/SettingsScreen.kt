package com.cleverpumpkin.settings.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cleverpumpkin.core.presentation.theme.TodoAppTheme
import com.cleverpumpkin.settings.R
import com.cleverpumpkin.core.domain.AppTheme

@Composable
fun SettingsScreen(
    onChangeTheme: (AppTheme) -> Unit,
    onNavigation: () -> Unit,
    themeString: String,
    modifier: Modifier = Modifier
) {
    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .clip(RoundedCornerShape(8.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val themes = listOf(AppTheme.System, AppTheme.Light, AppTheme.Dark)
            val selectedOption = remember { mutableStateOf(themes.first { it.type == themeString }) }
            val context = LocalContext.current
            Column {
                Text(
                    text = stringResource(R.string.chosen_theme),
                    style = TodoAppTheme.typography.title,
                    modifier = Modifier.padding(10.dp),
                    color = TodoAppTheme.colorScheme.labelPrimary
                )
                Column(Modifier.selectableGroup()) {
                    themes.forEach { theme ->
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically)
                        {
                            RadioButton(
                                selected = (theme == selectedOption.value),
                                onClick = {
                                    onChangeTheme(theme)
                                    selectedOption.value = theme
                                    Toast.makeText(
                                        context,
                                        R.string.restart_changes,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = TodoAppTheme.colorScheme.blue, unselectedColor = TodoAppTheme.colorScheme.supportSeparator)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigation() },
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.minimumInteractiveComponentSize(),
                    imageVector = Icons.Default.Info,
                    contentDescription = stringResource(R.string.about_app),
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