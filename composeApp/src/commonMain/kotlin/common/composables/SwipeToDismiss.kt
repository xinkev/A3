package common.composables

import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

enum class SwipeToDismissState {
    Rest, Dismissed
}

@Composable
fun rememberSwipeToDismissState(): MutableState<SwipeToDismissState> {
    return remember { mutableStateOf(SwipeToDismissState.Rest) }
}

/**
 * SwipeToDismiss is a composable that allows you to swipe away a content item.
 * It uses a [AnchoredDraggableState] to manage the swipe gesture and provides
 * a callback to be invoked when the item is dismissed.
 * 
 * @param modifier Modifier to be applied to the SwipeToDismiss container.
 * @param threshold The fraction of the screen width that must be swiped to dismiss the item.
 * @param state The state of the SwipeToDismiss, which can be either Rest or Dismissed.
 * @param onDismissed Callback to be invoked when the item is dismissed.
 * @param backgroundContent Composable to be displayed in the background while swiping.
 * @param content Composable to be displayed as the main content of the SwipeToDismiss.
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SwipeToDismiss(
    modifier: Modifier = Modifier,
    threshold: Float = 0.5f,
    state: MutableState<SwipeToDismissState>,
    onDismissed: () -> Unit,
    backgroundContent: @Composable () -> Unit,
    content: @Composable () ->Unit,
) {
    val density = LocalDensity.current
    var contentSize by remember{ mutableStateOf(IntSize.Zero) }
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val draggableState = remember(contentSize) {
        AnchoredDraggableState(
            initialValue = state.value,
            anchors = DraggableAnchors {
                val offset = contentSize.width.toFloat()
                SwipeToDismissState.Dismissed at -offset
                SwipeToDismissState.Rest at 0f
            },
            positionalThreshold = { totalDistance -> totalDistance * threshold },
            velocityThreshold = { with(density) { 50.dp.toPx()} },
            snapAnimationSpec = tween(),
            decayAnimationSpec = decayAnimationSpec
        )
    }

    LaunchedEffect(draggableState.currentValue) {
        if (draggableState.currentValue == SwipeToDismissState.Dismissed) {
            state.value = SwipeToDismissState.Dismissed
            onDismissed()
        }
    }

    LaunchedEffect(state.value) {
        if(state.value == SwipeToDismissState.Rest) {
            draggableState.animateTo(SwipeToDismissState.Rest)
        }
    }

    Box(
        modifier = modifier
    ) {
        Box(Modifier.matchParentSize()) {
            backgroundContent()
        }
        Box(
            modifier = Modifier
                .onGloballyPositioned {
                    contentSize = it.size
                }
                .anchoredDraggable(
                    state = draggableState,
                    orientation = Orientation.Horizontal
                )
                .offset { IntOffset(x = draggableState.requireOffset().roundToInt(), y = 0) }
        ) {
            content()
        }
    }
}
