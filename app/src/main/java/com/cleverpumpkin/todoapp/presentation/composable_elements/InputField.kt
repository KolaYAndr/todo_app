package com.cleverpumpkin.todoapp.presentation.composable_elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cleverpumpkin.todoapp.R
import com.cleverpumpkin.todoapp.presentation.theme.TodoAppTheme

@Composable
fun InputField(
    onTextValueChange: (String) -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp)
) {
    TextField(
        shape = shape,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = TodoAppTheme.colorScheme.backSecondary,
            focusedContainerColor = TodoAppTheme.colorScheme.backSecondary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedTextColor = TodoAppTheme.colorScheme.labelPrimary,
            unfocusedTextColor = TodoAppTheme.colorScheme.labelPrimary
        ),
        value = text,
        onValueChange = { onTextValueChange(it) },
        modifier = modifier.heightIn(min = 104.dp),
        placeholder = {
            Text(
                text = stringResource(id = R.string.what_to_do),
                color = TodoAppTheme.colorScheme.labelTertiary
            )
        }
    )
}

@Composable
@Preview
fun PreviewInput() {
    InputField(
        onTextValueChange = {  },
        text = "",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}