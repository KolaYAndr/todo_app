package com.cleverpumpkin.todoapp.presentation.composable_elements

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.cleverpumpkin.todoapp.presentation.theme.TodoAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SwipeableBackground(
    onEndToStart: () -> Unit,
    onStartToEnd: () -> Unit,
    modifier: Modifier = Modifier,
    onLongClick: () -> Unit,
    onClick: () -> Unit,
    durationMillis: Int = 500,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()

    val icon: ImageVector
    val alignment: Alignment
    val color: Color

    val swipeState = rememberSwipeToDismissBoxState()

    when (swipeState.dismissDirection) {
        SwipeToDismissBoxValue.EndToStart -> {
            icon = Icons.Filled.Delete
            alignment = Alignment.CenterEnd
            color = TodoAppTheme.colorScheme.red
        }

        else -> {
            icon = Icons.Filled.Done
            alignment = Alignment.CenterStart
            color = TodoAppTheme.colorScheme.green
        }
    }

    SwipeToDismissBox(
        modifier = modifier
            .animateContentSize()
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick() }
            ),
        state = swipeState,
        backgroundContent = {
            Box(
                contentAlignment = alignment,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
            ) {
                Icon(
                    modifier = Modifier.minimumInteractiveComponentSize(),
                    imageVector = icon,
                    contentDescription = null,
                    tint = TodoAppTheme.colorScheme.white
                )
            }
        }
    ) {
        content()
    }

    when (swipeState.currentValue) {
        SwipeToDismissBoxValue.EndToStart -> {
            LaunchedEffect(swipeState) {
                scope.launch {
                    delay(durationMillis.milliseconds)
                    onEndToStart()
                    swipeState.snapTo(SwipeToDismissBoxValue.Settled)
                }
            }
        }

        SwipeToDismissBoxValue.StartToEnd -> {
            LaunchedEffect(swipeState) {
                scope.launch {
                    delay(durationMillis.milliseconds)
                    onStartToEnd()
                    swipeState.snapTo(SwipeToDismissBoxValue.Settled)
                }
            }
        }

        else -> {}
    }
}