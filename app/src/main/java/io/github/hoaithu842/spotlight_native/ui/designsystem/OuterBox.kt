package io.github.hoaithu842.spotlight_native.ui.designsystem

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import kotlinx.coroutines.launch

@Composable
fun OuterBox(
    onLongPress: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current
    val progressAnimationValue = remember { Animatable(1f) }

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = progressAnimationValue.value
                scaleY = progressAnimationValue.value
                transformOrigin = TransformOrigin.Center
            }
            .wrapContentSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        scope.launch {
                            progressAnimationValue.animateTo(targetValue = 0.95f)
                            progressAnimationValue.animateTo(targetValue = 1f)
                        }
                        onLongPress()
                    },
                    onTap = { onClick() },
                )
            }
    ) {
        content()
    }
}